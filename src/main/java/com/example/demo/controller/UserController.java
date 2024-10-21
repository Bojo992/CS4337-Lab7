package com.example.demo.controller;

import com.example.demo.model.UserModel;
import com.example.demo.service.ExternalService;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private UserService userService;
    private ExternalService externalService;

    public UserController(UserService userService, ExternalService externalService) {
        this.userService = userService;
        this.externalService = externalService;
    }

    @GetMapping
    public String basic() {
        return "Spring Boot Demo is running";
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable String id) {
        var user = userService.getUserById(Integer.valueOf(id));
        if (user == null) {
            return new ResponseEntity<UserModel>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<UserModel>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getAllUser() {
        var users = new ArrayList<UserModel>();
        userService.getAllUsers(users);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @GetMapping("/externalapi")
    public ResponseEntity<String> getLoremIpsum() {
        return externalService.requestLoremIpsum();
    }

    @PostMapping("/user")
    public ResponseEntity<UserModel> getUser(@RequestBody UserModel id) {
        var user = userService.getUserById(Integer.valueOf(id.getId()));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
