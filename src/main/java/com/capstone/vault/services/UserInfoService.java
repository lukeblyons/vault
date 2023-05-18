package com.capstone.vault.services;

import com.capstone.vault.dtos.UserInfoDTO;

public interface UserInfoService {
    UserInfoDTO getUserInfoById(Long id);
    UserInfoDTO getUserInfoByUsername(String username);
    boolean isPasswordValid(String rawPassword, String encodedPassword);
    UserInfoDTO registerUser(UserInfoDTO userInfoDTO);
    UserInfoDTO updateUser(UserInfoDTO userInfoDTO);
    void deleteUserInfo(Long id);
}
