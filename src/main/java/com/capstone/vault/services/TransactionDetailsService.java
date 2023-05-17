package com.capstone.vault.services;

import com.capstone.vault.entities.AccountInfo;
import com.capstone.vault.entities.TransactionDetails;

import java.util.List;

public interface TransactionDetailsService {
    // Retrieves a TransactionDetails entity by its ID
    TransactionDetails getTransactionDetailsById(Long id);

    // Saves a TransactionDetails entity
    TransactionDetails saveTransactionDetails(TransactionDetails transactionDetails);

    // Updates an existing TransactionDetails entity
    TransactionDetails updateTransactionDetails(TransactionDetails transactionDetails);

    // Deletes a TransactionDetails entity by its ID
    void deleteTransactionDetails(Long id);

    // Retrieves all transactions for a specific account, ordered by datetime in descending order
    List<TransactionDetails> getTransactionsByAccount(AccountInfo accountInfo);
}
