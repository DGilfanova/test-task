package com.technokratos.adboard.api;

import javax.validation.Valid;

import com.technokratos.adboard.dto.request.RefreshTokenRequest;
import com.technokratos.adboard.dto.request.SignInRequest;
import com.technokratos.adboard.dto.response.ErrorResponse;
import com.technokratos.adboard.dto.response.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Auth Controller", description = "auth operations")
@RequestMapping("/api/v1")
public interface AuthApi {

    @Operation(summary = "Sign in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login",
                    content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtResponse.class))}
            ),
            @ApiResponse(responseCode = "401", description = "Invalid sign in info",
                    content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @PostMapping(value = "/login")
    JwtResponse login(@RequestBody @Valid SignInRequest signInRequest);

    @Operation(summary = "Renew expired access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful acess token renewing",
                    content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtResponse.class))}
            ),
            @ApiResponse(responseCode = "401", description = "Invalid token",
                    content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @PostMapping(value = "/token/refresh")
    JwtResponse updateTokens(
        @RequestBody @Valid RefreshTokenRequest refreshTokenRequest);
}
