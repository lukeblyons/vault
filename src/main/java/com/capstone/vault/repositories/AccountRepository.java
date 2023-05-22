package com.capstone.vault.repositories;

import com.capstone.vault.entities.Account;
import com.capstone.vault.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserEquals(User user);

    @Query("SELECT a.accountNumber FROM Account a")
    Set<String> findAllAccountNumbers();

    int countByUser(User user);

    // For "Transfer" transactions
    Account findByAccountNumber(String accountNumber);
}
