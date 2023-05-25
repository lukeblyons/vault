package com.capstone.vault.services;

import com.capstone.vault.dtos.TransactionDTO;
import com.capstone.vault.entities.Transaction;
import com.capstone.vault.entities.Account;
import com.capstone.vault.repositories.TransactionRepository;
import com.capstone.vault.repositories.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    @Transactional(readOnly = true)
    @Override
    public List<TransactionDTO> getAllTransactionsByAccountId(Long accountId) {
        // Retrieve the account by ID
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            // If the account is present, fetch its transactions
            Account account = accountOptional.get();
            Set<Transaction> transactions = account.getTransactionSet();
            System.out.println("Transactions for Account ID " + accountId + ": " + transactions);
            // Convert each Transaction entity to a TransactionDTO and collect them into a list
            return transactions.stream()
                    .map(TransactionDTO::new)
                    .collect(Collectors.toList());
        }
        System.out.println("No account found with ID: " + accountId);
        return Collections.emptyList(); // Returns empty list if the account doesn't exist
    }


    @Override
    @Transactional
    public void addTransaction(TransactionDTO transactionDTO, Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId); // Retrieve the account by ID

        if (accountOptional.isPresent()) { // If the account exists, perform the transaction
            Account account = accountOptional.get(); // Allows account balance to be retrieved

            Transaction transaction = new Transaction(transactionDTO, account); // Creates new Transaction entity from the incoming TransactionDTO
            transaction.setDateTime(LocalDateTime.now()); // Sets dateTime to current time
            BigDecimal transactionAmount = transactionDTO.getAmount(); // Amount is manually entered, this gets it
            BigDecimal accountBalance = account.getAccountBalance(); // Gets the current account balance to use for add / subtract

            // Handles Deposit and Withdraw
            if ("Deposit".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                account.setAccountBalance(accountBalance.add(transactionAmount)); // "Deposit" adds to balance
            } else if ("Withdraw".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                if (transactionAmount.compareTo(accountBalance) > 0) { // Makes sure they have enough in the account to withdraw
                    throw new IllegalArgumentException("Insufficient account balance");
                }
                account.setAccountBalance(accountBalance.subtract(transactionAmount)); // "Withdraw" subtracts from balance
            }
            account.getTransactionSet().add(transaction); // Adds the transaction to account's transaction set

            transactionRepository.saveAndFlush(transaction); // Saves transaction
            accountRepository.saveAndFlush(account); // Updates account balance
        } else {
            throw new IllegalArgumentException("No account found with the given ID");
        }
    }

}
