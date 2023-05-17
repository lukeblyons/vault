package com.capstone.vault.dtos;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailsDTO {
    private Long id;
    private String transactionType;
    private BigDecimal amount;
    private String description;
    private LocalDateTime dateTime;
    private AccountInfoDTO senderAccount;
    private AccountInfoDTO receiverAccount;
    private String status;
    private Long approvalUserId;
}
