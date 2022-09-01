package com.technokratos.adboard.dto.response;

import java.util.UUID;

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
@Schema(description = "User")
public class UserResponse {

    @Schema(description = "Id")
    private UUID id;

    @Schema(description = "Email", example = "ivan@mail.ru")
    private String email;

    @Schema(description = "User role")
    private Role role;
}
