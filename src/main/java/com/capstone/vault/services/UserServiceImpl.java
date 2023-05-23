package com.capstone.vault.services;

import com.capstone.vault.dtos.AccountDTO;
import com.capstone.vault.dtos.UserDTO;
import com.capstone.vault.entities.User;
import com.capstone.vault.repositories.UserRepository;
import com.capstone.vault.services.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public List<String> addUser(UserDTO userDTO) {
        List<String> response = new ArrayList<>();
        User user = new User(userDTO);
        userRepository.saveAndFlush(user);

        // Automatically creates "Checking" account after a user registers
        accountService.addAccount(new AccountDTO(), user.getId());
        // Automatically creates "Savings" account after a user registers
        accountService.addAccount(new AccountDTO(), user.getId());

        response.add("http://localhost:8080/landing.html");
        return response;
    }

    @Override
    public List<String> userLogin(UserDTO userDTO) {
        List<String> response = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByUsername(userDTO.getUsername());
        if (userOptional.isPresent()) {
            if (passwordEncoder.matches(userDTO.getPassword(), userOptional.get().getPassword())) {
                response.add("http://localhost:8080/landing.html");
                response.add(String.valueOf(userOptional.get().getId()));
            } else {
                response.add("Username or Password incorrect");
            }
        } else {
            response.add("Username or Password incorrect");
        }
        return response;
    }




}
