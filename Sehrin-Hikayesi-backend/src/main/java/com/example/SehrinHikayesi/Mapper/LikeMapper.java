/*
package com.example.SehrinHikayesi.Mapper;

import com.example.SehrinHikayesi.DTO.Request.LikeRequest;
import com.example.SehrinHikayesi.DTO.Response.LikeResponse;
import com.example.SehrinHikayesi.Entity.Like;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {
    public Like toEntity(LikeRequest dto) {
        Like like = new Like();
        return like;
    }

    public LikeResponse toResponse(Like like) {
        return new LikeResponse(
                like.getId(),
                like.getUser().getUsername(),
                like.getStory().getId(),
                like.getLikedAt()
        );
    }
}*/
package com.example.SehrinHikayesi.Mapper;

import com.example.SehrinHikayesi.DTO.Request.LikeRequest;
import com.example.SehrinHikayesi.DTO.Response.LikeResponse;
import com.example.SehrinHikayesi.Entity.Like;
import com.example.SehrinHikayesi.Repository.StoryRepository;
import com.example.SehrinHikayesi.Repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

    private final UserRepository userRepository;
    private final StoryRepository storyRepository;

    public LikeMapper(UserRepository userRepository, StoryRepository storyRepository) {
        this.userRepository = userRepository;
        this.storyRepository = storyRepository;
    }

    public Like toEntity(LikeRequest dto) {
        Like like = new Like();
        like.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı")));
        like.setStory(storyRepository.findById(dto.getStoryId()).orElseThrow(() -> new RuntimeException("Anı bulunamadı")));
        return like;
    }

    public LikeResponse toResponse(Like like) {
        return new LikeResponse(
                like.getId(),
                like.getUser().getUsername(),
                like.getStory().getId(),
                like.getLikedAt()
        );
    }
}
