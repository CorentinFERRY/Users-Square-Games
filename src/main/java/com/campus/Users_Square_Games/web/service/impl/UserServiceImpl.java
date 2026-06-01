package com.campus.Users_Square_Games.web.service.impl;

import com.campus.Users_Square_Games.web.model.User;
import com.campus.Users_Square_Games.web.repository.UserRepository;
import com.campus.Users_Square_Games.web.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deletedUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean userExists(Long id) {
        return userRepository.existsById(id);
    }
}
