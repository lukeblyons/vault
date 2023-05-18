package com.capstone.vault;

import com.capstone.vault.entities.UserInfo;
import com.capstone.vault.repositories.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserInfoRepositoryTests {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    public void testSaveUserInfo() {
        // Create a new user info
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("john.doe");
        userInfo.setFirstName("John");
        userInfo.setLastName("Doe");
        userInfo.setEmail("john.doe@example.com");
        userInfo.setPassword("password");

        // Save the user info
        UserInfo savedUserInfo = userInfoRepository.save(userInfo);

        // Verify the saved user info
        assertNotNull(savedUserInfo.getId());
        assertEquals("john.doe", savedUserInfo.getUsername());
        assertEquals("John", savedUserInfo.getFirstName());
        assertEquals("Doe", savedUserInfo.getLastName());
        assertEquals("john.doe@example.com", savedUserInfo.getEmail());
        assertEquals("password", savedUserInfo.getPassword());

        // Retrieve the user info and verify
        UserInfo retrievedUserInfo = userInfoRepository.findById(savedUserInfo.getId()).orElse(null);
        assertNotNull(retrievedUserInfo);
        assertEquals(savedUserInfo.getId(), retrievedUserInfo.getId());
        assertEquals(savedUserInfo.getUsername(), retrievedUserInfo.getUsername());
        assertEquals(savedUserInfo.getFirstName(), retrievedUserInfo.getFirstName());
        assertEquals(savedUserInfo.getLastName(), retrievedUserInfo.getLastName());
        assertEquals(savedUserInfo.getEmail(), retrievedUserInfo.getEmail());
        assertEquals(savedUserInfo.getPassword(), retrievedUserInfo.getPassword());
    }

    @Test
    public void testGetAllUserInfos() {
        // Create multiple user info objects
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setUsername("john.doe");
        userInfo1.setFirstName("John");
        userInfo1.setLastName("Doe");
        userInfo1.setEmail("john.doe@example.com");
        userInfo1.setPassword("password");

        UserInfo userInfo2 = new UserInfo();
        userInfo2.setUsername("jane.smith");
        userInfo2.setFirstName("Jane");
        userInfo2.setLastName("Smith");
        userInfo2.setEmail("jane.smith@example.com");
        userInfo2.setPassword("password");

        // Save the user info objects
        userInfoRepository.save(userInfo1);
        userInfoRepository.save(userInfo2);

        // Retrieve all user info objects
        List<UserInfo> userInfos = userInfoRepository.findAll();

        // Verify the retrieved user info objects
        assertEquals(2, userInfos.size());
        assertEquals("john.doe", userInfos.get(0).getUsername());
        assertEquals("John", userInfos.get(0).getFirstName());
        assertEquals("Doe", userInfos.get(0).getLastName());
        assertEquals("john.doe@example.com", userInfos.get(0).getEmail());
        assertEquals("password", userInfos.get(0).getPassword());
        assertEquals("jane.smith", userInfos.get(1).getUsername());
        assertEquals("Jane", userInfos.get(1).getFirstName());
        assertEquals("Smith", userInfos.get(1).getLastName());
        assertEquals("jane.smith@example.com", userInfos.get(1).getEmail());
        assertEquals("password", userInfos.get(1).getPassword());
    }

    @Test
    public void testDeleteUserInfo() {
        // Create a new user info
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("john.doe");
        userInfo.setFirstName("John");
        userInfo.setLastName("Doe");
        userInfo.setEmail("john.doe@example.com");
        userInfo.setPassword("password");

        // Save the user info
        UserInfo savedUserInfo = userInfoRepository.save(userInfo);

        // Delete the user info
        userInfoRepository.deleteById(savedUserInfo.getId());

        // Verify the user info is deleted
        UserInfo deletedUserInfo = userInfoRepository.findById(savedUserInfo.getId()).orElse(null);
        assertNull(deletedUserInfo);
    }

}
