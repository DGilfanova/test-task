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
@Schema(description = "Deal")
public class DealResponse {

    @Schema(description = "Id")
    private UUID id;

    @Schema(description = "Buyer contact info")
    private UserResponse user;

    @Schema(description = "Advertisement")
    private AdResponse advertisement;

    @Schema(description = "Status: deal is made or not")
    private Boolean isCompleted;
}
