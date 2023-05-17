package com.capstone.vault.entities;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "account_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "account_balance", nullable = false)
    private BigDecimal accountBalance;

    @ManyToOne
    @JoinColumn(name = "user_info_id")
    private UserInfo userInfo;

    @OneToMany(mappedBy = "receiverAccount", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TransactionDetails> sentTransactions;

    @OneToMany(mappedBy = "receiverAccount", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TransactionDetails> receivedTransactions;
}
