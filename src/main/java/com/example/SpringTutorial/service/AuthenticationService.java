package com.example.SpringTutorial.service;

import com.example.SpringTutorial.entity.UserEntity;
import com.example.SpringTutorial.repository.UserRepository;
import com.example.SpringTutorial.request.UserLoginDto;
import com.example.SpringTutorial.request.UserRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    public UserEntity register(UserRegisterDto userRegisterDto) {
        System.out.println("Calling register method");
        UserEntity userEntity = UserEntity.builder()
                .fullName(userRegisterDto.getName())
                .email(userRegisterDto.getEmail())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .build();
        return userRepository.save(userEntity);
    }
    
    public UserEntity login(UserLoginDto userLoginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getEmail(),
                        userLoginDto.getPassword()
                )
        );
        return userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
}
