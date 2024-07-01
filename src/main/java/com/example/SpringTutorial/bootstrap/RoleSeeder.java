package com.example.SpringTutorial.bootstrap;

import com.example.SpringTutorial.entity.Role;
import com.example.SpringTutorial.entity.RoleType;
import com.example.SpringTutorial.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent>, Ordered {
    
    @Override
    public int getOrder() {
        return 0;
    }
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadRoles();
    }
    
    
    private void loadRoles() {
        RoleType[] roleTypes = RoleType.values();
        Map<RoleType, String> roleDescriptionMap = Map.of(
                RoleType.USER, "Default user role",
                RoleType.ADMIN, "Administrator role",
                RoleType.SUPER_ADMIN, "Super administrator role"
        );
        
        Arrays.stream(roleTypes).forEach(roleType -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleType);
            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();
                roleToCreate.setName(roleType);
                roleToCreate.setDescription(roleDescriptionMap.get(roleType));
                roleRepository.save(roleToCreate);
            });
        });
    }
    
    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
    
}
