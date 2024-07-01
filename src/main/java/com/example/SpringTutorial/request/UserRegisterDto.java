package com.example.SpringTutorial.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterDto {
    
    private String email;
    
    private String password;
    
    private String name;
    
}
