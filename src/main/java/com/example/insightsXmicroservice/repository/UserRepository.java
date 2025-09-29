package com.example.insightsXmicroservice.repository;

import com.example.insightsXmicroservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the User entity.
 * Provides automatic data access methods for the 'users' table.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods can be defined here, e.g.:
    // Optional<User> findByEmail(String email);
}
