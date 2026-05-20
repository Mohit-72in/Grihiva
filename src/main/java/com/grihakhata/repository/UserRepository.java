package com.grihakhata.repository;

import com.grihakhata.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.grihakhata.domain.OwnerType;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findFirstByOwnerType(OwnerType ownerType);
}
