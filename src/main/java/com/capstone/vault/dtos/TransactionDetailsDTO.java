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

    public TransactionDetailsDTO(String transactionType, BigDecimal amount, String description,
                                 AccountInfoDTO senderAccount, AccountInfoDTO receiverAccount) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public AccountInfoDTO getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(AccountInfoDTO senderAccount) {
        this.senderAccount = senderAccount;
    }

    public AccountInfoDTO getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(AccountInfoDTO receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getApprovalUserId() {
        return approvalUserId;
    }

    public void setApprovalUserId(Long approvalUserId) {
        this.approvalUserId = approvalUserId;
    }
}
