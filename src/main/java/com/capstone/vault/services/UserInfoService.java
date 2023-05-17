package com.capstone.vault.services;

import com.capstone.vault.entities.UserInfo;

public interface UserInfoService {

    // Retrieves a UserInfo entity by its ID
    UserInfo getUserInfoById(Long id);

    // Saves a new UserInfo entity
    UserInfo saveUserInfo(UserInfo userInfo);

    // Updates an existing UserInfo entity by its ID
    UserInfo updateUserInfo(UserInfo userInfo);

    // Deletes a UserInfo entity by its ID
    void deleteUserInfo(Long id);
}
