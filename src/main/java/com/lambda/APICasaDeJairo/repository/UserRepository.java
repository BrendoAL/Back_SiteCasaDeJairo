package com.lambda.APICasaDeJairo.repository;

import com.lambda.APICasaDeJairo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//JPA
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

