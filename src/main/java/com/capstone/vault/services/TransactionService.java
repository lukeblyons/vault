package com.capstone.vault.services;

import com.capstone.vault.dtos.TransactionDTO;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionService {
    // Gets all of the account's transactions
    List<TransactionDTO> getAllTransactionsByAccountId(Long accountId);

    // Gets all of the user's transactions
    List<TransactionDTO> getAllTransactionsByUserId(Long userId);

    @Transactional
    void addTransaction(TransactionDTO transactionDTO, Long accountId);
}
