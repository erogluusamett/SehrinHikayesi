package com.example.SehrinHikayesi.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CityResponse {
    private Long id;
    private String name;
    private Long storyCount;

    public CityResponse(Long id, String name, Long storyCount) {
        this.id = id;
        this.name = name;
        this.storyCount = storyCount;
    }

    public CityResponse() {
    }

    public Long getId() {
        return id;
    }

    public Long getStoryCount() {
        return storyCount;
    }

    public void setStoryCount(Long storyCount) {
        this.storyCount = storyCount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
