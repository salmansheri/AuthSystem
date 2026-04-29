package com.salman.AuthSystem.config;

import com.salman.AuthSystem.models.Role;
import com.salman.AuthSystem.repositories.RoleRepository;
import com.salman.AuthSystem.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class CommandLineRunnerConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        roleRepository.findByName("ROLE_" + AppConstants.ROLE_ADMIN).ifPresentOrElse(role -> {
            System.out.println("Role: " + role.getName() + " Already Exists!");
        }, () -> {
            Role role = new Role();
            role.setName("ROLE_" + AppConstants.ROLE_ADMIN);
            roleRepository.save(role);
        });

        roleRepository.findByName("ROLE_" + AppConstants.ROLE_USER).ifPresentOrElse(role -> {
            System.out.println("Role: " + role.getName() + " Already Exists!");

        }, () -> {
            Role role = new Role();
            role.setName("ROLE_" + AppConstants.ROLE_USER);
            roleRepository.save(role);
        });


    }
}
