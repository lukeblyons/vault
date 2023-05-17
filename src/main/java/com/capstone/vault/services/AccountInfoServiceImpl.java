package com.capstone.vault.services;

import com.capstone.vault.entities.AccountInfo;
import com.capstone.vault.repositories.AccountInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class          AccountInfoServiceImpl implements AccountInfoService {
    private final AccountInfoRepository accountInfoRepository;

    // Constructor injection to inject the AccountInfoRepository dependency
    @Autowired
    public AccountInfoServiceImpl(AccountInfoRepository accountInfoRepository) {
        this.accountInfoRepository = accountInfoRepository;
    }

    // Retrieve an account by ID from the repository
    @Override
    public AccountInfo getAccountInfoById(Long id) {
        return accountInfoRepository.findById(id).orElse(null);
    }

    // Save an account using the repository's save method
    @Override
    public AccountInfo saveAccountInfo(AccountInfo accountInfo) {
        return accountInfoRepository.save(accountInfo);
    }

    // Update an existing account using the repository's save method
    @Override
    public AccountInfo updateAccountInfo(AccountInfo accountInfo) {
        return accountInfoRepository.save(accountInfo);
    }

    // Delete an account by ID using the repository's deleteById method
    @Override
    public void deleteAccountInfo(Long id) {
        accountInfoRepository.deleteById(id);
    }

    // Retrieve all account info from a specific account
    @Override
    public List<AccountInfo> getAccountsByUserId(Long userId) {
        return accountInfoRepository.findByUserInfoId(userId);
    }

    // Retrieve account info from multiple accounts
    @Override
    public List<AccountInfo> getAccountsByIds(Long... accountIds) {
        return accountInfoRepository.findByIdIn(accountIds);
    }
}
