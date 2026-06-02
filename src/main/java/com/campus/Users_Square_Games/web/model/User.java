package com.campus.Users_Square_Games.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;


@Entity
@Table(name = "users")
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    // Constructeur avec champs obligatoires (sans id)
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
