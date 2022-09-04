package com.technokratos.adboard.dto.response;

import java.sql.Timestamp;
import java.util.UUID;

import com.technokratos.adboard.dto.enums.MessageStatus;
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
@Schema(description = "Chat message")
public class ChatMessageResponse {

    @Schema(description = "Message ID")
    private UUID id;

    @Schema(description = "Content")
    private String content;

    @Schema(description = "Sender info")
    private UserResponse sender;

    @Schema(description = "Message status: OLD or NEW")
    private MessageStatus status;

    @Schema(description = "Creation date")
    private Timestamp created;
}
