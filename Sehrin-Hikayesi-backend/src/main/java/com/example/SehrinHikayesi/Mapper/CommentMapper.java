package com.example.SehrinHikayesi.Mapper;

import com.example.SehrinHikayesi.DTO.Request.CommentRequest;
import com.example.SehrinHikayesi.DTO.Response.CommentResponse;
import com.example.SehrinHikayesi.Entity.Comment;
import com.example.SehrinHikayesi.Entity.Story;
import com.example.SehrinHikayesi.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comment toEntity(CommentRequest dto, User user, Story story) {
        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setUser(user);
        comment.setStory(story);
        return comment;
    }

    public CommentResponse toResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getUsername(),
                comment.getCreatedAt(),
                comment.getStory().getId()

        );
    }
}

