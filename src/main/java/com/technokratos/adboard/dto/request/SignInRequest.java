package com.technokratos.adboard.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Sign in form")
public class SignInRequest {

    @Schema(description = "email")
    @NotBlank(message = "{NotBlank}")
    @Email(message = "{NotEmail}")
    private String email;

    @Schema(description = "password")
    @NotBlank(message = "{NotBlank}")
    private String password;
}

