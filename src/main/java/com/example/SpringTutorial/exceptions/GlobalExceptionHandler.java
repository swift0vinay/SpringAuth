package com.example.SpringTutorial.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception e) {
        ProblemDetail err = null;
        e.printStackTrace();
        if (e instanceof BadCredentialsException) {
            err = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), e.getMessage());
            err.setProperty("description", "Username or password is incorrect");
        }
        if (e instanceof AccountStatusException) {
            err = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), e.getMessage());
            err.setProperty("description", "The account is locked");
        }
        if (e instanceof AccessDeniedException) {
            err = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), e.getMessage());
            err.setProperty("description", "You are not authorized to access this resource");
        }
        
        if (e instanceof SignatureException) {
            err = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), e.getMessage());
            err.setProperty("description", "The JWT signature is invalid");
        }
        
        if (e instanceof ExpiredJwtException) {
            err = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), e.getMessage());
            err.setProperty("description", "The JWT token has expired");
        }

        if (err == null) {
            err = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), e.getMessage());
            err.setProperty("description", "Unknown internal server error.");
        }
        
        return err;
        
    }
    
}
