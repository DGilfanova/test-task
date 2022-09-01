package com.technokratos.adboard.dto.request;

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
@Schema(description = "Create deal form")
public class CreateDealRequest {

    @Schema(description = "User id")
    //while we don't have security
    private UUID userId;
}
