package com.example.SpringTutorial.request;

import lombok.Data;

@Data
public class UserRegisterDto {
    
    private String email;
    
    private String password;
    
    private String name;
    
}
