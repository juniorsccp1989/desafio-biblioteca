package com.desafio.biblioteca.service;

import com.desafio.biblioteca.domain.Book;
import com.desafio.biblioteca.domain.Loan;
import com.desafio.biblioteca.repository.BookRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final BookRepository bookRepository;

    public CacheService(RedisTemplate<String, Object> redisTemplate, BookRepository bookRepository) {
        this.redisTemplate = redisTemplate;
        this.bookRepository = bookRepository;
    }

    public Optional<Book> getBookFromCache(Long bookId) {
        String key = "book:status:" + bookId;
        Object o = redisTemplate.opsForValue().get(key);
        if (o instanceof Book) {
            return Optional.of((Book)o);
        }
        return bookRepository.findById(bookId);
    }

    public void cacheBook(Book book) {
        redisTemplate.opsForValue().set("book:status:" + book.getId(), book);
    }

    public void cacheLastLoan(Long bookId, Loan loan) {
        redisTemplate.opsForValue().set("book:lastloan:" + bookId, loan);
    }
}
