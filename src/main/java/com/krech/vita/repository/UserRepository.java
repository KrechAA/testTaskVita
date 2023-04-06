package com.krech.vita.repository;

import com.krech.vita.domain.db.User;

import java.util.List;


public interface UserRepository {

     List<User> getAllUsers();

     List<User>  getUserByName(String name);

     User getUserByLogin(String login);


     User getUserById(Long userId);

    User updateUser(User user);
}
