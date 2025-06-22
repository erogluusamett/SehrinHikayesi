/*
package com.example.SehrinHikayesi.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LikeRequest {
    private Long userId;
    private Long storyId;

    public LikeRequest(Long userId, Long storyId) {
        this.userId = userId;
        this.storyId = storyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }
}*/
package com.example.SehrinHikayesi.DTO.Request;

public class LikeRequest {
    private Long userId;
    private Long storyId;

    public LikeRequest() {}

    public LikeRequest(Long userId, Long storyId) {
        this.userId = userId;
        this.storyId = storyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }
}
