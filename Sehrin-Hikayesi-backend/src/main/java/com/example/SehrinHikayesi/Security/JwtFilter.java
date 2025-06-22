package com.example.SehrinHikayesi.Security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String requestURI = request.getRequestURI();
        System.out.println("İşlenen URI: " + requestURI);

        final String authHeader = request.getHeader("Authorization");
        System.out.println(" Auth Header: " + (authHeader != null ? authHeader.substring(0, Math.min(15, authHeader.length())) + "..." : "null"));

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println(" Auth header yok veya geçersiz format. İstek devam ediyor...");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        try {
            final String username = jwtUtil.extractUsername(jwt);
            System.out.println(" Çıkartılan Kullanıcı: " + username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (Boolean.TRUE.equals(jwtUtil.validateToken(jwt))) {
                    System.out.println("Token doğrulandı");

                    // 🔥 JWT'den rolü al ve SimpleGrantedAuthority olarak ata
                    Claims claims = jwtUtil.getClaims(jwt);
                    String role = claims.get("role", String.class);
                    System.out.println("JWT içindeki rol: " + role);

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            List.of(new SimpleGrantedAuthority(role))
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    System.out.println("SecurityContext güncellendi. Yetkilendirme tamam.");
                } else {
                    System.out.println(" Token doğrulanamadı");
                }
            } else {
                System.out.println("Kullanıcı adı null veya SecurityContext zaten authentication içeriyor");
            }
        } catch (Exception e) {
            System.out.println(" JWT işleme hatası: " + e.getMessage());
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth");
    }
}
