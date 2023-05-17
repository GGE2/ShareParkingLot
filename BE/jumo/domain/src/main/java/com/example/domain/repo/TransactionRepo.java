package com.example.domain.repo;

import com.example.domain.entity.Transaction;
import com.example.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUserAndTransactionDateStartingWithAndPtGetGreaterThan(User user, String date, int standard);
    List<Transaction> findAllByUserAndTransactionDateStartingWithAndPtLoseGreaterThan(User user, String date, int standard);
}
