package com.frontend.financetracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public Long registerUser(User user) {
        try {
            return userRepository.save(user).getId();
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("username")) {
                throw new DuplicateFieldException("The username is already taken. Please choose a different one.");
            } else if (e.getMessage().contains("email")) {
                throw new DuplicateFieldException("The email is already registered. Please use a different email.");
            } else {
                throw new DuplicateFieldException("An error occurred during registration. Please try again.");
            }
        }
    }
    public boolean authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
            // Login successful
        }
        return false;  // Login failed
    }

}




