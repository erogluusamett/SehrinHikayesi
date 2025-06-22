package com.example.SehrinHikayesi.Mapper;

import com.example.SehrinHikayesi.DTO.Request.FileAttachmentRequest;
import com.example.SehrinHikayesi.DTO.Response.FileAttachmentResponse;
import com.example.SehrinHikayesi.Entity.FileAttachment;
import org.springframework.stereotype.Component;

@Component
public class FileAttachmentMapper {
    public FileAttachment toEntity(FileAttachmentRequest dto) {
        FileAttachment file = new FileAttachment();
        file.setFileName(dto.getFileName());
        file.setFilePath(dto.getFilePath());
        return file;
    }

    public FileAttachmentResponse toResponse(FileAttachment file) {
        return new FileAttachmentResponse(file.getId(), file.getFileName(), file.getFilePath());
    }
}
