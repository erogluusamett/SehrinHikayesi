package com.example.SehrinHikayesi.Service.ImplService;

import com.example.SehrinHikayesi.DTO.Request.SessionTokenRequest;
import com.example.SehrinHikayesi.DTO.Response.SessionTokenResponse;
import com.example.SehrinHikayesi.Entity.SessionToken;
import com.example.SehrinHikayesi.Mapper.SessionTokenMapper;
import com.example.SehrinHikayesi.Repository.SessionTokenRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractSessionTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionTokenServiceImpl extends AbstractSessionTokenService {
    private final SessionTokenRepository repository;
    private final SessionTokenMapper mapper;

    public SessionTokenServiceImpl(SessionTokenRepository repository, SessionTokenMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public SessionTokenResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse).orElseThrow();
    }
    public List<SessionTokenResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
    public SessionTokenResponse create(SessionTokenRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }
    public SessionTokenResponse update(Long id, SessionTokenRequest request) {
        SessionToken entity = repository.findById(id).orElseThrow();
        entity.setToken(request.getToken());
        return mapper.toResponse(repository.save(entity));
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
