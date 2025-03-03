package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.security.JwtTokenUtil;
import com.example.demo.service.UserService;


// @Controller
@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    UserService service;

    @Autowired
    AuthenticationManager authmanager;

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        //TODO: process POST request
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode(user.getPassword()));
        return service.register(user);
        // return entity;
    }

    @PostMapping("/login")
    public String login(@RequestBody User data) {
        //TODO: process POST request
        // return "Success";
        // return service.verify(user);
        try{
            var authToken=new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
            var auth=authmanager.authenticate(authToken);
            String role=service.getRoleForUser(auth.getName()).name();
            var jwt=jwtTokenUtil.generateToken(auth.getName(),role);
            return jwt;
        }
        catch(AuthenticationException e){
            return "Invalid Credentials";
        }
        // return entity;
    }
    
    
}
