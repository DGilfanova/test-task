package com.technokratos.adboard.dto.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Pair of access and refresh tokens")
public class JwtResponse {

    @Schema(description = "Access token")
    private String accessToken;

    @Schema(description = "Refresh token")
    private UUID refreshToken;
}
