package com.example.SehrinHikayesi.Service.AbstactService;

import com.example.SehrinHikayesi.DTO.Request.SessionTokenRequest;
import com.example.SehrinHikayesi.DTO.Response.SessionTokenResponse;

import java.util.List;

public abstract class AbstractSessionTokenService {
    public abstract SessionTokenResponse getById(Long id);
    public abstract List<SessionTokenResponse> getAll();
    public abstract SessionTokenResponse create(SessionTokenRequest request);
    public abstract SessionTokenResponse update(Long id, SessionTokenRequest request);
    public abstract void delete(Long id);
}
