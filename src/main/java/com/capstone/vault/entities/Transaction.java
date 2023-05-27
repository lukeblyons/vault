package com.capstone.vault.entities;

import com.capstone.vault.dtos.TransactionDTO;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

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

    // Overrides the hashCode() method
    @Override
    public int hashCode() {
        return Objects.hash(id, transactionType, amount, description, dateTime);
    }

    // Overrides the equals() method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(transactionType, that.transactionType) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(description, that.description) &&
                Objects.equals(dateTime, that.dateTime);
    }

    // Overrides the toString() method, excluding transactionSet to prevent recursive toString call
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                // Exclude account field here
                '}';
    }


    public Transaction(TransactionDTO transactionDTO, Account account) {
        if (transactionDTO.getTransactionType() != null) {
            this.transactionType = transactionDTO.getTransactionType();
        }
        if (transactionDTO.getAmount() != null) {
            this.amount = transactionDTO.getAmount();
        }
        if (transactionDTO.getDescription() != null) {
            this.description = transactionDTO.getDescription();
        }
        this.account = account;

    }

}
