package com.capstone.vault.entities;

import com.capstone.vault.dtos.AccountDTO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "account_balance", nullable = false)
    private BigDecimal accountBalance;

    @Column(name = "card")
    private String card;

    @ManyToOne
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Transaction> transactionSet = new HashSet<>();


    public Account(AccountDTO accountDTO) {
        if (accountDTO.getAccountNumber() != null) {
            this.accountNumber = accountDTO.getAccountNumber();
        }
        if (accountDTO.getAccountBalance() != null) {
            this.accountBalance = accountDTO.getAccountBalance();
        }
        if (accountDTO.getNickname() != null) {
            this.nickname = accountDTO.getNickname();
        }
        if (accountDTO.getCard() != null) {
            this.card = accountDTO.getCard();
        }
    }


}
