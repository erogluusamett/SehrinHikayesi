package com.example.SehrinHikayesi.Service.AbstactService;

import com.example.SehrinHikayesi.DTO.Request.FileAttachmentRequest;
import com.example.SehrinHikayesi.DTO.Response.FileAttachmentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public abstract class AbstractFileAttachmentService {
    public abstract FileAttachmentResponse getById(Long id);
    public abstract List<FileAttachmentResponse> getAll();
    public abstract FileAttachmentResponse create(FileAttachmentRequest request);
    public abstract FileAttachmentResponse update(Long id, FileAttachmentRequest request);
    public abstract void delete(Long id);
    public abstract String uploadProfileImage(MultipartFile file);


    public abstract void upload(MultipartFile file, String city);
}
