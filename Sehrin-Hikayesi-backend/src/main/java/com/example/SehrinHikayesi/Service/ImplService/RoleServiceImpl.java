package com.example.SehrinHikayesi.Service.ImplService;

import com.example.SehrinHikayesi.DTO.Request.RoleRequest;
import com.example.SehrinHikayesi.DTO.Response.RoleResponse;
import com.example.SehrinHikayesi.Entity.Role;
import com.example.SehrinHikayesi.Mapper.RoleMapper;
import com.example.SehrinHikayesi.Repository.RoleRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends AbstractRoleService {
    private final RoleRepository repository;
    private final RoleMapper mapper;

    public RoleServiceImpl(RoleRepository repository, RoleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public RoleResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse).orElseThrow();
    }
    public List<RoleResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
    public RoleResponse create(RoleRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }
    public RoleResponse update(Long id, RoleRequest request) {
        Role entity = repository.findById(id).orElseThrow();
        entity.setRoleName(request.getRoleName());
        return mapper.toResponse(repository.save(entity));
    }
    public void delete(Long id) {
        Role role = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı."));

        if (!role.getUsers().isEmpty()) {
            throw new RuntimeException("Bu rolü kullanan kullanıcılar var. Önce kullanıcıların rolünü kaldırın.");
        }

        repository.deleteById(id);
    }

}
