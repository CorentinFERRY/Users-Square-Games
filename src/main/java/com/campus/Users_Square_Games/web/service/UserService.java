package com.campus.Users_Square_Games.web.service;

import com.campus.Users_Square_Games.web.model.User;

import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(Long id);
    void deletedUser(Long id);
    boolean userExists(Long id);
}
