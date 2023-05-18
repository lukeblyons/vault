package com.capstone.vault.controllers;

import com.capstone.vault.dtos.TransactionDetailsDTO;
import com.capstone.vault.services.TransactionDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionDetailsController {
    private final TransactionDetailsService transactionDetailsService;

    @Autowired
    public TransactionDetailsController(TransactionDetailsService transactionDetailsService) {
        this.transactionDetailsService = transactionDetailsService;
    }

    // Endpoint for creating a new transaction
    @PostMapping("/create")
    public TransactionDetailsDTO createTransaction(@RequestBody TransactionDetailsDTO transactionDetailsDTO) {
        return transactionDetailsService.createTransaction(transactionDetailsDTO);
    }

    // Endpoint for retrieving a transaction by ID
    @GetMapping("/{id}")
    public TransactionDetailsDTO getTransactionById(@PathVariable Long id) {
        return transactionDetailsService.getTransactionById(id);
    }

    // Endpoint for approving a transaction
    @PutMapping("/{id}/approve")
    public TransactionDetailsDTO approveTransaction(@PathVariable Long id) {
        return transactionDetailsService.approveTransaction(id);
    }

    // Endpoint for rejecting a transaction
    @PutMapping("/{id}/reject")
    public TransactionDetailsDTO rejectTransaction(@PathVariable Long id) {
        return transactionDetailsService.rejectTransaction(id);
    }

    // Endpoint for retrieving all transactions
    @GetMapping("/all")
    public List<TransactionDetailsDTO> getAllTransactions() {
        return transactionDetailsService.getAllTransactions();
    }

    // Endpoint for retrieving transactions by account ID
    @GetMapping("/account/{accountId}")
    public List<TransactionDetailsDTO> getTransactionsByAccount(@PathVariable Long accountId) {
        return transactionDetailsService.getTransactionsByAccount(accountId);
    }

    // Endpoint for retrieving transactions by user ID
    @GetMapping("/user/{userId}")
    public List<TransactionDetailsDTO> getTransactionsByUser(@PathVariable Long userId) {
        return transactionDetailsService.getTransactionsByUser(userId);
    }

    // Endpoint for updating a transaction
    @PutMapping("/{id}")
    public TransactionDetailsDTO updateTransaction(@PathVariable Long id, @RequestBody TransactionDetailsDTO transactionDetailsDTO) {
        return transactionDetailsService.updateTransaction(id, transactionDetailsDTO);
    }

    // Endpoint for deleting a transaction
    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionDetailsService.deleteTransaction(id);
    }
}
