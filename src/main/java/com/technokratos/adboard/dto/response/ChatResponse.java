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
@Schema(description = "Chat")
public class ChatResponse {

    @Schema(description = "Chat ID")
    private UUID id;

    @Schema(description = "First user in chat")
    private UserResponse firstUser;

    @Schema(description = "Second user in chat")
    private UserResponse secondUser;
}
