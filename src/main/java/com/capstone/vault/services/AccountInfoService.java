package com.capstone.vault.services;

import com.capstone.vault.entities.AccountInfo;

import java.util.List;

public interface AccountInfoService {
    // Retrieve an account by ID
    AccountInfo getAccountInfoById(Long id);

    // Save an account
    AccountInfo saveAccountInfo(AccountInfo accountInfo);

    // Update an account
    AccountInfo updateAccountInfo(AccountInfo accountInfo);

    // Delete an account by ID
    void deleteAccountInfo(Long id);



    // Retrieve all accounts for a specific user
    List<AccountInfo> getAccountsByUserId(Long userId);

    // Retrieve accounts by their IDs
    List<AccountInfo> getAccountsByIds(Long... accountIds);
}
