package com.example.SpringTutorial.controller;

import com.example.SpringTutorial.dto_converters.UserDtoConverter;
import com.example.SpringTutorial.entity.UserEntity;
import com.example.SpringTutorial.request.UserRegisterDto;
import com.example.SpringTutorial.response.UserResponse;
import com.example.SpringTutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admins")
@RestController
public class AdminController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    UserDtoConverter converter;
    
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserResponse> createAdmin(@RequestBody UserRegisterDto registerDto) {
        UserEntity admin = userService.createAdmin(registerDto);
        return ResponseEntity.ok(converter.toDto(admin));
    }
    
}
