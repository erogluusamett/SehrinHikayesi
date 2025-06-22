package com.example.SehrinHikayesi.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class FileAttachmentRequest {
    private String fileName;
    private String filePath;
    private Long storyId;

    public FileAttachmentRequest(String fileName, String filePath, Long storyId) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.storyId = storyId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }
}
