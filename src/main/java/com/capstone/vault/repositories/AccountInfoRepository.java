package com.capstone.vault.repositories;

import com.capstone.vault.entities.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfo, Long> {
    // Lets us display an account or multiple accounts and display respective info
    List<AccountInfo> findByUserInfoId(Long userId);
    List<AccountInfo> findByIdIn(Long... accountIds);
}
