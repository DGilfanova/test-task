package com.technokratos.adboard.dto.request;

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
    private String email;

    @Schema(description = "Password")
    private String password;

    @Schema(description = "Role", example = "SELLER or BUYER")
    private Role role;
}
