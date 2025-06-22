package com.example.SehrinHikayesi.Service.ImplService;

import com.example.SehrinHikayesi.DTO.Request.CityRequest;
import com.example.SehrinHikayesi.DTO.Response.CityResponse;
import com.example.SehrinHikayesi.Entity.City;
import com.example.SehrinHikayesi.Mapper.CityMapper;
import com.example.SehrinHikayesi.Repository.CategoryRepository;
import com.example.SehrinHikayesi.Repository.CityRepository;
import com.example.SehrinHikayesi.Repository.StoryRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractCityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl extends AbstractCityService {
    private final CityRepository repository;
    private final CityMapper mapper;
    private final CategoryRepository categoryRepository;
    private final StoryRepository storyRepository;

    public CityServiceImpl(CityRepository repository, CityMapper mapper, CategoryRepository categoryRepository, StoryRepository storyRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
        this.storyRepository = storyRepository;
    }

    public CityResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse).orElseThrow();
    }
    public List<CityResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
    public CityResponse create(CityRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }
    public CityResponse update(Long id, CityRequest request) {
        City entity = repository.findById(id).orElseThrow();
        entity.setName(request.getName());
        return mapper.toResponse(repository.save(entity));
    }
    public void delete(Long id) {
        City city = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Şehir bulunamadı."));


        if (storyRepository.existsByCityId(id)) {
            throw new RuntimeException("Bu şehre bağlı hikayeler var. Önce hikayeleri silin.");
        }


        if (categoryRepository.existsByCityId(id)) {
            throw new RuntimeException("Bu şehre bağlı kategoriler var. Önce onları silin.");
        }

        repository.deleteById(id);
    }
    public long countCities() {
        return repository.count();
    }



}
