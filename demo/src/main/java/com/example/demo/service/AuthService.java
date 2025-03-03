// // service/AuthService.java
// package com.example.demo.service;

// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.example.demo.dto.AuthResponse;
// import com.example.demo.dto.LoginRequest;
// import com.example.demo.dto.RegisterRequest;
// import com.example.demo.model.User;
// import com.example.demo.repository.UserRepository;
// import com.example.demo.security.JwtService;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class AuthService {
//     private final UserRepository userRepository;
//     private final JwtService jwtService;
//     private final AuthenticationManager authenticationManager;
//     private final PasswordEncoder passwordEncoder;

//     public AuthResponse register(RegisterRequest request) {
//         if (userRepository.existsByUsername(request.getUsername())) {
//             throw new RuntimeException("Username already exists");
//         }
//         if (userRepository.existsByEmail(request.getEmail())) {
//             throw new RuntimeException("Email already exists");
//         }

//         var user = User.builder()
//                 .username(request.getUsername())
//                 .email(request.getEmail())
//                 .password(passwordEncoder.encode(request.getPassword()))
//                 .role(request.getRole())
//                 .build();

//         var savedUser = userRepository.save(user);
//         var token = jwtService.generateToken(user);
//         return AuthResponse.builder()
//                 .token(token)
//                 .user(savedUser)
//                 .build();
//     }

//     public AuthResponse login(LoginRequest request) {
//         authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(
//                         request.getUsername(),
//                         request.getPassword()
//                 )
//         );
//         var user = userRepository.findByUsername(request.getUsername())
//                 .orElseThrow(() -> new RuntimeException("User not found"));
//         var token = jwtService.generateToken(user);
//         return AuthResponse.builder()
//                 .token(token)
//                 .user(user)
//                 .build();
//     }
// }
