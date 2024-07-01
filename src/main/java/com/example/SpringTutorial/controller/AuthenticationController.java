package com.example.SpringTutorial.controller;

import com.example.SpringTutorial.entity.UserEntity;
import com.example.SpringTutorial.request.UserLoginDto;
import com.example.SpringTutorial.request.UserRegisterDto;
import com.example.SpringTutorial.response.LoginResponse;
import com.example.SpringTutorial.service.AuthenticationService;
import com.example.SpringTutorial.service.JwtService;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody UserRegisterDto registerDto) {
        UserEntity user = authenticationService.register(registerDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginDto loginDto) {
        UserEntity user = authenticationService.login(loginDto);
        String jwtToken = jwtService.generateToken(user);
        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .expirationDate(jwtService.getExpiration(jwtToken))
                .build();
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
    
}
