package com.technokratos.adboard.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Schema(description = "Form for filtering ads")
public class FilterAdRequest {

    @Schema(description = "Incomplete name")
    private String title;

    @Schema(description = "Incomplete content")
    private String content;

    @Schema(description = "Seller email")
    private String email;

    @Schema(description = "Date after which the ads were created")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dateAfter;

    @Schema(description = "Min count of photo")
    private Long minPhotoCount;
}
