package com.grihakhata.bootstrap;

import com.grihakhata.domain.OwnerType;
import com.grihakhata.domain.Role;
import com.grihakhata.domain.User;
import com.grihakhata.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        User father = new User();
        father.setFullName("Admin Father");
        father.setPhoneNumber("9999990001");
        father.setEmail("father.admin@example.com");
        father.setRole(Role.ROLE_ADMIN);
        father.setOwnerType(OwnerType.FATHER);
        father.setPasswordHash(passwordEncoder.encode("Admin@123"));

        User uncle = new User();
        uncle.setFullName("Admin Uncle");
        uncle.setPhoneNumber("9999990002");
        uncle.setEmail("uncle.admin@example.com");
        uncle.setRole(Role.ROLE_ADMIN);
        uncle.setOwnerType(OwnerType.UNCLE);
        uncle.setPasswordHash(passwordEncoder.encode("Admin@123"));

        userRepository.save(father);
        userRepository.save(uncle);
    }
}
