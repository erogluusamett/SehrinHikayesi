package com.example.SehrinHikayesi.Service.AbstactService;

import com.example.SehrinHikayesi.DTO.Request.ProfileRequest;
import com.example.SehrinHikayesi.DTO.Response.ProfileResponse;

import java.util.List;

public abstract class AbstractProfileService {
    public abstract ProfileResponse getById(Long id);
    public abstract List<ProfileResponse> getAll();
    public abstract ProfileResponse create(ProfileRequest request);
    public abstract ProfileResponse update(Long id, ProfileRequest request);
    public abstract void delete(Long id);
}
