package com.technokratos.adboard.dto.request;

import java.util.Date;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@Schema(description = "Chat message form")
public class ChatMessageRequest {

    @Schema(description = "Recipient ID")
    @NotNull(message = "{NotBlank}")
    private UUID recipientId;

    @Schema(description = "Message content")
    @NotBlank(message = "{NotBlank}")
    @Size(min = 3, max = 1000, message = "{InvalidSizeDescription}")
    private String content;

    @Schema(description = "Creation date")
    @NotNull(message = "{NotBlank}")
    private Date timestamp;
}
