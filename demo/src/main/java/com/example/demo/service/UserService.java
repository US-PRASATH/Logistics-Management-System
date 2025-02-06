package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService{
    @Autowired
    UserRepository repo;

    public List<User> getAllUsers(){
        return repo.findAll();
    }

    public Optional<User> getUserById(Long id){
        return repo.findById(id);
    }

    public void createUser(User user){
        repo.save(user);
    }

    public void updateUser(Long id, User user){
        Optional<User> optionalUser = repo.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            if (user.getPassword() != null) {
                existingUser.setPassword(user.getPassword());
            }
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if (user.getRole() != null) {
                existingUser.setRole(user.getRole());
            }
            
            // Add more fields as needed
            repo.save(existingUser);
        }
    }

    public void deleteUser(Long id){
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }
}