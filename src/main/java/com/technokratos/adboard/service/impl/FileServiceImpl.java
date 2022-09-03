package com.technokratos.adboard.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.technokratos.adboard.dto.enums.FileType;
import com.technokratos.adboard.exception.FileNotFoundException;
import com.technokratos.adboard.exception.FileServiceException;
import com.technokratos.adboard.model.File;
import com.technokratos.adboard.model.FileContent;
import com.technokratos.adboard.repository.FileRepository;
import com.technokratos.adboard.service.FileContentStorageService;
import com.technokratos.adboard.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.technokratos.adboard.constant.Constant.NOT_DELETED;

/**
 * @author d.gilfanova
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    private final FileContentStorageService fileContentStorageService;

    @Transactional
    @Override
    public Set<File> uploadFiles(MultipartFile[] files, FileType filesType) {
        Set<File> fileIds = new HashSet<>();

        for (MultipartFile file: files) {
            fileIds.add(uploadFile(file, filesType));
        }

        return fileIds;
    }

    @Transactional
    @Override
    public File uploadFile(MultipartFile file, FileType fileType) {
        try {
            File newFile = File.builder()
                .link(fileContentStorageService.saveFileContent(file.getInputStream(),
                    file.getOriginalFilename()))
                .fileType(fileType)
                .name(file.getOriginalFilename())
                .size(file.getSize())
                .contentType(file.getContentType())
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .build();

            return fileRepository.save(newFile);
        } catch (IOException e) {
            log.error("Error when get stream from file with name {}", file.getOriginalFilename(), e);

            throw new FileServiceException("Can't get stream from  file "
                                           + file.getOriginalFilename(), e);
        }
    }

    @Override
    public ResponseEntity<Resource> downloadFile(UUID id) {
        File file = fileRepository.findByIdAndIsDeleted(id, NOT_DELETED)
            .orElseThrow(FileNotFoundException::new);

        FileContent fileContent = fileContentStorageService.getFileContent(file.getLink());

        return createResponse(file, fileContent);
    }

    public ResponseEntity<Resource> createResponse(File file, FileContent fileContent) {
        return ResponseEntity.ok()
            .header("Content-Type", file.getContentType())
            .header("Content-Length", file.getSize().toString())
            .header("Content-Disposition",
                "filename=\"" + file.getName() + "\"")
            .body(new InputStreamResource(fileContent.getContent()));
    }
}
