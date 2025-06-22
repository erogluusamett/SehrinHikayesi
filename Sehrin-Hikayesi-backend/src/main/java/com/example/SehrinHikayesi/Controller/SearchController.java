/*
package com.example.SehrinHikayesi.Controller;

import com.example.SehrinHikayesi.Repository.CategoryRepository;
import com.example.SehrinHikayesi.Repository.CityRepository;
import com.example.SehrinHikayesi.Repository.TagRepository;
import com.example.SehrinHikayesi.Repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final UserRepository userRepo;
    private final CityRepository cityRepo;
    private final CategoryRepository categoryRepo;
    private final TagRepository tagRepo;

    public SearchController(UserRepository userRepo, CityRepository cityRepo, CategoryRepository categoryRepo, TagRepository tagRepo) {
        this.userRepo = userRepo;
        this.cityRepo = cityRepo;
        this.categoryRepo = categoryRepo;
        this.tagRepo = tagRepo;
    }

    @GetMapping
    public List<Map<String, Object>> search(@RequestParam("q") String query) {
        List<Map<String, Object>> results = new ArrayList<>();

        userRepo.findByUsernameContainingIgnoreCase(query)
                .forEach(u -> results.add(Map.of("type", "Kullanıcı", "username", u.getUsername())));

        cityRepo.findByNameContainingIgnoreCase(query)
                .forEach(c -> results.add(Map.of("type", "Şehir", "name", c.getName())));

        categoryRepo.findByNameContainingIgnoreCase(query)
                .forEach(c -> results.add(Map.of("type", "Kategori", "name", c.getName())));

        tagRepo.findByNameContainingIgnoreCase(query)
                .forEach(t -> results.add(Map.of("type", "Etiket", "name", t.getName())));

        return results;
    }
}

*/
package com.example.SehrinHikayesi.Controller;

import com.example.SehrinHikayesi.DTO.Response.StoryResponse;
import com.example.SehrinHikayesi.Entity.Category;
import com.example.SehrinHikayesi.Entity.Tag;
import com.example.SehrinHikayesi.Entity.User;
import com.example.SehrinHikayesi.Repository.CategoryRepository;
import com.example.SehrinHikayesi.Repository.CityRepository;
import com.example.SehrinHikayesi.Repository.TagRepository;
import com.example.SehrinHikayesi.Repository.UserRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractStoryService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final UserRepository userRepo;
    private final CityRepository cityRepo;
    private final CategoryRepository categoryRepo;
    private final TagRepository tagRepo;
    private final AbstractStoryService service;

    public SearchController(UserRepository userRepo, CityRepository cityRepo, CategoryRepository categoryRepo, TagRepository tagRepo, AbstractStoryService service) {
        this.userRepo = userRepo;
        this.cityRepo = cityRepo;
        this.categoryRepo = categoryRepo;
        this.tagRepo = tagRepo;
        this.service = service;
    }

    //  Genel arama (her şeyde)
    @GetMapping
    public List<Map<String, Object>> searchAll(@RequestParam("q") String query) {
        List<Map<String, Object>> results = new ArrayList<>();

        userRepo.findByUsernameContainingIgnoreCase(query)
                .forEach(u -> results.add(Map.of("type", "Kullanıcı", "username", u.getUsername())));

        cityRepo.findByNameContainingIgnoreCase(query)
                .forEach(c -> results.add(Map.of("type", "Şehir", "name", c.getName())));

        categoryRepo.findByNameContainingIgnoreCase(query)
                .forEach(c -> results.add(Map.of("type", "Kategori", "name", c.getName())));

        tagRepo.findByNameContainingIgnoreCase(query)
                .forEach(t -> results.add(Map.of("type", "Etiket", "name", t.getName())));

        return results;
    }

    // Kullanıcı adına göre arama
    @GetMapping("/users")
    public List<User> searchUsers(@RequestParam("query") String query) {
        return userRepo.findByUsernameContainingIgnoreCase(query);
    }



}
