/*
package com.example.SehrinHikayesi.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class LikeResponse {
    private Long id;
    private String username;
    private Long storyId;
    private LocalDateTime likedAt;

    public LikeResponse(Long id, String username, Long storyId, LocalDateTime likedAt) {
        this.id = id;
        this.username = username;
        this.storyId = storyId;
        this.likedAt = likedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public LocalDateTime getLikedAt() {
        return likedAt;
    }

    public void setLikedAt(LocalDateTime likedAt) {
        this.likedAt = likedAt;
    }
}*/
package com.example.SehrinHikayesi.DTO.Response;

import java.time.LocalDateTime;

public class LikeResponse {
    private Long id;
    private String username;
    private Long storyId;
    private LocalDateTime likedAt;

    public LikeResponse() {}

    public LikeResponse(Long id, String username, Long storyId, LocalDateTime likedAt) {
        this.id = id;
        this.username = username;
        this.storyId = storyId;
        this.likedAt = likedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public LocalDateTime getLikedAt() {
        return likedAt;
    }

    public void setLikedAt(LocalDateTime likedAt) {
        this.likedAt = likedAt;
    }
}
