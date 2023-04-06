package com.krech.vita.service;

import com.krech.vita.domain.rest.request.UpdateRoleRequest;
import com.krech.vita.domain.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.krech.vita.repository.RoleRepositoryImpl;
import com.krech.vita.repository.UserRepositoryImpl;

import java.util.List;

@Service
public class UserService {

    private final UserRepositoryImpl userRepositoryImpl;
    private final RoleRepositoryImpl roleRepositoryImpl;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepositoryImpl userRepositoryImpl, RoleRepositoryImpl roleRepositoryImpl, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepositoryImpl = userRepositoryImpl;
        this.roleRepositoryImpl = roleRepositoryImpl;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    /**
     * Get all users in base
     * @return
     */
    public List <User> getAllUsers() {
        return userRepositoryImpl.getAllUsers();
    }


    /**
     * Get all users whose name starts with the input string
     * @param name
     * @return
     */
    public List<User> getUserByName(String name) {
        return userRepositoryImpl.getUserByName(name);
    }


    /**
     * Get user by login
     * @param login
     * @return
     */
    public User getUserByLogin(String login) {
        return userRepositoryImpl.getUserByLogin(login);
    }


    /**
     * Changing role of user
     * @param request
     * @return
     */
    public User changeRole(UpdateRoleRequest request) {
        User user = userRepositoryImpl.getUserById(request.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("Can't find user by id " + request.getUserId());
        }
        user.getRoles().clear();
        user.getRoles().add(roleRepositoryImpl.getRoleById(request.getRoleId()));
        return userRepositoryImpl.updateUser(user);
    }

    /**
     * Adding role to user
     * @param request
     * @return
     */
    public User addRole(UpdateRoleRequest request) {
        User user = userRepositoryImpl.getUserById(request.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("Can't find user by id " + request.getUserId());
        }
        user.getRoles().add(roleRepositoryImpl.getRoleById(request.getRoleId()));
        return userRepositoryImpl.updateUser(user);
    }

    /**
     * Searching user by login and verifying password
     * @param login
     * @param password
     * @return
     */
    public User getByLoginAndVerifyPassword(String login, String password) {
        AuthenticationServiceException authenticationException = new AuthenticationServiceException("Invalid login/password pair");
        User user = userRepositoryImpl.getUserByLogin(login);
        if (user == null) {
            throw authenticationException;
        }
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw authenticationException;
    }




}
