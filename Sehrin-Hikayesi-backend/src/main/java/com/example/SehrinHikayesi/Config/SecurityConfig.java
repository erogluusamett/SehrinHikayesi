package com.example.SehrinHikayesi.Config;

import com.example.SehrinHikayesi.Security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)//saldırı
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("http://localhost:3000"));
                    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    configuration.setAllowCredentials(true);
                    return configuration;
                }))
                .authorizeHttpRequests(auth -> auth

                        // Giriş ve kayıt işlemleri serbest
                        .requestMatchers("/api/auth/**").permitAll()

                        //  Public profile ve anılar (herkes erişebilir)
                        .requestMatchers("/api/users/username/**").permitAll()
                        .requestMatchers("/api/stories/by-username/**").permitAll()

                        //  Görsel ve konum oluşturma açık
                        .requestMatchers("/uploads/**", "/api/locations/create").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/users/update-profile").permitAll()
                                .requestMatchers("/api/stories/**").permitAll()

                        // Anı oluşturma & görsel yükleme (girişli kullanıcı)
                        .requestMatchers(HttpMethod.POST, "/api/stories/create").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/stories/upload-image/**").authenticated()
                        // Ziyaretçilere açık story endpointleri
//                        .requestMatchers(HttpMethod.GET, "/api/stories/random").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/stories/by-city/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/stories/**").permitAll()



                        //  Beğeni işlemleri (girişli kullanıcı)
                        .requestMatchers(HttpMethod.POST, "/api/likes/create").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/likes/exists").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/likes/unlike").authenticated()

                        //  Yorum işlemleri (herkes yorum yapabilsin)
                        .requestMatchers(HttpMethod.POST, "/api/comments").permitAll()
                        .requestMatchers("/api/comments/**").permitAll()

                        //  Arama işlemleri
                        .requestMatchers("/api/search/**").permitAll().requestMatchers("/api/users/username/**").permitAll()

                        //  Sayı gösterimleri
                        .requestMatchers(
                                "/api/cities/count",
                                "/api/categories/count",
                                "/api/tags/count",
                                "/api/users/count",
                                "/api/stories/count",
                                "/api/users/count/today",
                                "/api/stories/count/today"
                        ).permitAll()

                        //  Etiket ve kategori işlemleri
                        .requestMatchers("/api/tags/**", "/api/categories/**").permitAll()

                        // Kullanıcı işlemleri
                        .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority("ROLE_ADMIN")

                        // 🗑 Anı silme (sadece girişli kullanıcı)
                        .requestMatchers(HttpMethod.DELETE, "/api/stories/**").authenticated()

                        // Kategori oluşturma (sadece admin)
                        .requestMatchers(HttpMethod.POST, "/api/categories/create").hasAuthority("ROLE_ADMIN")

                        //  Diğer tüm endpoint'ler için giriş zorunlu
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
