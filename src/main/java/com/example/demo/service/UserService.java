package com.example.demo.service;

import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserModel insertUser(UserModel user) {
        System.out.println(user);


        Optional<UserModel> savedUser = userRepository.findByEmail(user.getEmail());
        if(savedUser.isPresent()){
            throw new IllegalArgumentException("Employee already exist with given email:" + savedUser.get().getEmail());
        }
        return userRepository.save(user);

    }

    public void getAllUsers(ArrayList<UserModel> user) {
        userRepository.findAll().forEach(user::add);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(Integer.parseInt(id));
    }
}
