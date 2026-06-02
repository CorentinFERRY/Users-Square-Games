package com.campus.Users_Square_Games.web.repository;

import com.campus.Users_Square_Games.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsById(String id);
}
