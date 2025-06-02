package com.example.demo.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.model.UserPrincipal;

@Service
public class TenantService {
    
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            return userPrincipal.getUser();
        }
        throw new RuntimeException("No authenticated user found");
    }
    
    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}