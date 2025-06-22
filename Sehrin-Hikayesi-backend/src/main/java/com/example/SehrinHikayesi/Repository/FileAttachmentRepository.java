package com.example.SehrinHikayesi.Repository;

import com.example.SehrinHikayesi.Entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAttachmentRepository extends JpaRepository<FileAttachment, Long> {}
