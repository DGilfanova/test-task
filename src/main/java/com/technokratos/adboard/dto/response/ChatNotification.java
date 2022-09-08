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
public class ChatNotification {

    @Schema(description = "Chat id")
    private UUID chatId;

    @Schema(description = "Sender email")
    private String senderEmail;

    @Schema(description = "Part of new message")
    private String messagePart;
}
