package com.example.SehrinHikayesi.Controller;

import com.example.SehrinHikayesi.DTO.Request.CommentRequest;
import com.example.SehrinHikayesi.DTO.Response.CommentResponse;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final AbstractCommentService service;

    public CommentController(AbstractCommentService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // 🔄 Hem "/api/comments" hem "/api/comments/create" için çalışır
    @PostMapping({"", "/create"})
    public ResponseEntity<CommentResponse> create(@RequestBody CommentRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@PathVariable Long id, @RequestBody CommentRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/story/{storyId}")
    public ResponseEntity<List<CommentResponse>> getByStory(@PathVariable Long storyId) {
        return ResponseEntity.ok(service.getAllByStoryId(storyId));
    }
}
