package com.technokratos.adboard.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.technokratos.adboard.dto.enums.Role;
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
@Schema(description = "Create user form")
public class CreateUserRequest {

    @Schema(description = "Email")
    @NotBlank(message = "{NotBlank}")
    @Email(message = "{NotEmail}")
    private String email;

    @Schema(description = "Password")
    @NotBlank(message = "{NotBlank}")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%+-]).{8,40}$",
        message = "{WeakPassword}")
    private String password;

    @Schema(description = "Role", example = "SELLER or BUYER")
    @NotNull(message = "{NotBlank}")
    private Role role;
}
