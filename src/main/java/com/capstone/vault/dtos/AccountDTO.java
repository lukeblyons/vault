package com.capstone.vault.dtos;

import com.capstone.vault.entities.Account;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO implements Serializable {
    private Long id;
    private String nickname;
    private String accountNumber;
    private BigDecimal accountBalance;
    private UserDTO userInfo;



    public AccountDTO(Account account) {
        if (account.getId() != null) {
            this.id = account.getId();
        }
        if (account.getAccountNumber() != null) {
            this.accountNumber = account.getAccountNumber();
        }
        if (account.getAccountBalance() != null) {
            this.accountBalance = account.getAccountBalance();
        }
        if (account.getNickname() != null) {
            this.nickname = account.getNickname();
        }
    }

}
