package com.example.SehrinHikayesi.Service.AbstactService;

import com.example.SehrinHikayesi.DTO.Request.StoryRequest;
import com.example.SehrinHikayesi.DTO.Response.StoryResponse;
import com.example.SehrinHikayesi.Entity.Story;
import com.example.SehrinHikayesi.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public abstract class AbstractStoryService {
    public abstract StoryResponse getById(Long id);
    public abstract List<StoryResponse> getAll();
    public abstract StoryResponse create(StoryRequest request);
    public abstract void delete(Long id,String username, boolean isAdmin);
    public abstract StoryResponse update(Long id, StoryRequest request);


    public abstract void uploadFile(Long storyId, MultipartFile file);
    public abstract List<StoryResponse> getStoriesByCity(Long cityId);
    public abstract long countStories();
    public abstract long countStoriesCreatedToday();
    public abstract Page<StoryResponse> getAllPaginated(int page, int size);
    public abstract List<StoryResponse> getAllStories();
    public abstract List<StoryResponse> getRandomStories();
    public abstract List<StoryResponse> getStoriesByCurrentUser();
    // AbstractStoryService.java
    public abstract List<StoryResponse> getStoriesByUsername(String username);
    public abstract List<StoryResponse> getStoriesByTagName(String tagName);
    public abstract List<StoryResponse> getStoriesByCategoryName(String categoryName);





}
