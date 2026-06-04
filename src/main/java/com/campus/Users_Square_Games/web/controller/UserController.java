package com.campus.Users_Square_Games.web.controller;

import com.campus.Users_Square_Games.web.model.User;
import com.campus.Users_Square_Games.web.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /users/me - route de test connexion utilisateur
    @GetMapping("/me")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or #id == authentication.principal.id")
    public String me(Authentication authentication) {
        return "Connecté en tant que : " + authentication.getName();
    }

    // GET /users - Liste tous les utilisateurs (ADMIN uniquement)
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // POST /users - Créer un utilisateur
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    // GET /users/{id} - Récupérer un utilisateur par son identifiant
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or #id == authentication.principal.id")
    public Optional<User> getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    // DELETE /users/{id} - Supprimer un utilisateur
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteUser(@PathVariable String id){
        userService.deletedUser(id);
    }

    // GET /users/{id}/valid - Vérifier qu'un id existe
    @GetMapping("/{id}/valid")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public boolean userExists (@PathVariable String id){
        return userService.userExists(id);
    }

}
