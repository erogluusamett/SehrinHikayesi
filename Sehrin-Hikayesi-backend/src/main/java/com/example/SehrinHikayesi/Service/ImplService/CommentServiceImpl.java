package com.example.SehrinHikayesi.Service.ImplService;

import com.example.SehrinHikayesi.DTO.Request.CommentRequest;
import com.example.SehrinHikayesi.DTO.Response.CommentResponse;
import com.example.SehrinHikayesi.Entity.Comment;
import com.example.SehrinHikayesi.Entity.Story;
import com.example.SehrinHikayesi.Entity.User;
import com.example.SehrinHikayesi.Mapper.CommentMapper;
import com.example.SehrinHikayesi.Repository.CommentRepository;
import com.example.SehrinHikayesi.Repository.StoryRepository;
import com.example.SehrinHikayesi.Repository.UserRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractCommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends AbstractCommentService {

    private final CommentRepository repository;
    private final CommentMapper mapper;
    private final UserRepository userRepository;
    private final StoryRepository storyRepository;

    public CommentServiceImpl(CommentRepository repository,
                              CommentMapper mapper,
                              UserRepository userRepository,
                              StoryRepository storyRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.storyRepository = storyRepository;
    }

    @Override
    public CommentResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı"));
    }

    @Override
    public List<CommentResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }
    @Override
    public List<CommentResponse> getAllByStoryId(Long storyId) {
        return repository.findAll().stream()
                .filter(comment -> comment.getStory().getId().equals(storyId))
                .map(mapper::toResponse)
                .toList();
    }


    @Override
    public CommentResponse create(CommentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        Story story = storyRepository.findById(request.getStoryId())
                .orElseThrow(() -> new RuntimeException("Hikaye bulunamadı"));

        Comment comment = mapper.toEntity(request, user, story);
        return mapper.toResponse(repository.save(comment));
    }

    @Override
    public CommentResponse update(Long id, CommentRequest request) {
        Comment comment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı"));

        comment.setContent(request.getContent());
        return mapper.toResponse(repository.save(comment));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
