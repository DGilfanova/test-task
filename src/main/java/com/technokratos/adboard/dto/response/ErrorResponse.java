package com.technokratos.adboard.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Error response")
public class ErrorResponse {

    @Schema(description = "List of errors")
    private List<ErrorDto> errors;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Error form")
    public static class ErrorDto {

        @Schema(description = "Name of the object with the error")
        private String object;

        @Schema(description = "Name of the exception")
        private String exception;

        @Schema(description = "Error message")
        private String message;
    }
}
