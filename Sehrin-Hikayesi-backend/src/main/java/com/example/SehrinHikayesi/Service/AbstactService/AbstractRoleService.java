package com.example.SehrinHikayesi.Service.AbstactService;

import com.example.SehrinHikayesi.DTO.Request.RoleRequest;
import com.example.SehrinHikayesi.DTO.Response.RoleResponse;

import java.util.List;

public abstract class AbstractRoleService {
    public abstract RoleResponse getById(Long id);
    public abstract List<RoleResponse> getAll();
    public abstract RoleResponse create(RoleRequest request);
    public abstract RoleResponse update(Long id, RoleRequest request);
    public abstract void delete(Long id);
}