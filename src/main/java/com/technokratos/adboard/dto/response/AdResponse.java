package com.technokratos.adboard.dto.response;

import java.util.Set;
import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author d.gilfanova
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Advertisement")
public class AdResponse {

    @Schema(description = "Id")
    private UUID id;

    @Schema(description = "Ad title")
    private String title;

    @Schema(description = "Ad content")
    private String content;

    @Schema(description = "User contact info")
    private UserResponse user;

    @Schema(description = "Status: active ad or not")
    private Boolean isActive;

    @Schema(description = "Set of ad's photos")
    private Set<FileResponse> photos;
}
