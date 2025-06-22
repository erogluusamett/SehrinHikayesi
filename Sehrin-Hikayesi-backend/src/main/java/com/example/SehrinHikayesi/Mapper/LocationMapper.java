package com.example.SehrinHikayesi.Mapper;

import com.example.SehrinHikayesi.DTO.Request.LocationRequest;
import com.example.SehrinHikayesi.DTO.Response.LocationResponse;
import com.example.SehrinHikayesi.Entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public Location toEntity(LocationRequest dto) {
        Location location = new Location();
        location.setCity(dto.getCity());
        location.setDistrict(dto.getDistrict());
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());
        return location;
    }

    public LocationResponse toResponse(Location location) {
        return new LocationResponse(
                location.getId(),
                location.getCity(),
                location.getDistrict(),
                location.getLatitude(),
                location.getLongitude()
        );
    }
}
