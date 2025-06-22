package com.example.SehrinHikayesi.Service.ImplService;

import com.example.SehrinHikayesi.DTO.Request.LocationRequest;
import com.example.SehrinHikayesi.DTO.Response.LocationResponse;
import com.example.SehrinHikayesi.Entity.Location;
import com.example.SehrinHikayesi.Mapper.LocationMapper;
import com.example.SehrinHikayesi.Repository.LocationRepository;
import com.example.SehrinHikayesi.Repository.StoryRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractLocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl extends AbstractLocationService {
    private final LocationRepository repository;
    private final LocationMapper mapper;
    private final StoryRepository storyRepository;

    public LocationServiceImpl(LocationRepository repository, LocationMapper mapper, StoryRepository storyRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.storyRepository = storyRepository;
    }

    public LocationResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse).orElseThrow();
    }
    public List<LocationResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
    public LocationResponse create(LocationRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }
    public LocationResponse update(Long id, LocationRequest request) {
        Location entity = repository.findById(id).orElseThrow();
        entity.setCity(request.getCity());
        entity.setDistrict(request.getDistrict());
        entity.setLatitude(request.getLatitude());
        entity.setLongitude(request.getLongitude());
        return mapper.toResponse(repository.save(entity));
    }
    public void delete(Long id) {
        Location location = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lokasyon bulunamadı."));

        if (storyRepository.existsByLocationId(id)) {
            throw new RuntimeException("Bu lokasyona bağlı hikayeler var. Önce hikayeleri silmelisiniz.");
        }

        repository.deleteById(id);
    }

}
