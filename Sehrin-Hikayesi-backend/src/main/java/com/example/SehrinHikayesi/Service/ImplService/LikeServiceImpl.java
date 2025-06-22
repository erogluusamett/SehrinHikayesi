/*
package com.example.SehrinHikayesi.Service.ImplService;

import com.example.SehrinHikayesi.DTO.Request.LikeRequest;
import com.example.SehrinHikayesi.DTO.Response.LikeResponse;
import com.example.SehrinHikayesi.Entity.Like;
import com.example.SehrinHikayesi.Mapper.LikeMapper;
import com.example.SehrinHikayesi.Repository.LikeRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractLikeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl extends AbstractLikeService {
    private final LikeRepository repository;
    private final LikeMapper mapper;

    public LikeServiceImpl(LikeRepository repository, LikeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public LikeResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse).orElseThrow();
    }
    public List<LikeResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
    public LikeResponse create(LikeRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }
    public LikeResponse update(Long id, LikeRequest request) {
        Like entity = repository.findById(id).orElseThrow();
        return mapper.toResponse(repository.save(entity));
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
*/
package com.example.SehrinHikayesi.Service.ImplService;

import com.example.SehrinHikayesi.DTO.Request.LikeRequest;
import com.example.SehrinHikayesi.DTO.Response.LikeResponse;
import com.example.SehrinHikayesi.Entity.Like;
import com.example.SehrinHikayesi.Mapper.LikeMapper;
import com.example.SehrinHikayesi.Repository.LikeRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractLikeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl extends AbstractLikeService {

    private final LikeRepository repository;
    private final LikeMapper mapper;

    public LikeServiceImpl(LikeRepository repository, LikeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public LikeResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse).orElseThrow();
    }

    public List<LikeResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public LikeResponse create(LikeRequest request) {
        if (repository.existsByUserIdAndStoryId(request.getUserId(), request.getStoryId())) {
            throw new RuntimeException("Bu kullanıcı bu anıyı zaten beğenmiş.");
        }
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    public LikeResponse update(Long id, LikeRequest request) {
        Like existing = repository.findById(id).orElseThrow();
        existing.setLikedAt(java.time.LocalDateTime.now());
        return mapper.toResponse(repository.save(existing));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
