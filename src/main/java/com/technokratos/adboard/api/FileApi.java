package com.technokratos.adboard.api;

import java.util.UUID;

import com.technokratos.adboard.dto.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author d.gilfanova
 */
@Tag(name = "File controller", description = "file operations")
@RequestMapping("/api/v1/file")
public interface FileApi {

    @Operation(summary = "Getting file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful file getting",
            content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", description = "File not found",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(responseCode = "401", description = "Invalid auth token",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @GetMapping("/{file-id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Resource> download(@Parameter(description = "file id") @PathVariable("file-id")
        UUID fileId);
}
