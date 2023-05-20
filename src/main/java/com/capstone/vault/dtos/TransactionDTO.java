package com.capstone.vault.dtos;

import com.capstone.vault.entities.Transaction;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private String transactionType;
    private BigDecimal amount;
    private String description;
    private LocalDateTime dateTime;



    public TransactionDTO(Transaction transaction) {
        if (transaction.getId() != null) {
            this.id = transaction.getId();
        }
    }
}
