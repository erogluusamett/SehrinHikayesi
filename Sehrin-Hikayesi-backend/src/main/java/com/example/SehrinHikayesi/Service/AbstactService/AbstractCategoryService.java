package com.example.SehrinHikayesi.Service.AbstactService;

import com.example.SehrinHikayesi.DTO.Request.CategoryRequest;
import com.example.SehrinHikayesi.DTO.Response.CategoryResponse;

import java.util.List;

public abstract class AbstractCategoryService {
    public abstract CategoryResponse getById(Long id);
    public abstract List<CategoryResponse> getAll();
    public abstract CategoryResponse create(CategoryRequest request);
    public abstract CategoryResponse update(Long id, CategoryRequest request);
    public abstract void delete(Long id);



    public abstract List<CategoryResponse> getCategoriesByCityId(Long cityId);


    public abstract List<CategoryResponse> getTopCategories(int limit);


    // ✅ Özel metod: Kategori adına göre ara
    public abstract List<CategoryResponse> searchCategoriesByName(String keyword);
    public abstract long countCategories();


}
