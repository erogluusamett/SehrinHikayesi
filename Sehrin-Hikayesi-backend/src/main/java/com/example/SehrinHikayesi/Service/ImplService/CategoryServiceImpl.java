package com.example.SehrinHikayesi.Service.ImplService;

import com.example.SehrinHikayesi.DTO.Request.CategoryRequest;
import com.example.SehrinHikayesi.DTO.Response.CategoryResponse;
import com.example.SehrinHikayesi.Entity.Category;
import com.example.SehrinHikayesi.Mapper.CategoryMapper;
import com.example.SehrinHikayesi.Repository.CategoryRepository;
import com.example.SehrinHikayesi.Repository.StoryRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends AbstractCategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final StoryRepository storyRepository;

    public CategoryServiceImpl(CategoryRepository repository, CategoryMapper mapper, StoryRepository storyRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.storyRepository = storyRepository;
    }

    public CategoryResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse).orElseThrow();
    }
    public List<CategoryResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
    public CategoryResponse create(CategoryRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category entity = repository.findById(id).orElseThrow();
        entity.setName(request.getName());
        return mapper.toResponse(repository.save(entity));
    }
    public void delete(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı."));

        if (storyRepository.existsByCategoryId(id)) {
            throw new RuntimeException("Bu kategoriye bağlı hikayeler var. Önce o hikayeleri silmelisiniz.");
        }

        repository.deleteById(id);
    }


    @Override
    public List<CategoryResponse> getCategoriesByCityId(Long cityId) {
        return repository.findAllByCityId(cityId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponse> getTopCategories(int limit) {
        return repository.findTopCategories(limit)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

    }

    @Override
    public List<CategoryResponse> searchCategoriesByName(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public long countCategories() {
        return repository.count();
    }
}
