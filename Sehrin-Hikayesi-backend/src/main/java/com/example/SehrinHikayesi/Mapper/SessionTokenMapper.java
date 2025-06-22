package com.example.SehrinHikayesi.Mapper;

import com.example.SehrinHikayesi.DTO.Request.SessionTokenRequest;
import com.example.SehrinHikayesi.DTO.Response.SessionTokenResponse;
import com.example.SehrinHikayesi.Entity.SessionToken;
import org.springframework.stereotype.Component;

@Component
public class SessionTokenMapper {
    public SessionToken toEntity(SessionTokenRequest dto) {
        SessionToken token = new SessionToken();
        token.setToken(dto.getToken());
        return token;
    }

    public SessionTokenResponse toResponse(SessionToken token) {
        return new SessionTokenResponse(token.getId(), token.getToken(), token.getCreatedAt());
    }
}