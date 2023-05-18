package com.capstone.vault.services;

import com.capstone.vault.entities.UserInfo;
import com.capstone.vault.dtos.UserInfoDTO;
import com.capstone.vault.repositories.UserInfoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserInfoDTO getUserInfoById(Long id) {
        UserInfo userInfo = userInfoRepository.findById(id).orElse(null);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfo, userInfoDTO);
        return userInfoDTO;
    }

    @Override
    public UserInfoDTO getUserInfoByUsername(String username) {
        UserInfo userInfo = userInfoRepository.findByUsername(username);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfo, userInfoDTO);
        return userInfoDTO;
    }

    @Override
    public boolean isPasswordValid(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public UserInfoDTO registerUser(UserInfoDTO userInfoDTO) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoDTO, userInfo);
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        UserInfo savedUserInfo = userInfoRepository.save(userInfo);
        UserInfoDTO savedUserInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(savedUserInfo, savedUserInfoDTO);
        return savedUserInfoDTO;
    }

    @Override
    public UserInfoDTO updateUser(UserInfoDTO userInfoDTO) {
        UserInfo userInfo = userInfoRepository.findById(userInfoDTO.getId()).orElse(null);
        if (userInfo != null) {
            BeanUtils.copyProperties(userInfoDTO, userInfo);
            UserInfo updatedUserInfo = userInfoRepository.save(userInfo);
            UserInfoDTO updatedUserInfoDTO = new UserInfoDTO();
            BeanUtils.copyProperties(updatedUserInfo, updatedUserInfoDTO);
            return updatedUserInfoDTO;
        }
        return null;
    }

    @Override
    public void deleteUserInfo(Long id) {
        userInfoRepository.deleteById(id);
    }
}
