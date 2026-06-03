package com.campus.Users_Square_Games.web.service;

import com.campus.Users_Square_Games.web.dto.LoginUserDto;
import com.campus.Users_Square_Games.web.model.User;
import com.campus.Users_Square_Games.web.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    public User authenticate(LoginUserDto input){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )
        );
        return userRepository.findByUsername(input.username())
                .orElseThrow();
    }

}
