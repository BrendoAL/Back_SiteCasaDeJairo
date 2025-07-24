package com.lambda.APICasaDeJairo.repository;

import com.lambda.APICasaDeJairo.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
