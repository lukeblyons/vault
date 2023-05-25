package com.capstone.vault.controllers;

import com.capstone.vault.dtos.TransactionDTO;
import com.capstone.vault.services.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Gets all transactions based on account
    @GetMapping("/account/{accountId}")
    public List<TransactionDTO> getAllTransactionsByAccountId(@PathVariable Long accountId) {
        return transactionService.getAllTransactionsByAccountId(accountId);
    }

    // Adds transaction to database
    @PostMapping("/{accountId}")
    public void addTransaction(@RequestBody TransactionDTO transactionDTO, @PathVariable Long accountId) {
        transactionService.addTransaction(transactionDTO, accountId);
    }

}
