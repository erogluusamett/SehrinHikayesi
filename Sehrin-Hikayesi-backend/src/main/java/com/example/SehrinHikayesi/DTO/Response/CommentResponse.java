package com.example.SehrinHikayesi.DTO.Response;

import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private Long storyId;

    public CommentResponse(Long id, String content, String username, LocalDateTime createdAt, Long storyId) {
        this.id = id;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
        this.storyId = storyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }
}
