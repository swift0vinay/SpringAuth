package com.example.SpringTutorial.service;

import com.example.SpringTutorial.entity.Role;
import com.example.SpringTutorial.entity.RoleType;
import com.example.SpringTutorial.entity.UserEntity;
import com.example.SpringTutorial.repository.RoleRepository;
import com.example.SpringTutorial.repository.UserRepository;
import com.example.SpringTutorial.request.UserRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public UserEntity addUser(UserEntity user) {
        return userRepository.save(user);
    }
    
    public List<UserEntity> allUsers() {
        return userRepository.findAll();
    }
    
    public UserEntity createAdmin(UserRegisterDto dto) {
        Optional<Role> role = roleRepository.findByName(RoleType.ADMIN);
        if (role.isEmpty())
            return null;
        UserEntity user = UserEntity.builder()
                .fullName(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(role.get())
                .build();
        return userRepository.save(user);
    }
    
}
