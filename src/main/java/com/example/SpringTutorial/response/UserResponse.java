package com.example.SpringTutorial.response;

import com.example.SpringTutorial.entity.Role;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class UserResponse {
    
    private String name;
    
    private String email;
    
    private Role role;
    
}
