package com.example.SehrinHikayesi.Mapper;

import com.example.SehrinHikayesi.DTO.Request.StoryRequest;
import com.example.SehrinHikayesi.DTO.Response.StoryResponse;
import com.example.SehrinHikayesi.Entity.City;
import com.example.SehrinHikayesi.Entity.Story;
import com.example.SehrinHikayesi.Entity.Tag;
import com.example.SehrinHikayesi.Entity.User;
import com.example.SehrinHikayesi.Repository.*;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class StoryMapper {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final TagRepository tagRepository;

    public StoryMapper(UserRepository userRepository,
                       CityRepository cityRepository,
                       CategoryRepository categoryRepository,
                       LocationRepository locationRepository,
                       TagRepository tagRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
        this.tagRepository = tagRepository;
    }

    public Story toEntity(StoryRequest dto) {
        Story story = new Story();
        story.setTitle(dto.getTitle());
        story.setContent(dto.getContent());

        if (dto.getAuthorId() == null) {
            throw new IllegalArgumentException("Yazar ID boş olamaz");
        }
        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Yazar (User) bulunamadı: " + dto.getAuthorId()));
        story.setAuthor(author);

        if (dto.getCityId() == null) {
            throw new IllegalArgumentException("Şehir ID boş olamaz");
        }
        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new RuntimeException("Şehir bulunamadı: " + dto.getCityId()));
        story.setCity(city);

        if (dto.getCategoryId() != null) {
            categoryRepository.findById(dto.getCategoryId())
                    .ifPresent(story::setCategory);
        }

        if (dto.getLocationId() != null) {
            locationRepository.findById(dto.getLocationId())
                    .ifPresent(story::setLocation);
        }

        if (dto.getTagIds() != null && !dto.getTagIds().isEmpty()) {
            story.setTags(dto.getTagIds().stream()
                    .filter(tagId -> tagId != null)
                    .map(tagId -> tagRepository.findById(tagId)
                            .orElseThrow(() -> new RuntimeException("Tag bulunamadı: " + tagId)))
                    .collect(Collectors.toSet()));
        } else {
            story.setTags(new HashSet<>());
        }

        return story;
    }

    public StoryResponse toResponse(Story story) {
        StoryResponse res = new StoryResponse();
        res.setId(story.getId());
        res.setTitle(story.getTitle());
        res.setContent(story.getContent());
        res.setAuthorUsername(story.getAuthor().getUsername());
        res.setCity(story.getCity().getName());

        if (story.getCategory() != null) {
            res.setCategory(story.getCategory().getName());
        }

        if (story.getTags() != null) {
            res.setTags(story.getTags().stream()
                    .map(Tag::getName)
                    .collect(Collectors.toList()));
        }

        if (story.getAttachments() != null && !story.getAttachments().isEmpty()) {
            String fileName = story.getAttachments().iterator().next().getFileName();
            res.setImageUrl("http://localhost:8585/uploads/" + fileName);
        }


        if (story.getCreatedAt() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM", new java.util.Locale("tr", "TR"));
            String formattedDate = story.getCreatedAt().format(formatter);
            res.setCreatedAt(formattedDate);  // DTO'ya aktarıyoruz
        }

        return res;
    }
}
