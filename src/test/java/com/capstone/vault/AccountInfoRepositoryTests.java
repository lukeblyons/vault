package com.capstone.vault;

import com.capstone.vault.entities.AccountInfo;
import com.capstone.vault.repositories.AccountInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AccountInfoRepositoryTests {

    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Test
    public void testSaveAccountInfo() {
        // Create a new account info
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setNickname("Savings");
        accountInfo.setAccountNumber("1234567890");
        accountInfo.setAccountBalance(BigDecimal.valueOf(1000.00));

        // Save the account info
        AccountInfo savedAccountInfo = accountInfoRepository.save(accountInfo);

        // Verify the saved account info
        assertNotNull(savedAccountInfo.getId());
        assertEquals("Savings", savedAccountInfo.getNickname());
        assertEquals("1234567890", savedAccountInfo.getAccountNumber());
        assertEquals(BigDecimal.valueOf(1000.00), savedAccountInfo.getAccountBalance());

        // Retrieve the account info and verify
        AccountInfo retrievedAccountInfo = accountInfoRepository.findById(savedAccountInfo.getId()).orElse(null);
        assertNotNull(retrievedAccountInfo);
        assertEquals(savedAccountInfo.getId(), retrievedAccountInfo.getId());
        assertEquals(savedAccountInfo.getNickname(), retrievedAccountInfo.getNickname());
        assertEquals(savedAccountInfo.getAccountNumber(), retrievedAccountInfo.getAccountNumber());
        assertEquals(savedAccountInfo.getAccountBalance(), retrievedAccountInfo.getAccountBalance());
    }

    @Test
    public void testGetAllAccountInfos() {
        // Create multiple account info objects
        AccountInfo accountInfo1 = new AccountInfo();
        accountInfo1.setNickname("Savings");
        accountInfo1.setAccountNumber("1234567890");
        accountInfo1.setAccountBalance(BigDecimal.valueOf(1000.00));

        AccountInfo accountInfo2 = new AccountInfo();
        accountInfo2.setNickname("Checking");
        accountInfo2.setAccountNumber("2345678901");
        accountInfo2.setAccountBalance(BigDecimal.valueOf(2000.00));

        // Save the account info objects
        accountInfoRepository.save(accountInfo1);
        accountInfoRepository.save(accountInfo2);

        // Retrieve all account info objects
        List<AccountInfo> accountInfos = accountInfoRepository.findAll();

        // Verify the retrieved account info objects
        assertEquals(2, accountInfos.size());
        assertEquals("Savings", accountInfos.get(0).getNickname());
        assertEquals("1234567890", accountInfos.get(0).getAccountNumber());
        assertEquals(BigDecimal.valueOf(1000.00), accountInfos.get(0).getAccountBalance());
        assertEquals("Checking", accountInfos.get(1).getNickname());
        assertEquals("2345678901", accountInfos.get(1).getAccountNumber());
        assertEquals(BigDecimal.valueOf(2000.00), accountInfos.get(1).getAccountBalance());
    }

    @Test
    public void testDeleteAccountInfo() {
        // Create a new account info
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setNickname("Savings");
        accountInfo.setAccountNumber("1234567890");
        accountInfo.setAccountBalance(BigDecimal.valueOf(1000.00));

        // Save the account info
        AccountInfo savedAccountInfo = accountInfoRepository.save(accountInfo);

        // Delete the account info
        accountInfoRepository.deleteById(savedAccountInfo.getId());

        // Verify the account info is deleted
        AccountInfo deletedAccountInfo = accountInfoRepository.findById(savedAccountInfo.getId()).orElse(null);
        assertNull(deletedAccountInfo);
    }

}
