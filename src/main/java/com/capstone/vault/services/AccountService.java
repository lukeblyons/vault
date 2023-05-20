package com.capstone.vault.services;

import com.capstone.vault.dtos.AccountDTO;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<AccountDTO> getAllAccountsByUserId(Long userId);

    @Transactional
    void addAccount(AccountDTO accountDTO, Long userId);

    @Transactional
    void deleteAccountById(Long accountId);

    Optional<AccountDTO> getAccountById(Long accountId);
}
