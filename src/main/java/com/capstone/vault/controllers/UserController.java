package com.capstone.vault.controllers;

import com.capstone.vault.dtos.UserDTO;
import com.capstone.vault.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public List<String> addUser(@RequestBody UserDTO userDTO) {
        String passHash = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(passHash);
        return userService.addUser(userDTO);
    }

    @PostMapping("/login")
    public List<String> userLogin(@RequestBody UserDTO userDTO) {
        return userService.userLogin(userDTO);
    }

}
