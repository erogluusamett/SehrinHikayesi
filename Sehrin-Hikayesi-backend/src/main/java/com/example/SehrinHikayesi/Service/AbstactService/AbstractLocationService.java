package com.example.SehrinHikayesi.Service.AbstactService;

import com.example.SehrinHikayesi.DTO.Request.LocationRequest;
import com.example.SehrinHikayesi.DTO.Response.LocationResponse;

import java.util.List;

public abstract class AbstractLocationService {
    public abstract LocationResponse getById(Long id);
    public abstract List<LocationResponse> getAll();
    public abstract LocationResponse create(LocationRequest request);
    public abstract LocationResponse update(Long id, LocationRequest request);
    public abstract void delete(Long id);
}
