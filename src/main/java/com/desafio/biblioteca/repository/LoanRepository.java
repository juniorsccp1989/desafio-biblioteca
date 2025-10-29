package com.desafio.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.biblioteca.domain.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {
	
    List<Loan> findByBook_IdOrderByStartDateDesc(Long bookId);
}
