package com.capstone.vault.entities;

import com.capstone.vault.dtos.UserDTO;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    @JsonManagedReference
    private Set<Account> accountSet = new HashSet<>();



    public User(UserDTO userDTO){
        if (userDTO.getEmail() != null) {
            this.email = userDTO.getEmail();
        }
        if (userDTO.getFirstName() != null) {
            this.firstName = userDTO.getFirstName();
        }
        if (userDTO.getLastName() != null) {
            this.lastName = userDTO.getLastName();
        }
        if (userDTO.getUsername() != null) {
            this.username = userDTO.getUsername();
        }
        if (userDTO.getPassword() != null) {
            this.password = userDTO.getPassword();
        }
    }


}
