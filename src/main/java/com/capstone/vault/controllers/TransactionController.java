package com.capstone.vault.controllers;

import com.capstone.vault.dtos.TransactionDTO;
import com.capstone.vault.services.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{accountId}")
    public List<TransactionDTO> getTransactionsByAccountId(@PathVariable Long accountId) {
        return transactionService.getAllTransactionsByAccountId(accountId);
    }

    @PostMapping("/{accountId}")
    public void addTransaction(@RequestBody TransactionDTO transactionDTO, @PathVariable Long accountId) {
        transactionService.addTransaction(transactionDTO, accountId);
    }

}
