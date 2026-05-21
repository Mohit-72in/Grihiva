package com.grihakhata.service.impl;

import com.grihakhata.domain.OwnerType;
import com.grihakhata.domain.Role;
import com.grihakhata.domain.User;
import com.grihakhata.dto.UserRegistrationRequestDTO;
import com.grihakhata.repository.UserRepository;
import com.grihakhata.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional
    public User onboardRenter(UserRegistrationRequestDTO request) {
        if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Phone number already registered");
        }

        User renter = new User();
        renter.setFullName(request.getFullName());
        renter.setPhoneNumber(request.getPhoneNumber());
        renter.setEmail(request.getEmail());
        renter.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        renter.setRole(Role.ROLE_RENTER);
        renter.setOwnerType(OwnerType.NONE);
        renter.setKycApproved(false);

        return userRepository.save(renter);
    }
}

