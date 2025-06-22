package com.example.SehrinHikayesi.Service.AbstactService;

import com.example.SehrinHikayesi.DTO.Request.UserRequest;
import com.example.SehrinHikayesi.DTO.Response.UserResponse;

public interface AuthService {
    String login(String username, String password);
    UserResponse register(UserRequest request);
}
