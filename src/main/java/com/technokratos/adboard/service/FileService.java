package com.technokratos.adboard.service;

import java.util.Set;
import java.util.UUID;

import com.technokratos.adboard.dto.enums.FileType;
import com.technokratos.adboard.model.File;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author d.gilfanova
 */
public interface FileService {
    Set<File> uploadFiles(MultipartFile[] files, FileType filesType);
    File uploadFile(MultipartFile file, FileType fileType);
    ResponseEntity<Resource> downloadFile(UUID id);
}
