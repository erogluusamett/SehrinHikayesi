package com.example.SehrinHikayesi.Controller;

import com.example.SehrinHikayesi.DTO.Request.CategoryRequest;
import com.example.SehrinHikayesi.DTO.Response.CategoryResponse;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final AbstractCategoryService service;

    public CategoryController(AbstractCategoryService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-city/{cityId}")
    public ResponseEntity<List<CategoryResponse>> getByCity(@PathVariable Long cityId) {
        return ResponseEntity.ok(service.getCategoriesByCityId(cityId));
    }

    @GetMapping("/top")
    public ResponseEntity<List<CategoryResponse>> getTopCategories(@RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(service.getTopCategories(limit));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryResponse>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(service.searchCategoriesByName(name));
    }
    @GetMapping("/count")
    public ResponseEntity<Long> countCategories() {
        return ResponseEntity.ok(service.countCategories());
    }
}