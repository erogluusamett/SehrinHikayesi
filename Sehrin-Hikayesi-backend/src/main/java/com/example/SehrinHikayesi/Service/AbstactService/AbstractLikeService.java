/*
package com.example.SehrinHikayesi.Service.AbstactService;

import com.example.SehrinHikayesi.DTO.Request.LikeRequest;
import com.example.SehrinHikayesi.DTO.Response.LikeResponse;

import java.util.List;

public abstract class AbstractLikeService {
    public abstract LikeResponse getById(Long id);
    public abstract List<LikeResponse> getAll();
    public abstract LikeResponse create(LikeRequest request);
    public abstract LikeResponse update(Long id, LikeRequest request);
    public abstract void delete(Long id);
}
*/
package com.example.SehrinHikayesi.Service.AbstactService;

import com.example.SehrinHikayesi.DTO.Request.LikeRequest;
import com.example.SehrinHikayesi.DTO.Response.LikeResponse;

import java.util.List;

public abstract class AbstractLikeService {
    public abstract LikeResponse getById(Long id);
    public abstract List<LikeResponse> getAll();
    public abstract LikeResponse create(LikeRequest request);
    public abstract LikeResponse update(Long id, LikeRequest request);
    public abstract void delete(Long id);
}

