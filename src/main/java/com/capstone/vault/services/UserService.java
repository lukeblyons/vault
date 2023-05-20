package com.capstone.vault.services;

import com.capstone.vault.dtos.UserDTO;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    @Transactional
    List<String> addUser(UserDTO userDTO);

    List<String> userLogin(UserDTO userDTO);
}
