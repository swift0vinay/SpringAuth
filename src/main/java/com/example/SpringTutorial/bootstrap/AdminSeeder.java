package com.example.SpringTutorial.bootstrap;

import com.example.SpringTutorial.entity.Role;
import com.example.SpringTutorial.entity.RoleType;
import com.example.SpringTutorial.entity.UserEntity;
import com.example.SpringTutorial.repository.RoleRepository;
import com.example.SpringTutorial.repository.UserRepository;
import com.example.SpringTutorial.request.UserRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent>, Ordered {
    
    @Override
    public int getOrder() {
        return 1;
    }
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createSuperAdmin();
    }
    
    private void createSuperAdmin() {
        System.out.println("Creating Admin User");
        UserRegisterDto dto = UserRegisterDto.builder()
                .name("Super Admin")
                .email("super_admin@myorg.com")
                .password("123456")
                .build();
        
        Optional<Role> role = roleRepository.findByName(RoleType.SUPER_ADMIN);
        Optional<UserEntity> user = userRepository.findByEmail(dto.getEmail());
        if (role.isEmpty() || user.isPresent()) {
            System.out.println("Either role was empty or user is already present");
            return;
        }
        
        UserEntity userEntity = UserEntity.builder()
                .fullName(dto.getName())
                .email(dto.getEmail())
                .role(role.get())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        
        userRepository.save(userEntity);
    }
    
    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
    
}
