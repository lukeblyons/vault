package com.capstone.vault.dtos;

import com.capstone.vault.entities.Transaction;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private String transactionType;
    private BigDecimal amount;
    private String description;
    private String formattedDate;
    private String formattedTime;
    private Long accountId;
    private Long toAccountId; // Does not need to be added to entity


    public TransactionDTO(Transaction transaction) {
        if (transaction.getId() != null) {
            this.id = transaction.getId();
        }
        if (transaction.getTransactionType() != null) {
            this.transactionType = transaction.getTransactionType();
        }
        if (transaction.getAmount() != null) {
            this.amount = transaction.getAmount();
        }
        if (transaction.getDescription() != null) {
            this.description = transaction.getDescription();
        }
        if (transaction.getDateTime() != null) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            this.formattedDate = transaction.getDateTime().format(dateFormatter);

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
            this.formattedTime = transaction.getDateTime().format(timeFormatter);
        }
        if (transaction.getAccount() != null) {
            this.accountId = transaction.getAccount().getId();
        }

    }

    // Getter and Setter for toAccountId
    public Long getToAccountId() {
        return toAccountId;
    }
    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

}
