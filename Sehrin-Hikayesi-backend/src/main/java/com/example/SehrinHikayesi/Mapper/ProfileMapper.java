package com.example.SehrinHikayesi.Mapper;

import com.example.SehrinHikayesi.DTO.Request.ProfileRequest;
import com.example.SehrinHikayesi.DTO.Response.ProfileResponse;
import com.example.SehrinHikayesi.Entity.Profile;
import com.example.SehrinHikayesi.Entity.User;
import com.example.SehrinHikayesi.Repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    private final UserRepository userRepository;

    public ProfileMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Profile toEntity(ProfileRequest dto) {
        Profile profile = new Profile();
        profile.setBio(dto.getBio());
        profile.setProfileImageUrl(dto.getProfileImageUrl());
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        profile.setUser(user);

        return profile;
    }

    public ProfileResponse toResponse(Profile profile) {
        return new ProfileResponse(
                profile.getId(),
                profile.getBio(),
                profile.getProfileImageUrl(),
                profile.getUser().getUsername()
        );
    }
}
