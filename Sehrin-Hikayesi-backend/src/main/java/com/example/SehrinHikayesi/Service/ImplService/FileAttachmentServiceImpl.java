package com.example.SehrinHikayesi.Service.ImplService;

import com.example.SehrinHikayesi.DTO.Request.FileAttachmentRequest;
import com.example.SehrinHikayesi.DTO.Response.FileAttachmentResponse;
import com.example.SehrinHikayesi.Entity.FileAttachment;
import com.example.SehrinHikayesi.Mapper.FileAttachmentMapper;
import com.example.SehrinHikayesi.Repository.FileAttachmentRepository;
import com.example.SehrinHikayesi.Service.AbstactService.AbstractFileAttachmentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileAttachmentServiceImpl extends AbstractFileAttachmentService {

    private final FileAttachmentRepository repository;
    private final FileAttachmentMapper mapper;

    public FileAttachmentServiceImpl(FileAttachmentRepository repository, FileAttachmentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public FileAttachmentResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse).orElseThrow();
    }
    @Override
    public String uploadProfileImage(MultipartFile file) {
        try {
            // 1. Profil dizinini oluştur
            String uploadDir = "uploads/profile/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 2. Dosya adını benzersiz yap
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            // 3. Dosyayı yaz
            Files.write(filePath, file.getBytes());

            // 4. Geriye dosya adını döndür (veritabanına kayıt işlemi yapılmayacak burada)
            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("Profil fotoğrafı yüklenemedi: " + e.getMessage());
        }
    }

    @Override
    public List<FileAttachmentResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public FileAttachmentResponse create(FileAttachmentRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Override
    public FileAttachmentResponse update(Long id, FileAttachmentRequest request) {
        FileAttachment entity = repository.findById(id).orElseThrow();
        entity.setFileName(request.getFileName());
        entity.setFilePath(request.getFilePath());
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void upload(MultipartFile file, String city) {
        try {
            // 1. Kayıt dizinini ayarla
            String uploadDir = "uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs(); // klasörü oluştur
            }

            // 2. Dosya adı ve tam yol
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            // 3. Dosyayı diske yaz
            Files.write(filePath, file.getBytes());

            // 4. Veritabanına kaydet
            FileAttachment attachment = new FileAttachment();
            attachment.setFileName(fileName);
            attachment.setFilePath(filePath.toString());
            // attachment.setCity(city); // eğer city alanı varsa ekleyebilirsin

            repository.save(attachment);

        } catch (IOException e) {
            throw new RuntimeException("Dosya yüklenemedi: " + e.getMessage());
        }
    }
}
