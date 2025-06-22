package com.example.SehrinHikayesi.Service.AbstactService;

import com.example.SehrinHikayesi.DTO.Request.UserRequest;
import com.example.SehrinHikayesi.DTO.Response.UserResponse;
import org.springframework.data.domain.Page;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public abstract class AbstractUserService {
    public abstract UserResponse getById(Long id);
    public abstract List<UserResponse> getAll();
    public abstract UserResponse create(UserRequest request);
    public abstract void delete(Long id);
    public abstract UserResponse update(Long id, UserRequest request);
    public abstract long countUsers();
    public abstract long countUsersCreatedToday();
    public abstract Page<UserResponse> getAllPaginated(int page, int size);


    public abstract UserResponse getCurrentUser();
    public abstract UserResponse updateProfile(MultipartFile image);
    public abstract UserResponse getByUsername(String username);

}