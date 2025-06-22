package com.example.SehrinHikayesi.Service.AbstactService;

import com.example.SehrinHikayesi.DTO.Request.CityRequest;
import com.example.SehrinHikayesi.DTO.Response.CityResponse;

import java.util.List;

public abstract class AbstractCityService {
    public abstract CityResponse getById(Long id);
    public abstract List<CityResponse> getAll();
    public abstract CityResponse create(CityRequest request);
    public abstract CityResponse update(Long id, CityRequest request);
    public abstract void delete(Long id);
    public abstract long countCities();




}
