package com.technokratos.adboard.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @NotBlank(message = "{NotBlank}")
    @Size(min = 3, max = 100, message = "{InvalidSize}")
    private String title;

    @Schema(description = "Ad content")
    @NotBlank(message = "{NotBlank}")
    @Size(min = 3, max = 1000, message = "{InvalidSizeDescription}")
    private String content;

    @Schema(description = "Status: ad is active or not")
    @NotNull(message = "{NotBlank}")
    private Boolean isActive;

    @Schema(description = "List of photos")
    private MultipartFile[] photos;
}
