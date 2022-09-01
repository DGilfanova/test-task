package com.technokratos.adboard.dto.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author d.gilfanova
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Main file info")
public class FileResponse {

    @Schema(description = "Id")
    private UUID id;

    @Schema(description = "File name")
    private String name;

    @Schema(description = "File size")
    private Long size;
}
