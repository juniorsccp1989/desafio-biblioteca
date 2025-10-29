package com.desafio.biblioteca.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.desafio.biblioteca.controller.LoanController;
import com.desafio.biblioteca.domain.Book;
import com.desafio.biblioteca.domain.BookStatus;
import com.desafio.biblioteca.dto.BookDto;
import com.desafio.biblioteca.repository.BookRepository;

@Service
public class BookService {
	
	private static final Logger log = LoggerFactory.getLogger(LoanController.class);
	private final BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	public List<Book> save(List<BookDto> lsBooksDto) {
		log.info("Iniciando service para gravar livros");
		List<Book> lsBooksSaved = new ArrayList<>();
		for (BookDto bookDto : lsBooksDto) {
			log.info("Livro", bookDto);
			Optional<Book> bookById = bookRepository.findById(bookDto.getId());
			if (bookById.isPresent()){
				log.error("Book already exists");
			}
			Book book = new Book();
			book.setId(bookDto.getId());
			book.setTitle(bookDto.getTitle());
			book.setStatus(BookStatus.findByValue(bookDto.getStatus()));
			bookRepository.save(book);
			lsBooksSaved.add(book);
		}
		return lsBooksSaved;
	}

}
