package com.example.SehrinHikayesi.Controller;

import com.example.SehrinHikayesi.DTO.Request.FileAttachmentRequest;
import com.example.SehrinHikayesi.DTO.Response.FileAttachmentResponse;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractFileAttachmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileAttachmentController {

    private final AbstractFileAttachmentService service;

    public FileAttachmentController(AbstractFileAttachmentService service) {
        this.service = service;
    }

    // 📥Dosya yükleme (örneğin .jpeg, .png)
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("city") String city) {

        //  Servis katmanına yönlendir
        service.upload(file, city);

        return ResponseEntity.ok("Dosya başarıyla yüklendi.");
    }

    // ID ile dosya bilgisi getir
    @GetMapping("/{id}")
    public ResponseEntity<FileAttachmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    //  Tüm dosyaları getir
    @GetMapping("/getall")
    public ResponseEntity<List<FileAttachmentResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    //  Yeni dosya kaydı (JSON formatında)
    @PostMapping("/create")
    public ResponseEntity<FileAttachmentResponse> create(@RequestBody FileAttachmentRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    // Güncelleme
    @PutMapping("/{id}")
    public ResponseEntity<FileAttachmentResponse> update(
            @PathVariable Long id,
            @RequestBody FileAttachmentRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    // Silme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
