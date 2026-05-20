package com.grihakhata.service;

import com.grihakhata.domain.User;

import java.util.Optional;

public interface UserService {
    User create(User user);
    User getById(Long id);
    Optional<User> findByPhoneNumber(String phoneNumber);
}
