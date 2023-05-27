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
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Set<Transaction> transactions = account.getTransactionSet();
            System.out.println("Transactions for Account ID " + accountId + ": " + transactions);
            return transactions.stream()
                    .map(TransactionDTO::new)
                    .collect(Collectors.toList());
        }
        System.out.println("No account found with ID: " + accountId);
        return Collections.emptyList(); // Returns empty list if the account doesn't exist
    }

    @Transactional(readOnly = true)
    @Override
    public List<TransactionDTO> getAllTransactionsByUserId(Long userId) {
        List<Account> userAccounts = accountRepository.findByUserId(userId);
        List<Transaction> allTransactions = new ArrayList<>();

        for (Account account : userAccounts) {
            allTransactions.addAll(account.getTransactionSet());
        }

        List<TransactionDTO> allTransactionDTOs = allTransactions.stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());

        return allTransactionDTOs;
    }

    @Override
    @Transactional
    public void addTransaction(TransactionDTO transactionDTO, Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (accountOptional.isPresent()) {
            Account fromAccount = accountOptional.get();

            Transaction transaction = new Transaction(transactionDTO, fromAccount);
            BigDecimal transactionAmount = transactionDTO.getAmount();
            BigDecimal fromAccountBalance = fromAccount.getAccountBalance();

            // Handles Deposits //
            if ("Deposit".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                fromAccount.setAccountBalance(fromAccountBalance.add(transactionAmount));

                // Handles Withdraws //
            } else if ("Withdraw".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                if (transactionAmount.compareTo(fromAccountBalance) > 0) {
                    throw new IllegalArgumentException("Insufficient account balance");
                }
                fromAccount.setAccountBalance(fromAccountBalance.subtract(transactionAmount));

                // Handles internal and external Transfers //
            } else if ("Transfer to".equalsIgnoreCase(transactionDTO.getTransactionType()) || "Send to".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                Long toAccountId = transactionDTO.getToAccountId();
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

                Transaction toTransaction = new Transaction(transactionDTO, toAccount);
                if ("Transfer to".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                    toTransaction.setTransactionType("Transferred from " + fromAccount.getNickname());
                } else if ("Send to".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                    toTransaction.setTransactionType("Received from " + fromAccount.getUser().getFirstName() + " " + fromAccount.getUser().getLastName());
                }
                toTransaction.setDateTime(LocalDateTime.now()); // Sets dateTime to current time for toTransaction
                toAccount.getTransactionSet().add(toTransaction);

                if ("Transfer to".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                    transaction.setTransactionType("Transferred to " + toAccount.getNickname());
                } else if ("Send to".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                    transaction.setTransactionType("Sent to " + toAccount.getUser().getFirstName() + " " + fromAccount.getUser().getLastName());
                }

                transactionRepository.saveAndFlush(toTransaction);
                accountRepository.saveAndFlush(toAccount);
            }

            fromAccount.getTransactionSet().add(transaction);
            transaction.setDateTime(LocalDateTime.now());

            transactionRepository.saveAndFlush(transaction);
            accountRepository.saveAndFlush(fromAccount);
        } else {
            throw new IllegalArgumentException("No account found with the given ID");
        }
    }



}
