package com.capstone.vault.repositories;

import com.capstone.vault.entities.AccountInfo;
import com.capstone.vault.entities.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {
    // Retrieves all transactions for a specific account, ordered by datetime in descending order
    List<TransactionDetails> findByReceiverAccountOrSenderAccountOrderByDateTimeDesc(AccountInfo receiverAccount, AccountInfo senderAccount);
}
