package com.mongodb_example.main;

import com.mongodb_example.main.repositories.SteamUserRepository;
import com.mongodb_example.main.steam_users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RESTfulMongoController {
    @Autowired
    SteamUserRepository userRepository;

    @GetMapping("/users/{username}")
    public String getIdByUsername(@PathVariable String username) {
        return userRepository.findUserByUsername(username).getId();
    }

    @GetMapping("/users")
    public String showAllUsers() {
        StringBuilder stringBuilder = new StringBuilder();
        userRepository.findAll().forEach(user -> stringBuilder.append(user.toString()));
        return stringBuilder.toString();
    }

    @PostMapping("/users")
    public void createAUser(@RequestBody User newUser) {
        userRepository.save(newUser);
    }

    @PutMapping("/users/{username}")
    public void updateUser(@PathVariable String username) {
        User user = userRepository.findUserByUsername(username);
        user.getAchievements().add("New Achievement");
        userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void removeUser(@PathVariable String id) {
        userRepository.deleteById(id);
    }
}
