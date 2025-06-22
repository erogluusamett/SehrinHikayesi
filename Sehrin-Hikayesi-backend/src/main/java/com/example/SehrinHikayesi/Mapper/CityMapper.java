package com.example.SehrinHikayesi.Mapper;

import com.example.SehrinHikayesi.DTO.Request.CityRequest;
import com.example.SehrinHikayesi.DTO.Response.CityResponse;
import com.example.SehrinHikayesi.Entity.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    public City toEntity(CityRequest dto) {
        City city = new City();
        city.setName(dto.getName());
        return city;
    }

    public CityResponse toResponse(City city) {
        return new CityResponse(city.getId(), city.getName(),city.getStories().stream().count());
    }

    public CityResponse toResponse(Long id, String name, Long storyCount) {
        CityResponse response = new CityResponse();
        response.setId(id);
        response.setName(name);
        response.setStoryCount(storyCount);
        return response;
    }
}
