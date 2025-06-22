package com.example.SehrinHikayesi.Service.ImplService;

import com.example.SehrinHikayesi.DTO.Request.TagRequest;
import com.example.SehrinHikayesi.DTO.Response.TagResponse;
import com.example.SehrinHikayesi.Entity.Tag;
import com.example.SehrinHikayesi.Mapper.TagMapper;
import com.example.SehrinHikayesi.Repository.TagRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl extends AbstractTagService {
    private final TagRepository repository;
    private final TagMapper mapper;

    public TagServiceImpl(TagRepository repository, TagMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public TagResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse).orElseThrow();
    }
    public List<TagResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
    public TagResponse create(TagRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }
    public TagResponse update(Long id, TagRequest request) {
        Tag entity = repository.findById(id).orElseThrow();
        entity.setName(request.getName());
        return mapper.toResponse(repository.save(entity));
    }
    public void delete(Long id) {
        Tag tag = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etiket bulunamadı."));

        if (!tag.getStories().isEmpty()) {
            throw new RuntimeException("Bu etiket hikayelerde kullanılıyor. Önce ilişkileri kaldırın.");
        }

        repository.deleteById(id);
    }

    @Override
    public List<TagResponse> searchByName(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
    public long countTags() {
        return repository.count();
    }
}