package com.example.SpringTutorial.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "roles")
@Entity
@Data
@ToString
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;
    
    @Column(unique = true, nullable = false)
    private RoleType name;
    
    @Column(nullable = false)
    private String description;
    
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    
}
