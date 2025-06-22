/*
package com.example.SehrinHikayesi.Controller;

import com.example.SehrinHikayesi.DTO.Request.LikeRequest;
import com.example.SehrinHikayesi.DTO.Response.LikeResponse;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")

public class LikeController {
    private final AbstractLikeService service;


    public LikeController(AbstractLikeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LikeResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<LikeResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<LikeResponse> create(@RequestBody LikeRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LikeResponse> update(@PathVariable Long id, @RequestBody LikeRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
*/
package com.example.SehrinHikayesi.Controller;

import com.example.SehrinHikayesi.DTO.Request.LikeRequest;
import com.example.SehrinHikayesi.DTO.Response.LikeResponse;
import com.example.SehrinHikayesi.Repository.LikeRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;


import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {
    private final LikeRepository repository;


    private final AbstractLikeService service;

    public LikeController(LikeRepository repository, AbstractLikeService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LikeResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<LikeResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<LikeResponse> create(@RequestBody LikeRequest request) {
        return ResponseEntity.ok(service.create(request));
    }
    @GetMapping("/exists")
    public ResponseEntity<Boolean> hasUserLiked(@RequestParam Long userId, @RequestParam Long storyId) {
        return ResponseEntity.ok(repository.existsByUserIdAndStoryId(userId, storyId));
    }
    @GetMapping("/count")
    public ResponseEntity<Long> countLikes(@RequestParam Long storyId) {
        return ResponseEntity.ok(repository.countByStoryId(storyId));
    }
    @GetMapping("/users")
    public ResponseEntity<List<String>> getUsernamesWhoLiked(@RequestParam Long storyId) {
        List<String> usernames = repository.findByStoryId(storyId)
                .stream()
                .map(like -> like.getUser().getUsername())
                .toList();
        return ResponseEntity.ok(usernames);
    }



    @DeleteMapping("/unlike")
    @Transactional

    public ResponseEntity<?> unlike(@RequestParam Long userId, @RequestParam Long storyId) {
        repository.deleteByUserIdAndStoryId(userId, storyId);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<LikeResponse> update(@PathVariable Long id, @RequestBody LikeRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
