/*
package com.example.SehrinHikayesi.Mapper;

import com.example.SehrinHikayesi.DTO.Request.UserRequest;
import com.example.SehrinHikayesi.DTO.Response.UserResponse;
import com.example.SehrinHikayesi.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public UserResponse toResponse(User user) {
        String profileImageUrl = null;
        if (user.getProfile() != null) {
            profileImageUrl = user.getProfile().getProfileImageUrl();
        }

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                profileImageUrl
        );
    }
}
*/
package com.example.SehrinHikayesi.Mapper;

import com.example.SehrinHikayesi.DTO.Request.UserRequest;
import com.example.SehrinHikayesi.DTO.Response.UserResponse;
import com.example.SehrinHikayesi.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getProfileImage()
        );
    }
}
