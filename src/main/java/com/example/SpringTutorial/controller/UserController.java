package com.example.SpringTutorial.controller;

import com.example.SpringTutorial.dto_converters.UserDtoConverter;
import com.example.SpringTutorial.entity.UserEntity;
import com.example.SpringTutorial.response.UserResponse;
import com.example.SpringTutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserDtoConverter converter;
    
    @PostMapping("/add")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user) {
        System.out.println("Calling this API");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    
    @GetMapping("/current-user")
    @PreAuthorize("isAuthenticated()")
    public String getLoggedinUser(Principal principal) {
        return principal.getName();
    }
    
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        return new ResponseEntity<>(converter.toDto(currentUser), HttpStatus.OK);
    }
    
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserEntity> users = userService.allUsers();
        return new ResponseEntity<>(converter.toDto(users), HttpStatus.OK);
    }
    
    
}
