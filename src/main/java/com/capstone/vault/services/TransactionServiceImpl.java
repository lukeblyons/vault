package com.capstone.vault.services;

import com.capstone.vault.dtos.TransactionDTO;
import com.capstone.vault.entities.Transaction;
import com.capstone.vault.entities.Account;
import com.capstone.vault.repositories.TransactionRepository;
import com.capstone.vault.repositories.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

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

    @Override
    public List<TransactionDTO> getAllTransactionsByAccountId(Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            List<Transaction> transactionList = transactionRepository.findAllByAccountEquals(accountOptional.get());
            return transactionList.stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public void addTransaction(TransactionDTO transactionDTO, Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get(); // Allows account balance to be retrieved

            Transaction transaction = new Transaction(transactionDTO);
            transaction.setAccount(accountOptional.get());

            transaction.setDateTime(LocalDateTime.now());

            BigDecimal transactionAmount = transactionDTO.getAmount();
            BigDecimal accountBalance = account.getAccountBalance();

            if ("Deposit".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                account.setAccountBalance(accountBalance.add(transactionAmount));
            } else if ("Withdraw".equalsIgnoreCase(transactionDTO.getTransactionType())) {
                if (transactionAmount.compareTo(accountBalance) > 0) {
                    throw new IllegalArgumentException("Insufficient account balance");
                }
                account.setAccountBalance(accountBalance.subtract(transactionAmount));
            }

            transactionRepository.saveAndFlush(transaction);
            accountRepository.saveAndFlush(account);
        }
    }

}
