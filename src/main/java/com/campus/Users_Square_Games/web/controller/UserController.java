package com.campus.Users_Square_Games.web.controller;

import com.campus.Users_Square_Games.web.model.User;
import com.campus.Users_Square_Games.web.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /users - Créer un utilisateur
    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    // GET /users/{id} - Récupérer un utilisateur par son identifiant
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    // DELETE /users/{id} - Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id){
        userService.deletedUser(id);
    }

    // GET /users/{id}/valid - Vérifier qu'un id existe
    @GetMapping("/{id}/valid")
    public boolean userExists (@PathVariable String id){
        return userService.userExists(id);
    }

}
