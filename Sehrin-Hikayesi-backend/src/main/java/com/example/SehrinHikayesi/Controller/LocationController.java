package com.example.SehrinHikayesi.Controller;

import com.example.SehrinHikayesi.DTO.Request.LocationRequest;
import com.example.SehrinHikayesi.DTO.Response.LocationResponse;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractLocationService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final AbstractLocationService service;

    public LocationController(AbstractLocationService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<LocationResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<LocationResponse> create(@RequestBody LocationRequest request, Authentication authentication) {
        System.out.println("Kimlik: " + authentication);
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponse> update(@PathVariable Long id, @RequestBody LocationRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
