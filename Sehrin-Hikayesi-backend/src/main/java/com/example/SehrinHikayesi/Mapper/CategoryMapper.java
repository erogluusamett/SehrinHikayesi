package com.example.SehrinHikayesi.Mapper;

import com.example.SehrinHikayesi.DTO.Request.CategoryRequest;
import com.example.SehrinHikayesi.DTO.Response.CategoryResponse;
import com.example.SehrinHikayesi.Entity.Category;
import com.example.SehrinHikayesi.Entity.City;
import com.example.SehrinHikayesi.Repository.CityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.beans.Beans;
import java.util.Optional;

@Component
public class CategoryMapper {

    private final CityRepository cityRepository;

    public CategoryMapper(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Category toEntity(CategoryRequest dto) {
        Category category = new Category();
        category.setName(dto.getName());

        if (dto.getCity_id() != null) {
            cityRepository.findById(dto.getCity_id()).ifPresent(category::setCity);
        }

        return category;
    }


    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
