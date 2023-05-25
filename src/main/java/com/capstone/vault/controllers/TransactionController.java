package com.capstone.vault.controllers;

import com.capstone.vault.dtos.TransactionDTO;
import com.capstone.vault.services.TransactionService;

import com.capstone.vault.entities.Account;
import com.capstone.vault.repositories.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountRepository accountRepository;

    // Gets all transactions based on account
    @GetMapping("/account/{accountId}")
    public List<TransactionDTO> getAllTransactionsByAccountId(@PathVariable Long accountId) {
        return transactionService.getAllTransactionsByAccountId(accountId);
    }

    // Gets user's summed balance from all accounts
    @GetMapping("/user/{userId}/totalBalance")
    public BigDecimal getTotalBalanceByUserId(@PathVariable Long userId) {
        return accountRepository.findByUserId(userId).stream()
                .map(Account::getAccountBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    // Adds transaction to database
    @PostMapping("/{accountId}")
    public void addTransaction(@RequestBody TransactionDTO transactionDTO, @PathVariable Long accountId) {
        transactionService.addTransaction(transactionDTO, accountId);
    }

}
