package com.technokratos.adboard.model;

import java.io.InputStream;

import lombok.Builder;
import lombok.Data;

/**
 * @author d.gilfanova
 */
@Data
@Builder
public class FileContent {

    private String id;
    private InputStream content;
}
