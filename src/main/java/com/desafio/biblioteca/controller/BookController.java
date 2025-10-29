package com.desafio.biblioteca.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.biblioteca.domain.Book;
import com.desafio.biblioteca.dto.BookDto;
import com.desafio.biblioteca.service.BookService;

@RestController
@RequestMapping
public class BookController {

	private final BookService bookService;
	
    public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping("/books")
    public ResponseEntity<Void> saveBooks(@RequestBody List<BookDto> lsBooksDto) {
        List<Book> lsBook = bookService.save(lsBooksDto);
        if (lsBook.isEmpty() || lsBook == null) return ResponseEntity.notFound().build();
        return ResponseEntity.accepted().build();
    }
}
