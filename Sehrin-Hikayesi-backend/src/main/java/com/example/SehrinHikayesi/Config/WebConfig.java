package com.example.SehrinHikayesi.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // "uploads/" klasörünü http://localhost:8585/uploads/... adresine aç
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // React'in çalıştığı portu burada belirtiyoruz
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // React'in çalıştığı port
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);

    }

}
