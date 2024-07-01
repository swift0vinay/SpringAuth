package com.example.SpringTutorial.service;

import com.example.SpringTutorial.entity.UserEntity;
import com.example.SpringTutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public UserEntity addUser(UserEntity user) {
        return userRepository.save(user);
    }
    
    public List<UserEntity> allUsers() {
        return userRepository.findAll();
    }
    
}
