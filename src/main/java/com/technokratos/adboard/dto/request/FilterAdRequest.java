package com.technokratos.adboard.dto.request;

import java.sql.Timestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

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
    @Size(min = 1, max = 50, message = "{InvalidFilterSize}")
    private String title;

    @Schema(description = "Incomplete content")
    @Size(min = 1, max = 50, message = "{InvalidFilterSize}")
    private String content;

    @Schema(description = "Owner email")
    @Size(min = 1, max = 50, message = "{InvalidFilterSize}")
    private String email;

    @Schema(description = "Date after which the ads were created")
    private Timestamp dateAfter;

    @Schema(description = "Min count of photo")
    @Min(value = 0, message = "{PositiveValue}")
    private Long minPhotoCount;
}
