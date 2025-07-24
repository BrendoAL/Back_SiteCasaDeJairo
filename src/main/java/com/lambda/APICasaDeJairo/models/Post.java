package com.lambda.APICasaDeJairo.models;

@Entity
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();
}

