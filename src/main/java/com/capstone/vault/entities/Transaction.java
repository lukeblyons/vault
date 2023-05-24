package com.capstone.vault.entities;

import com.capstone.vault.dtos.TransactionDTO;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column
    private String description;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne
    @JsonBackReference
    private Account account;



    public Transaction(TransactionDTO transactionDTO) {
        if (transactionDTO.getTransactionType() != null) {
            this.transactionType = transactionDTO.getTransactionType();
        }
        if (transactionDTO.getAmount() != null) {
            this.amount = transactionDTO.getAmount();
        }
        if (transactionDTO.getDescription() != null) {
            this.description = transactionDTO.getDescription();
        }
        if (transactionDTO.getDateTime() != null) {
            this.dateTime = transactionDTO.getDateTime();
        }

    }


}
