package com.desafio.biblioteca.service;

import com.desafio.biblioteca.domain.Book;
import com.desafio.biblioteca.domain.BookStatus;
import com.desafio.biblioteca.domain.Loan;
import com.desafio.biblioteca.dto.LoanBatchRequestDto;
import com.desafio.biblioteca.dto.LoanRequestDto;
import com.desafio.biblioteca.dto.LoanResultDto;
import com.desafio.biblioteca.repository.BookRepository;
import com.desafio.biblioteca.repository.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final CacheService cacheService;
    private final MessagingService messagingService;

    public LoanService(BookRepository bookRepository, LoanRepository loanRepository,
                       CacheService cacheService, MessagingService messagingService) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.cacheService = cacheService;
        this.messagingService = messagingService;
    }

    @Transactional
    public void processBatch(LoanBatchRequestDto batch) {
        for (LoanRequestDto req : batch.getLoans()) {
            LoanResultDto result = new LoanResultDto();
            result.setBookId(req.getBookId());
            try {
                Loan loan = validateAndSave(req);
                result.setLoanId(loan.getId());
                result.setSuccess(true);
            } catch (IllegalArgumentException ex) {
                result.setSuccess(false);
                result.setReason(ex.getMessage());
                messagingService.sendToDlq(batch.getRequestId(), ex.getMessage());
            }
            messagingService.sendLoanResult(result);
        }
    }

    private Loan validateAndSave(LoanRequestDto req) {
        if (req.getFinishDate().isBefore(req.getStartDate())) {
            throw new IllegalArgumentException("finish_date must be >= start_date");
        }

        Book book = null;
        
        book = cacheService.getBookFromCache(req.getBookId())
                .orElseGet(() -> bookRepository.findById(req.getBookId())
                        .orElseThrow(() -> new IllegalArgumentException("Book not found")));

        if (book.getStatus() == BookStatus.RETIRED) {
            throw new IllegalArgumentException("Book is retired");
        }

        loanRepository.findByBook_IdOrderByStartDateDesc(req.getBookId())
                .stream().findFirst().ifPresent(last -> {
                    if (overlap(last.getStartDate(), last.getFinishDate(), req.getStartDate(), req.getFinishDate())) {
                        throw new IllegalArgumentException("Overlapping loan exists");
                    }
                });
        
        book = bookRepository.findById(req.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id " + req.getBookId()));

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setStartDate(req.getStartDate());
        loan.setFinishDate(req.getFinishDate());
        Loan loanSaved = loanRepository.save(loan);
        
        book.setStatus(BookStatus.RETIRED);
        bookRepository.save(book);
        
        cacheService.cacheLastLoan(req.getBookId(), loan);
        
        return loanSaved;
    }

    private boolean overlap(LocalDate aStart, LocalDate aFinish, LocalDate bStart, LocalDate bFinish) {
        return !aFinish.isBefore(bStart) && !bFinish.isBefore(aStart);
    }

    public List<Loan> getLoansForBook(Long bookId) {
        return loanRepository.findByBook_IdOrderByStartDateDesc(bookId);
    }

    public Loan getLoan(Long id) {
        return loanRepository.findById(id).orElse(null);
    }
}
