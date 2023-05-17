package com.capstone.vault.services;

import com.capstone.vault.entities.AccountInfo;
import com.capstone.vault.entities.TransactionDetails;
import com.capstone.vault.repositories.TransactionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionDetailsServiceImpl implements TransactionDetailsService {
    private final TransactionDetailsRepository transactionDetailsRepository;

    // Constructor injection to inject the TransactionDetailsRepository dependency
    @Autowired
    public TransactionDetailsServiceImpl(TransactionDetailsRepository transactionDetailsRepository) {
        this.transactionDetailsRepository = transactionDetailsRepository;
    }

    // Retrieves a TransactionDetails entity by its ID from the repository
    @Override
    public TransactionDetails getTransactionDetailsById(Long id) {
        return transactionDetailsRepository.findById(id).orElse(null);
    }

    // Saves a TransactionDetails entity to the repository
    @Override
    public TransactionDetails saveTransactionDetails(TransactionDetails transactionDetails) {
        return transactionDetailsRepository.save(transactionDetails);
    }

    // Updates an existing TransactionDetails entity from the repository
    @Override
    public TransactionDetails updateTransactionDetails(TransactionDetails transactionDetails) {
        return transactionDetailsRepository.save(transactionDetails);
    }

    // Deletes a TransactionDetails entity by its ID from the repository
    @Override
    public void deleteTransactionDetails(Long id) {
        transactionDetailsRepository.deleteById(id);
    }

    // Retrieves all transactions for a specific account, ordered by datetime in descending order
    @Override
    public List<TransactionDetails> getTransactionsByAccount(AccountInfo accountInfo) {
        return transactionDetailsRepository.findByReceiverAccountOrSenderAccountOrderByDateTimeDesc(accountInfo, accountInfo);
    }

}
