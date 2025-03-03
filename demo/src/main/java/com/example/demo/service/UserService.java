// UserService.java
package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
// @RequiredArgsConstructor
// public class UserService implements UserDetailsService {
    public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authManager;
    // private final PasswordEncoder passwordEncoder;

    // @Override
    // public UserDetails loadUserByUsername(String username) {
    //     return userRepository.findByUsername(username)
    //         .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    // }

    // public User createUser(User user) {
        public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    public String verify(User user){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
            return "Success";
        }
        else{
            return "Fail";
        }
    }

    // public User login(User user){

    public User.Role getRoleForUser(String name) {
        Optional<User> optionalUser = userRepository.findByUsername(name);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            return user.getRole();
        }
        throw new UsernameNotFoundException("Username not found");
    }

    }

    // public Optional<User> findByUsername(String username) {
    //     return userRepository.findByUsername(username);
    // }

