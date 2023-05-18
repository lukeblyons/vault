package com.capstone.vault.controllers;

import com.capstone.vault.dtos.UserInfoDTO;
import com.capstone.vault.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserInfoController {
    private final UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    // Endpoint for user registration
    @PostMapping("/register")
    public UserInfoDTO registerUser(@RequestBody UserInfoDTO userInfoDTO) {
        return userInfoService.registerUser(userInfoDTO);
    }

    // Endpoint for user login
    @PostMapping("/login")
    public boolean loginUser(@RequestBody UserInfoDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        UserInfoDTO userInfoDTO = userInfoService.getUserInfoByUsername(username);

        if (userInfoDTO != null) {
            String encodedPassword = userInfoDTO.getPassword();
            return userInfoService.isPasswordValid(password, encodedPassword);
        }

        return false;
    }
}
