package com.capstone.vault.repositories;

import com.capstone.vault.entities.AccountInfo;
import com.capstone.vault.entities.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {
    List<TransactionDetails> findByReceiverAccountOrderByDateTimeDesc(AccountInfo receiverAccount);
    List<TransactionDetails> findByReceiverAccountUserInfoIdOrSenderAccountUserInfoIdOrderByDateTimeDesc(Long userId, Long userId1);
}
