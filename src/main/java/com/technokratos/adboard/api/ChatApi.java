package com.technokratos.adboard.api;

import java.util.List;
import java.util.UUID;

import com.technokratos.adboard.dto.response.ChatMessageResponse;
import com.technokratos.adboard.dto.response.ChatResponse;
import com.technokratos.adboard.dto.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author d.gilfanova
 */
@Tag(name = "Chat controller", description = "chat operations")
@RequestMapping("/api/v1/chat")
public interface ChatApi<PRINCIPAL> {

    @Operation(summary = "Getting user chats")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully chats creating"),
        @ApiResponse(responseCode = "401", description = "Invalid auth token",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<ChatResponse> getUserChats(@Parameter(hidden = true) PRINCIPAL principal);

    @Operation(summary = "Getting user chats count")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully count getting"),
        @ApiResponse(responseCode = "404", description = "Chat not found",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(responseCode = "401", description = "Invalid auth token",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @GetMapping(value = "/{chat-id}/message/count", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Long getCountChatNewMessages(@PathVariable("chat-id") UUID chatId,
        @Parameter(hidden = true) PRINCIPAL principal);

    @Operation(summary = "Getting chat messages")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully messages getting"),
        @ApiResponse(responseCode = "404", description = "Chat not found",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(responseCode = "401", description = "Invalid auth token",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @GetMapping(value = "/{chat-id}/message", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<ChatMessageResponse> getChatMessages(@PathVariable("chat-id") UUID chatId,
        @Parameter(hidden = true) PRINCIPAL principal);
}
