package com.technokratos.adboard.dto.request;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author d.gilfanova
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Create ad form")
public class CreateAdRequest {

    @Schema(description = "Ad title")
    private String title;

    @Schema(description = "Ad content")
    private String content;

    @Schema(description = "User id")
    //while we don't have security
    private UUID userId;

    @Schema(description = "Status: ad is active or not")
    private Boolean isActive;

    @Schema(description = "List of photos")
    private MultipartFile[] photos;
}
