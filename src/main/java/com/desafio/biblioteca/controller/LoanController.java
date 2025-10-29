package com.desafio.biblioteca.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.biblioteca.domain.Loan;
import com.desafio.biblioteca.dto.LoanBatchRequestDto;
import com.desafio.biblioteca.service.BookService;
import com.desafio.biblioteca.service.LoanService;
import com.desafio.biblioteca.service.MessagingService;

@RestController
@RequestMapping
public class LoanController {

    private final MessagingService messagingService;
    private final LoanService loanService;

    public LoanController(MessagingService messagingService, LoanService loanService, BookService bookService) {
        this.messagingService = messagingService;
        this.loanService = loanService;
    }

    @PostMapping("/loans")
    public ResponseEntity<Void> postLoans(@RequestBody LoanBatchRequestDto batch) {
        messagingService.sendLoanRequest(batch);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/books/{id}/loans")
    public ResponseEntity<List<Loan>> getLoansByBook(@PathVariable("id") Long id) {
    	List<Loan> loansForBook = loanService.getLoansForBook(id);
    	if(loansForBook.isEmpty() || loansForBook == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(loansForBook);
    }

    @GetMapping("/loans/{id}")
    public ResponseEntity<Loan> getLoan(@PathVariable("id") Long id) {
        Loan loan = loanService.getLoan(id);
        if (loan == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(loan);
    }
}
