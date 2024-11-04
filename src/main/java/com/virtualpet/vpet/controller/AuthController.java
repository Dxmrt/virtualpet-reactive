package com.virtualpet.vpet.controller;

import com.virtualpet.vpet.model.User;
import com.virtualpet.vpet.repository.r2dbc.UserRepository;
import com.virtualpet.vpet.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/virtualpet")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> registerUser(@RequestBody User user) {
        return userRepository.findByUsername(user.getUsername())
                .flatMap(existingUser -> Mono.just(ResponseEntity.badRequest().body("Username already exists")))
                .switchIfEmpty(userRepository.save(user.withEncodedPassword(passwordEncoder.encode(user.getPassword())))
                        .then(Mono.just(ResponseEntity.ok("User registered successfully"))));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, String>>> login(@RequestBody User user) {
        return userRepository.findByUsername(user.getUsername())
                .flatMap(u -> {
                    if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                        String token = jwtUtil.generateToken(u.getUsername());
                        Map<String, String> response = new HashMap<>();
                        response.put("token", token);
                        return Mono.just(ResponseEntity.ok(response));
                    } else {
                        return Mono.error(new RuntimeException("Invalid credentials"));
                    }
                })
                .onErrorResume(e -> {
                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage())));
                });
    }
}
