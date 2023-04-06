package com.krech.vita.service;

import com.krech.vita.domain.db.Role;
import com.krech.vita.domain.db.User;
import com.krech.vita.domain.rest.request.UpdateRoleRequest;
import com.krech.vita.repository.RoleRepositoryImpl;
import com.krech.vita.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;


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

    @Test
    void changeRoleTest() {
        UpdateRoleRequest request = new UpdateRoleRequest(1L, 2);

        User user = new User();
        user.setRoles(new HashSet<>());
        user.getRoles().add(new Role(1, "admin"));
        user.getRoles().add(new Role(3,"user"));

        Role role = new Role();
        role.setId(2);
        role.setName("operator");
        when(userRepository.getUserById(request.getUserId())).thenReturn(user);
        when(roleRepository.getRoleById(request.getRoleId())).thenReturn(role);
        when(userRepository.updateUser(user)).thenReturn(user);

        User result = userService.changeRole(request);
        assertTrue(result.getRoles().contains(role));
        assertEquals(1, result.getRoles().size());
    }



    @Test
    void changeRoleTestOfException() {
        UpdateRoleRequest request = new UpdateRoleRequest(1L, 2);

        when(userRepository.getUserById(request.getUserId())).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->  userService.changeRole(request));

    }

    @Test
    void addRoleTest() {
        UpdateRoleRequest request = new UpdateRoleRequest(1L, 2);

        User user = new User();
        user.setRoles(new HashSet<>());
        user.getRoles().add(new Role(1, "admin"));
        user.getRoles().add(new Role(3,"user"));

        Role role = new Role();
        role.setId(2);
        role.setName("operator");
        when(userRepository.getUserById(request.getUserId())).thenReturn(user);
        when(roleRepository.getRoleById(request.getRoleId())).thenReturn(role);
        when(userRepository.updateUser(user)).thenReturn(user);

        User result = userService.addRole(request);
        assertTrue(result.getRoles().contains(role));
        assertEquals(3, result.getRoles().size());
    }


    @Test
    void addRoleTestOfException() {
        UpdateRoleRequest request = new UpdateRoleRequest(1L, 2);

        when(userRepository.getUserById(request.getUserId())).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->  userService.addRole(request));

    }








}
