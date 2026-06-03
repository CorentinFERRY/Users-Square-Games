package com.campus.Users_Square_Games.web.controller;

import com.campus.Users_Square_Games.web.dto.LoginResponse;
import com.campus.Users_Square_Games.web.dto.LoginUserDto;
import com.campus.Users_Square_Games.web.model.User;
import com.campus.Users_Square_Games.web.security.JwtService;
import com.campus.Users_Square_Games.web.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService){
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto LoginUserDto){
        User authenticatedUser = authenticationService.authenticate(LoginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

}
