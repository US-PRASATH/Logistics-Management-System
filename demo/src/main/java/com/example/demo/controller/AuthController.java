package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;


@Controller
public class AuthController{
@Autowired
UserRepository repo;

@GetMapping("/api/users")
public List<User> getAllUsers() {
    return repo.findAll();
}

@GetMapping("path")
public String getMethodName(@RequestParam String param) {
    return new String();
}

}