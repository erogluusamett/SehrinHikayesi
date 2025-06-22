package com.example.SehrinHikayesi.Service.AbstactService;

import com.example.SehrinHikayesi.DTO.Request.CommentRequest;
import com.example.SehrinHikayesi.DTO.Response.CommentResponse;

import java.util.List;

public abstract class AbstractCommentService {
    public abstract CommentResponse getById(Long id);
    public abstract List<CommentResponse> getAll();
    public abstract CommentResponse create(CommentRequest request);
    public abstract CommentResponse update(Long id, CommentRequest request);
    public abstract void delete(Long id);
    public abstract List<CommentResponse> getAllByStoryId(Long storyId);

}
