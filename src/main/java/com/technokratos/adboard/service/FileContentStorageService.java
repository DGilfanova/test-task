package com.technokratos.adboard.service;

import java.io.InputStream;

import com.technokratos.adboard.model.FileContent;

/**
 * @author d.gilfanova
 */
public interface FileContentStorageService {
    String saveFileContent(InputStream file, String fileName);
    FileContent getFileContent(String id);
}
