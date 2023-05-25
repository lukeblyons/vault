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
            Account fromAccount = accountOptional.get(); // Allows account balance to be retrieved

            Transaction transaction = new Transaction(transactionDTO, fromAccount); // Creates new Transaction entity from the incoming TransactionDTO
            BigDecimal transactionAmount = transactionDTO.getAmount(); // Amount is manually entered, this gets it
            BigDecimal fromAccountBalance = fromAccount.getAccountBalance(); // Gets the current account balance to use for add / subtract

            // Handles Deposits
            if ("Deposit".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                fromAccount.setAccountBalance(fromAccountBalance.add(transactionAmount)); // "Deposit" adds to balance

                // Handles Withdraws
            } else if ("Withdraw".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                if (transactionAmount.compareTo(fromAccountBalance) > 0) { // Makes sure they have enough in the account to withdraw
                    throw new IllegalArgumentException("Insufficient account balance");
                }
                fromAccount.setAccountBalance(fromAccountBalance.subtract(transactionAmount));

                // Handles Transfers from a users account to another one of their accounts
            } else if ("Transfer to".equalsIgnoreCase(transactionDTO.getTransactionType()) || "Send to".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                Long toAccountId = transactionDTO.getToAccountId(); // Assuming you've added this field to TransactionDTO
                Optional<Account> toAccountOptional = accountRepository.findById(toAccountId);
                if (!toAccountOptional.isPresent()) {
                    throw new IllegalArgumentException("Invalid 'to' account id");
                }
                Account toAccount = toAccountOptional.get();
                BigDecimal toAccountBalance = toAccount.getAccountBalance();

                if (transactionAmount.compareTo(fromAccountBalance) > 0) {
                    throw new IllegalArgumentException("Insufficient account balance");
                }
                fromAccount.setAccountBalance(fromAccountBalance.subtract(transactionAmount));
                toAccount.setAccountBalance(toAccountBalance.add(transactionAmount));

                // Add the transaction to 'to' account's transaction set as a Deposit
                Transaction toTransaction = new Transaction(transactionDTO, toAccount);
                if ("Transfer to".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                    toTransaction.setTransactionType("Transfer from " + fromAccount.getId());
                } else if ("Send to".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                    toTransaction.setTransactionType("Received from " + fromAccount.getId());
                }
                toTransaction.setDateTime(LocalDateTime.now()); // Sets dateTime to current time for toTransaction
                toAccount.getTransactionSet().add(toTransaction);

                // Modify the transaction type for 'from' account to show the transfer to account id
                if ("Transfer to".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                    transaction.setTransactionType("Transfer to " + toAccount.getId());
                } else if ("Send to".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                    transaction.setTransactionType("Sent to " + toAccount.getId());
                }

                // Save the 'to' transaction
                transactionRepository.saveAndFlush(toTransaction);
                accountRepository.saveAndFlush(toAccount);
            }


            // Add the transaction to 'from' account's transaction set
            fromAccount.getTransactionSet().add(transaction);

            // Set dateTime to current time for transaction
            transaction.setDateTime(LocalDateTime.now());

            // Save the 'from' transaction
            transactionRepository.saveAndFlush(transaction);
            accountRepository.saveAndFlush(fromAccount);
        } else {
            throw new IllegalArgumentException("No account found with the given ID");
        }
    }



}
