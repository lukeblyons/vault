package com.capstone.vault.services;

import com.capstone.vault.dtos.AccountDTO;
import com.capstone.vault.entities.Account;
import com.capstone.vault.entities.User;
import com.capstone.vault.repositories.AccountRepository;
import com.capstone.vault.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<AccountDTO> getAllAccountsByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            List<Account> accountList = accountRepository.findAllByUserEquals(userOptional.get());
            return accountList.stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public void addAccount(AccountDTO accountDTO, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Account account = new Account(accountDTO);
        userOptional.ifPresent(account::setUser);
        accountRepository.saveAndFlush(account);
    }

    @Override
    @Transactional
    public void deleteAccountById(Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        accountOptional.ifPresent(account -> accountRepository.delete(account));
    }

    @Override
    public Optional<AccountDTO> getAccountById(Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            return Optional.of(new AccountDTO(accountOptional.get()));
        }
        return Optional.empty();
    }

}
