/*
package com.example.SehrinHikayesi.Service.ImplService;

import com.example.SehrinHikayesi.DTO.Request.UserRequest;
import com.example.SehrinHikayesi.DTO.Response.UserResponse;
import com.example.SehrinHikayesi.Entity.Profile;
import com.example.SehrinHikayesi.Entity.User;
import com.example.SehrinHikayesi.Mapper.UserMapper;
import com.example.SehrinHikayesi.Repository.LikeRepository;
import com.example.SehrinHikayesi.Repository.UserRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractUserService;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractFileAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserServiceImpl extends AbstractUserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final LikeRepository likeRepository;
    private final AbstractFileAttachmentService fileAttachmentService;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, LikeRepository likeRepository, AbstractFileAttachmentService fileAttachmentService) {
        this.repository = repository;
        this.mapper = mapper;
        this.likeRepository = likeRepository;
        this.fileAttachmentService = fileAttachmentService;
    }

    @Override
    public UserResponse create(UserRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Override
    public List<UserResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public UserResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse).orElseThrow();
    }

    @Override
    public void delete(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        if (!user.getStories().isEmpty()) {
            throw new RuntimeException("Bu kullanıcıya ait hikayeler var. Önce onları silin.");
        }

        if (!user.getComments().isEmpty()) {
            throw new RuntimeException("Bu kullanıcıya ait yorumlar var. Önce onları silin.");
        }

        if (likeRepository.existsByUserId(id)) {
            throw new RuntimeException("Bu kullanıcıya ait beğeniler var. Önce onları silin.");
        }

        repository.deleteById(id);
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {
        User user = repository.findById(id).orElseThrow();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return mapper.toResponse(repository.save(user));
    }

    @Override
    public long countUsers() {
        return repository.count();
    }

    @Override
    public long countUsersCreatedToday() {
        return repository.countUsersCreatedToday();
    }

    @Override
    public Page<UserResponse> getAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public UserResponse getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        return mapper.toResponse(user);
    }

    @Override
    public UserResponse updateProfile(MultipartFile image) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Profile profile = user.getProfile();
        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
        }

        if (image != null && !image.isEmpty()) {
            fileAttachmentService.upload(image, ""); // şehir bilgisi gerekmediği için boş
            String imageUrl = "/uploads/" + image.getOriginalFilename();
            profile.setProfileImageUrl(imageUrl);
        }

        user.setProfile(profile);
        repository.save(user);

        return mapper.toResponse(user);
    }
}
*/
package com.example.SehrinHikayesi.Service.ImplService;

import com.example.SehrinHikayesi.DTO.Request.UserRequest;
import com.example.SehrinHikayesi.DTO.Response.UserResponse;
import com.example.SehrinHikayesi.Entity.User;
import com.example.SehrinHikayesi.Mapper.UserMapper;
import com.example.SehrinHikayesi.Repository.LikeRepository;
import com.example.SehrinHikayesi.Repository.UserRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractUserService;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractFileAttachmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class UserServiceImpl extends AbstractUserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final LikeRepository likeRepository;
    private final AbstractFileAttachmentService fileAttachmentService;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, LikeRepository likeRepository, AbstractFileAttachmentService fileAttachmentService) {
        this.repository = repository;
        this.mapper = mapper;
        this.likeRepository = likeRepository;
        this.fileAttachmentService = fileAttachmentService;
    }

    @Override
    public UserResponse create(UserRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Override
    public List<UserResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public UserResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse).orElseThrow();
    }
    @Override
    public UserResponse getByUsername(String username) {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + username));
        return mapper.toResponse(user);
    }


    @Override
    public void delete(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        if (!user.getStories().isEmpty()) {
            throw new RuntimeException("Bu kullanıcıya ait hikayeler var. Önce onları silin.");
        }

        if (!user.getComments().isEmpty()) {
            throw new RuntimeException("Bu kullanıcıya ait yorumlar var. Önce onları silin.");
        }

        if (likeRepository.existsByUserId(id)) {
            throw new RuntimeException("Bu kullanıcıya ait beğeniler var. Önce onları silin.");
        }

        repository.deleteById(id);
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {
        User user = repository.findById(id).orElseThrow();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return mapper.toResponse(repository.save(user));
    }

    @Override
    public long countUsers() {
        return repository.count();
    }

    @Override
    public long countUsersCreatedToday() {
        return repository.countUsersCreatedToday();
    }

    @Override
    public Page<UserResponse> getAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public UserResponse getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        return mapper.toResponse(user);
    }


    @Override
    public UserResponse updateProfile(MultipartFile image) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (image != null && !image.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            String uploadDir = "uploads/profile/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            try {
                Path filePath = Paths.get(uploadDir, fileName);
                Files.write(filePath, image.getBytes());
                user.setProfileImage(fileName);
            } catch (IOException e) {
                throw new RuntimeException("Profil fotoğrafı yüklenemedi: " + e.getMessage());
            }
        }

        repository.save(user);
        return mapper.toResponse(user);
    }
}
