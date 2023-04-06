package com.krech.vita.controller;

import com.krech.vita.domain.rest.request.LogInRequest;
import com.krech.vita.domain.rest.request.UpdateRoleRequest;
import com.krech.vita.domain.rest.response.AuthenticationResponse;
import com.krech.vita.domain.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.krech.vita.service.JwtService;
import com.krech.vita.service.UserService;

@RestController
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }


    /**
     * Authitication method. For everybody
     * @param logInRequest
     * @return
     */
    @PostMapping(path = "/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> doLogin(@RequestBody LogInRequest logInRequest) {
        User user = userService.getByLoginAndVerifyPassword(logInRequest.getLogin(), logInRequest.getPassword());
        String jwtToken = jwtService.generateToken(user.getLogin());
        return new ResponseEntity<>(new AuthenticationResponse(jwtToken), HttpStatus.OK);
    }
}
