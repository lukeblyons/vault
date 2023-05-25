package com.capstone.vault.services;

import com.capstone.vault.dtos.TransactionDTO;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getAllTransactionsByAccountId(Long accountId);

    @Transactional
    void addTransaction(TransactionDTO transactionDTO, Long accountId);
}
