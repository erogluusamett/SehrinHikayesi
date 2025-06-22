package com.example.SehrinHikayesi.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class StoryRequest {
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private Long authorId;
    @NotNull
    private Long cityId;
    private Long categoryId;
    private Long locationId;
    private List<Long> tagIds;

    public StoryRequest(String title, String content, Long authorId, Long cityId, Long categoryId, Long locationId, List<Long> tagIds) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.cityId = cityId;
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.tagIds = tagIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }
}
