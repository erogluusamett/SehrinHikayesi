package com.example.SehrinHikayesi.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProfileRequest {
    private String bio;
    private String profileImageUrl;
    private Long userId;

    public ProfileRequest(String bio, String profileImageUrl, Long userId) {
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
        this.userId = userId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}