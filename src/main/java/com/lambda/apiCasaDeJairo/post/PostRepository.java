package com.lambda.apiCasaDeJairo.post;

import org.springframework.data.jpa.repository.JpaRepository;

//JPA
public interface PostRepository extends JpaRepository<Post, Long> {
}
