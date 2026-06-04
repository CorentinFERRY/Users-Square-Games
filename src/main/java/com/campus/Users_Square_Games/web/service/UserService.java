package com.campus.Users_Square_Games.web.service;

import com.campus.Users_Square_Games.web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(String id);
    List<User> getAllUsers();
    void deletedUser(String id);
    boolean userExists(String id);
}
