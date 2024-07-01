package com.example.SpringTutorial.dto_converters;

import com.example.SpringTutorial.entity.UserEntity;
import com.example.SpringTutorial.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDtoConverter {
    
    public UserResponse toDto(UserEntity user) {
        return UserResponse.builder()
                .email(user.getUsername())
                .role(user.getRole())
                .name(user.getFullName())
                .build();
    }
    
    public List<UserResponse> toDto(List<UserEntity> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
    
}
