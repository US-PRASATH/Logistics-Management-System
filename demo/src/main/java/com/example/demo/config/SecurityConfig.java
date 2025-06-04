// // config/SecurityConfig.java
package com.example.demo.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.example.demo.security.JwtAuthFilter;
// import com.example.demo.service.UserService;

// import lombok.RequiredArgsConstructor;

// @Configuration
// @EnableWebSecurity
// @RequiredArgsConstructor
// public class SecurityConfig {
//     private final JwtAuthFilter jwtAuthFilter;
//     private final UserService userService;

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http.csrf(csrf -> csrf.disable())
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/api/auth/**").permitAll()
//                 .anyRequest().authenticated()
//             )
//             .sessionManagement(session -> session
//                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//             )
//             .authenticationProvider(authenticationProvider())
//             .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//         return http.build();
//     }

//     @Bean
//     public AuthenticationProvider authenticationProvider() {
//         DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//         provider.setUserDetailsService(userService);
//         provider.setPasswordEncoder(passwordEncoder());
//         return provider;
//     }

//     @Bean
//     public BCryptPasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//         return config.getAuthenticationManager();
//     }
// }
// package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Apply CORS config
            .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Allow public auth routes
                .anyRequest().authenticated() // Secure all other routes
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Arrays.asList("*")); // Allow all origins (you can restrict later)
        config.setAllowedHeaders(Arrays.asList("*")); // Allow all headers
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow these HTTP methods

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails user = User.withDefaultPasswordEncoder()
    //             .username("admin")
    //             .password("password")
    //             .roles("USER")
    //             .build();
    //     return new InMemoryUserDetailsManager(user);
    // }
// }

