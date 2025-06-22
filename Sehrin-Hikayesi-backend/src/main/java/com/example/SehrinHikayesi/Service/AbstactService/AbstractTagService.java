package com.example.SehrinHikayesi.Service.AbstactService;

import com.example.SehrinHikayesi.DTO.Request.TagRequest;
import com.example.SehrinHikayesi.DTO.Response.TagResponse;

import java.util.List;

public abstract class AbstractTagService {
    public abstract TagResponse getById(Long id);
    public abstract List<TagResponse> getAll();
    public abstract TagResponse create(TagRequest request);
    public abstract TagResponse update(Long id, TagRequest request);
    public abstract void delete(Long id);
    public abstract List<TagResponse> searchByName(String keyword);
    public abstract long countTags();

}