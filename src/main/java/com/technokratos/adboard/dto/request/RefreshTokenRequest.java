package com.technokratos.adboard.dto.request;

import java.util.UUID;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Refresh token form")
public class RefreshTokenRequest {

    @Schema(description = "Refresh token")
    @NotNull(message = "{NotBlank}")
    private UUID refreshToken;
}
