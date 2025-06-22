package com.example.SehrinHikayesi.Controller;

import com.example.SehrinHikayesi.DTO.Request.UserRequest;
import com.example.SehrinHikayesi.DTO.Response.UserResponse;
import com.example.SehrinHikayesi.Entity.User;
import com.example.SehrinHikayesi.Repository.UserRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        UserResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserRequest request) {
        String token = authService.login(request.getUsername(), request.getPassword());
        User user = userRepository.findByUsername(request.getUsername()).get();
        String roleName = user.getRoles().stream()
                .findFirst()
                .map(role -> role.getRoleName())
                .orElse("USER");

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", roleName);
        response.put("userId", user.getId().toString());

        return ResponseEntity.ok(response);
    }




}
