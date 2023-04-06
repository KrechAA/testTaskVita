package com.krech.vita.controller;

import com.krech.vita.domain.rest.request.UpdateRoleRequest;
import com.krech.vita.domain.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.krech.vita.service.UserService;

import java.util.List;


@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get concrete user. Method for admin
     * @param userName
     * @return
     */
    @GetMapping(value = "/admin/users/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List <User>> getUserByName(@PathVariable("userName") String userName) {
        List <User> user = userService.getUserByName(userName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Get all user in base. Method for admin
     * @return
     */
    @GetMapping(value = "/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers() {
        List <User> userList= userService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    /**
     * Changing role of user. Method for admin
     * @param updateRoleRequest
     * @return
     */
    @PutMapping(value = "/admin/users/changerole", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> changeRole(@RequestBody UpdateRoleRequest updateRoleRequest) {
        User updatedUser = userService.changeRole(updateRoleRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * Adding role to user. Method for admin
     * @param updateRoleRequest
     * @return
     */
    @PutMapping(value = "/admin/users/addrole", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addRole(@RequestBody UpdateRoleRequest updateRoleRequest) {
        User updatedUser = userService.addRole(updateRoleRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }




}
