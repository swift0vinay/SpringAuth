package com.example.SpringTutorial.repository;

import com.example.SpringTutorial.entity.Role;
import com.example.SpringTutorial.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    Optional<Role> findByName(RoleType name);
    
}
