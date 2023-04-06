package com.krech.vita.service;

import com.krech.vita.domain.db.User;
import com.krech.vita.repository.RoleRepositoryImpl;
import com.krech.vita.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepositoryImpl userRepository;

    @Mock
    RoleRepositoryImpl roleRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    UserService userService;

    @BeforeEach
    public void init() {
        userService = new UserService(userRepository, roleRepository, bCryptPasswordEncoder);
    }


    @Test
    void findByLoginAndVerifyPasswordTest() {
        String login = "Petya";
        String password = "абыр";

        User existingUser = new User();
        existingUser.setPassword("абыр");
        existingUser.setLogin("Petya");
        existingUser.setName("Петя");
        existingUser.setId(1);
        existingUser.setRoles(new HashSet<>());


        when(userRepository.getUserByLogin(login)).thenReturn(existingUser);

        when(bCryptPasswordEncoder.matches(password, "абыр")).thenReturn(true);

        User result = userService.getByLoginAndVerifyPassword(login, password);

        assertEquals("Петя", result.getName());
        assertEquals("абыр", result.getPassword());
    }

    @Test
    void findByLoginAndVerifyPasswordTestOfException1() {
        String login = "Petya";
        String password = "абыр1";
        User user = new User();
        user.setLogin("Petya");
        user.setPassword("123");
        when(userRepository.getUserByLogin(login)).thenReturn(user);

        assertThrows(AuthenticationServiceException.class, () -> userService.getByLoginAndVerifyPassword(login, password));
    }


}
