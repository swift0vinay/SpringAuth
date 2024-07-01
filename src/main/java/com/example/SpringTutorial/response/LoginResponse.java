package com.example.SpringTutorial.response;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Builder
@Data
@ToString
public class LoginResponse {
    
    private String token;
    
    private long expiresIn;
    
    private Date expirationDate;
    
}
