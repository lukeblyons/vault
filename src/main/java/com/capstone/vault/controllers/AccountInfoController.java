package com.capstone.vault.controllers;

import com.capstone.vault.dtos.AccountInfoDTO;
import com.capstone.vault.entities.AccountInfo;
import com.capstone.vault.services.AccountInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public class AccountInfoController {

    private final AccountInfoService accountInfoService;

    @Autowired
    public AccountInfoController(AccountInfoService accountInfoService) {
        this.accountInfoService = accountInfoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountInfoDTO> getAccountInfo(@PathVariable Long id) {
        AccountInfo accountInfo = accountInfoService.getAccountInfoById(id);
        if (accountInfo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AccountInfoDTO accountInfoDTO = new AccountInfoDTO();
        BeanUtils.copyProperties(accountInfo, accountInfoDTO);
        return new ResponseEntity<>(accountInfoDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountInfoDTO> createAccountInfo(@RequestBody AccountInfoDTO accountInfoDTO) {
        AccountInfo accountInfo = new AccountInfo();
        BeanUtils.copyProperties(accountInfoDTO, accountInfo);
        AccountInfo savedAccountInfo = accountInfoService.saveAccountInfo(accountInfo);
        AccountInfoDTO savedAccountInfoDTO = new AccountInfoDTO();
        BeanUtils.copyProperties(savedAccountInfo, savedAccountInfoDTO);
        return new ResponseEntity<>(savedAccountInfoDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountInfoDTO> updateAccountInfo(@PathVariable Long id, @RequestBody AccountInfoDTO accountInfoDTO) {
        AccountInfo existingAccountInfo = accountInfoService.getAccountInfoById(id);
        if (existingAccountInfo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BeanUtils.copyProperties(accountInfoDTO, existingAccountInfo);
        AccountInfo updatedAccountInfo = accountInfoService.updateAccountInfo(existingAccountInfo);
        AccountInfoDTO updatedAccountInfoDTO = new AccountInfoDTO();
        BeanUtils.copyProperties(updatedAccountInfo, updatedAccountInfoDTO);
        return new ResponseEntity<>(updatedAccountInfoDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountInfo(@PathVariable Long id) {
        AccountInfo accountInfo = accountInfoService.getAccountInfoById(id);
        if (accountInfo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountInfoService.deleteAccountInfo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountInfoDTO>> getAccountsByUserId(@PathVariable Long userId) {
        List<AccountInfo> accountInfoList = accountInfoService.getAccountsByUserId(userId);
        if (accountInfoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<AccountInfoDTO> accountInfoDTOList = accountInfoList.stream()
                .map(accountInfo -> {
                    AccountInfoDTO accountInfoDTO = new AccountInfoDTO();
                    BeanUtils.copyProperties(accountInfo, accountInfoDTO);
                    return accountInfoDTO;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(accountInfoDTOList, HttpStatus.OK);
    }
}
