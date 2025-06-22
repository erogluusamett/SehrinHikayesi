package com.example.SehrinHikayesi.Mapper;

import com.example.SehrinHikayesi.DTO.Request.RoleRequest;
import com.example.SehrinHikayesi.DTO.Response.RoleResponse;
import com.example.SehrinHikayesi.Entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public Role toEntity(RoleRequest dto) {
        Role role = new Role();
        role.setRoleName(dto.getRoleName());
        return role;
    }

    public RoleResponse toResponse(Role role) {
        return new RoleResponse(role.getId(), role.getRoleName());
    }
}
