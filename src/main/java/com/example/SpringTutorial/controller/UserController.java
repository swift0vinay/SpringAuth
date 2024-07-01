package com.example.SpringTutorial.controller;

import com.example.SpringTutorial.entity.UserEntity;
import com.example.SpringTutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    
    @PostMapping("/add")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user) {
        System.out.println("Calling this API");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    
    @GetMapping("/current-user")
    public String getLoggedinUser(Principal principal) {
        return principal.getName();
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserEntity> getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }
    
    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> getUsers() {
        List<UserEntity> users = userService.allUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
