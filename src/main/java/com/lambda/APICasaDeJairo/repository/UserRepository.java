package com.lambda.APICasaDeJairo.repository;

import com.lambda.APICasaDeJairo.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

