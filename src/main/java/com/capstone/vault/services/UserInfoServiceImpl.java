package com.capstone.vault.services;

import com.capstone.vault.entities.UserInfo;
import com.capstone.vault.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;

    // Constructor injection to inject the UserInfoRepository dependency
    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    // Retrieves a UserInfo entity by its ID using the userInfoRepository
    @Override
    public UserInfo getUserInfoById(Long id) {
        return userInfoRepository.findById(id).orElse(null);
    }

    // Saves a new UserInfo entity using the userInfoRepository
    @Override
    public UserInfo saveUserInfo(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    // Updates an existing UserInfo entity using the userInfoRepository
    @Override
    public UserInfo updateUserInfo(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    // Deletes a UserInfo entity by its ID using the userInfoRepository
    @Override
    public void deleteUserInfo(Long id) {
        userInfoRepository.deleteById(id);
    }

}
