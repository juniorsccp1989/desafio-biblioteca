package com.desafio.biblioteca.repository;

import com.desafio.biblioteca.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
