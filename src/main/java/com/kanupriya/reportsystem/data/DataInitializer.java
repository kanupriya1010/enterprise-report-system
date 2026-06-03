package com.kanupriya.reportsystem.data;

import com.kanupriya.reportsystem.model.Role;
import com.kanupriya.reportsystem.model.User;
import com.kanupriya.reportsystem.repository.RoleRepository;
import com.kanupriya.reportsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");

        // Correct order: roles → users → admins
        createDefaultRolesIfNotExist(defaultRoles);
        createDefaultUsersIfNotExist();
        createDefaultAdminsIfNotExist();
    }

    private void createDefaultUsersIfNotExist() {
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        for (int i = 1; i <= 5; i++) {
            String email = "user" + i + "@email.com";

            if (userRepository.existsByEmail(email)) {
                continue;
            }

            User user = new User();
            user.setFirstName("User");
            user.setLastName("User" + i);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));

            userRepository.save(user);
            System.out.println("Default user " + i + " created successfully.");
        }
    }

    private void createDefaultAdminsIfNotExist() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));

        for (int i = 1; i <= 2; i++) {
            String email = "admin" + i + "@email.com";

            if (userRepository.existsByEmail(email)) {
                continue;
            }

            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));

            userRepository.save(user);
            System.out.println("Default admin " + i + " created successfully.");
        }
    }

    private void createDefaultRolesIfNotExist(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new)
                .forEach(roleRepository::save);
    }
}