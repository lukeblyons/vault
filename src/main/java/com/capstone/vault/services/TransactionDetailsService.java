package com.capstone.vault.services;

import com.capstone.vault.dtos.TransactionDetailsDTO;

import java.util.List;

public interface TransactionDetailsService {
    TransactionDetailsDTO createTransaction(TransactionDetailsDTO transactionDetailsDTO);
    TransactionDetailsDTO getTransactionById(Long id);
    TransactionDetailsDTO approveTransaction(Long id);
    TransactionDetailsDTO rejectTransaction(Long id);
    List<TransactionDetailsDTO> getAllTransactions();
    List<TransactionDetailsDTO> getTransactionsByAccount(Long accountId);
    List<TransactionDetailsDTO> getTransactionsByUser(Long userId);
    TransactionDetailsDTO updateTransaction(Long id, TransactionDetailsDTO transactionDetailsDTO);
    void deleteTransaction(Long id);
}
