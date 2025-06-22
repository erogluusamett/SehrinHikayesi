package com.example.SehrinHikayesi.Service.ImplService;

import com.example.SehrinHikayesi.DTO.Request.StoryRequest;
import com.example.SehrinHikayesi.DTO.Response.StoryResponse;
import com.example.SehrinHikayesi.Entity.FileAttachment;
import com.example.SehrinHikayesi.Entity.Story;
import com.example.SehrinHikayesi.Entity.User;
import com.example.SehrinHikayesi.Mapper.StoryMapper;
import com.example.SehrinHikayesi.Repository.FileAttachmentRepository;
import com.example.SehrinHikayesi.Repository.StoryRepository;
import com.example.SehrinHikayesi.Repository.UserRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractStoryService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StoryServiceImpl extends AbstractStoryService {

    private final StoryRepository repository;
    private final StoryMapper mapper;
    private final FileAttachmentRepository fileAttachmentRepository;
    private final UserRepository userRepository;

    public StoryServiceImpl(StoryRepository repository, StoryMapper mapper, FileAttachmentRepository fileAttachmentRepository, UserRepository userRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public StoryResponse create(StoryRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Override
    public List<StoryResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public StoryResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse).orElseThrow();
    }

    @Override
    public void delete(Long id, String username, boolean isAdmin) {
        Story story = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hikaye bulunamadı."));


        if (!isAdmin && !story.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("Sadece kendi anılarınızı silebilirsiniz.");
        }

        story.getTags().clear();

        repository.delete(story);
    }


    @Override
    public StoryResponse update(Long id, StoryRequest request) {
        Story story = repository.findById(id).orElseThrow();
        story.setTitle(request.getTitle());
        story.setContent(request.getContent());
        return mapper.toResponse(repository.save(story));
    }
    @Override
    public List<StoryResponse> getAllStories() {
        return repository.findAll().stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<StoryResponse> getStoriesByCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        return repository.findAllByAuthor(user)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public List<StoryResponse> getRandomStories() {
        List<Story> allStories = repository.findAll();
        Collections.shuffle(allStories);
        return allStories.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public void uploadFile(Long storyId, MultipartFile file) {
        Story story = repository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Hikaye bulunamadı."));


        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) uploadPath.mkdirs();

        String originalName = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9.\\-_]", "_");
        String fileName = UUID.randomUUID() + "_" + originalName;
        File filePath = new File(uploadDir + fileName);

        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            e.printStackTrace(); // Logla
            throw new RuntimeException("Dosya yüklenemedi.", e);
        }

        FileAttachment attachment = new FileAttachment();
        attachment.setFileName(fileName);
        attachment.setFilePath(filePath.getAbsolutePath());
        attachment.setStory(story);

        fileAttachmentRepository.save(attachment);
    }

    public List<StoryResponse> getStoriesByCity(Long cityId) {
        List<Story> stories = repository.findByCityId(cityId);
        return stories.stream().map(mapper::toResponse).collect(Collectors.toList());
    }
    public long countStories() {
        return repository.count();
    }

    @Override
    public long countStoriesCreatedToday() {
        return repository.countStoriesCreatedToday();
    }

    @Override
    public List<StoryResponse> getStoriesByUsername(String username) {
        List<Story> stories = repository.findByAuthorUsername(username);
        return stories.stream().map(mapper::toResponse).toList();
    }

    public Page<StoryResponse> getAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public List<StoryResponse> getStoriesByTagName(String tagName) {
        List<Story> stories = repository.findByTagNameContainsIgnoreCase(tagName);
        return stories.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<StoryResponse> getStoriesByCategoryName(String categoryName) {
        List<Story> stories = repository.findByCategory_NameIgnoreCase(categoryName);
        return stories.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }




}
