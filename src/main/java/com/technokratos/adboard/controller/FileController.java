package com.technokratos.adboard.controller;

import java.util.UUID;

import com.technokratos.adboard.api.FileApi;
import com.technokratos.adboard.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author d.gilfanova
 */
@RestController
@RequiredArgsConstructor
public class FileController implements FileApi {

    private final FileService fileService;

    @Override
    public ResponseEntity<Resource> download(UUID fileId) {
        return fileService.downloadFile(fileId);
    }
}
