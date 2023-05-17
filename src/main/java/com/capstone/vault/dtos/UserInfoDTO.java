package com.capstone.vault.dtos;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private List<AccountInfoDTO> accountInfoList;
}
