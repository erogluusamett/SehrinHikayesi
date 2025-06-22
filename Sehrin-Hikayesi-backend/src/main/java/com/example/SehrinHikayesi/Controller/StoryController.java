package com.example.SehrinHikayesi.Controller;

import com.example.SehrinHikayesi.DTO.Request.StoryRequest;
import com.example.SehrinHikayesi.DTO.Response.StoryResponse;
import com.example.SehrinHikayesi.Repository.StoryRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractStoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    private final AbstractStoryService service;
    private final StoryRepository repository;

    public StoryController(AbstractStoryService service, StoryRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<StoryResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Sadece giriş yapmış kullanıcılar anı oluşturabilir
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public ResponseEntity<StoryResponse> create(@RequestBody StoryRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    //  Sadece giriş yapmış kullanıcılar fotoğraf yükleyebilir
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/upload-image/{storyId}")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long storyId,
            @RequestParam("file") MultipartFile file) {
        try {
            service.uploadFile(storyId, file);
            return ResponseEntity.ok("Dosya başarıyla yüklendi");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Yükleme hatası: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoryResponse> update(@PathVariable Long id, @RequestBody StoryRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        service.delete(id, username, isAdmin);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-city/{cityId}")
    public List<StoryResponse> getStoriesByCity(@PathVariable Long cityId) {
        return service.getStoriesByCity(cityId);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countStories() {
        return ResponseEntity.ok(service.countStories());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/count/today")
    public ResponseEntity<Long> countStoriesCreatedToday() {
        return ResponseEntity.ok(service.countStoriesCreatedToday());
    }

    @GetMapping("/all")
    public List<StoryResponse> getAllStories() {
        return service.getAllStories();
    }

    @GetMapping("/random")
    public List<StoryResponse> getRandomStories() {
        return service.getRandomStories();
    }

    @GetMapping("/paginated")
    public Page<StoryResponse> getStoriesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.getAllPaginated(page, size);
    }
    @GetMapping("/by-username/{username}")
    public ResponseEntity<List<StoryResponse>> getStoriesByUsername(@PathVariable String username) {
        return ResponseEntity.ok(service.getStoriesByUsername(username));
    }


    @GetMapping("/user")
    public ResponseEntity<List<StoryResponse>> getUserStories() {
        return ResponseEntity.ok(service.getStoriesByCurrentUser());
    }
    @GetMapping("/by-tag/{tagName}")
    public List<StoryResponse> getStoriesByTag(@PathVariable String tagName) {
        return service.getStoriesByTagName(tagName);
    }

    @GetMapping("/by-category/{categoryName}")
    public List<StoryResponse> getStoriesByCategory(@PathVariable String categoryName) {
        return service.getStoriesByCategoryName(categoryName);
    }

}
