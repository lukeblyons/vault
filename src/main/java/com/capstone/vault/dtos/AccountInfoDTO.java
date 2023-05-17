package com.capstone.vault.dtos;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfoDTO {
    private Long id;
    private String nickname;
    private String accountNumber;
    private BigDecimal accountBalance;
    private UserInfoDTO userInfo;
    private List<TransactionDetailsDTO> sentTransactions;
    private List<TransactionDetailsDTO> receivedTransactions;
}
