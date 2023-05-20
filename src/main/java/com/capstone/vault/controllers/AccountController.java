package com.capstone.vault.controllers;

import com.capstone.vault.dtos.AccountDTO;
import com.capstone.vault.services.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/user/{userId}")
    public List<AccountDTO> getAccountsByUser(@PathVariable Long userId){
        return accountService.getAllAccountsByUserId(userId);
    }

    @GetMapping("/{accountId}")
    public Optional<AccountDTO> getAccountById(@PathVariable Long accountId){
        return accountService.getAccountById(accountId);
    }

    @PostMapping("/user/{userId}")
    public void addAccount(@RequestBody AccountDTO accountDTO, @PathVariable Long userId){
        accountService.addAccount(accountDTO, userId);
    }

    @DeleteMapping("/{accountId}")
    public void deleteAccountById(@PathVariable Long accountId){
        accountService.deleteAccountById(accountId);
    }

    
}
