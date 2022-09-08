package com.technokratos.adboard.dto.request;

import javax.validation.constraints.NotNull;

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
@Schema(description = "Ad status form")
public class UpdateAdStatusRequest {

    @Schema(description = "Status: is active or not")
    @NotNull(message = "{NotBlank}")
    private Boolean isActive;
}
