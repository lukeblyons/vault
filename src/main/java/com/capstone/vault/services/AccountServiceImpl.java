package com.capstone.vault.services;

import com.capstone.vault.dtos.AccountDTO;
import com.capstone.vault.entities.Account;
import com.capstone.vault.entities.User;
import com.capstone.vault.repositories.AccountRepository;
import com.capstone.vault.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.math.BigDecimal;
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
        if (userOptional.isPresent()) {
            Account account = new Account(accountDTO);
            account.setUser(userOptional.get());
            account.setAccountNumber(generateAccountNumber()); // Generate the account number
            account.setAccountBalance(BigDecimal.ZERO); // Account balance starts at 0.00

            // Automatically creates "Checking" and "Savings" accounts upon user registration
            int accountCount = accountRepository.countByUser(userOptional.get());
            if (accountCount == 0) {
                account.setNickname("Checking"); // User's first account's nickname is "Checking"
            } else if (accountCount == 1) {
                account.setNickname("Savings"); // User's second account's nickname is "Savings"
            }

            accountRepository.saveAndFlush(account);
        }
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

    // Automatically generates account number
    private String generateAccountNumber() {
        String prefix = "125-";
        String uniqueNumbers = generateUniqueNumbers();
        return prefix + uniqueNumbers;
    }

    // Helper method to generate 6 unique random numbers
    private String generateUniqueNumbers() {
        String uniqueNumbers = "";
        Random random = new Random();
        Set<String> existingAccountNumbers = accountRepository.findAllAccountNumbers();
        while (uniqueNumbers.isEmpty() || existingAccountNumbers.contains(uniqueNumbers)) {
            uniqueNumbers = String.format("%06d", random.nextInt(1000000));
        }
        return uniqueNumbers;
    }
}
