package com.example.SehrinHikayesi.Service.ImplService;

import com.example.SehrinHikayesi.DTO.Request.ProfileRequest;
import com.example.SehrinHikayesi.DTO.Response.ProfileResponse;
import com.example.SehrinHikayesi.Entity.Profile;
import com.example.SehrinHikayesi.Mapper.ProfileMapper;
import com.example.SehrinHikayesi.Repository.ProfileRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl extends AbstractProfileService {
    private final ProfileRepository repository;
    private final ProfileMapper mapper;

    public ProfileServiceImpl(ProfileRepository repository, ProfileMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ProfileResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse).orElseThrow();
    }
    public List<ProfileResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
    public ProfileResponse create(ProfileRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }
    public ProfileResponse update(Long id, ProfileRequest request) {
        Profile entity = repository.findById(id).orElseThrow();
        entity.setBio(request.getBio());
        entity.setProfileImageUrl(request.getProfileImageUrl());
        return mapper.toResponse(repository.save(entity));
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
