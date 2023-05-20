package com.capstone.vault.repositories;

import com.capstone.vault.entities.Transaction;
import com.capstone.vault.entities.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByAccountEquals(Account account);
}
