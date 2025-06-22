package com.example.SehrinHikayesi.Controller;

import com.example.SehrinHikayesi.DTO.Request.TagRequest;
import com.example.SehrinHikayesi.DTO.Response.TagResponse;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractTagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final AbstractTagService service;

    public TagController(AbstractTagService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<TagResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<TagResponse> create(@RequestBody TagRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> update(@PathVariable Long id, @RequestBody TagRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<TagResponse>> searchByName(@RequestParam String keyword) {
        return ResponseEntity.ok(service.searchByName(keyword));
    }
    @GetMapping("/count")
    public ResponseEntity<Long> countTags() {
        return ResponseEntity.ok(service.countTags());
    }

}