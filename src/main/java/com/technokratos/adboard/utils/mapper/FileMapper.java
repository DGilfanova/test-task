package com.technokratos.adboard.utils.mapper;

import java.util.UUID;

import com.technokratos.adboard.dto.response.FileResponse;
import com.technokratos.adboard.model.File;
import org.mapstruct.Mapper;

/**
 * @author d.gilfanova
 */
@Mapper(componentModel = "spring")
public interface FileMapper {

    FileResponse toResponse(File file);
}
