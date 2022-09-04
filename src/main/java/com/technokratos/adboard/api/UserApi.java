package com.technokratos.adboard.api;

import java.util.UUID;

import javax.validation.Valid;

import com.technokratos.adboard.dto.request.UpdateAdStatusRequest;
import com.technokratos.adboard.dto.request.CreateAdRequest;
import com.technokratos.adboard.dto.response.AdResponse;
import com.technokratos.adboard.dto.response.DealResponse;
import com.technokratos.adboard.dto.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * @author d.gilfanova
 */
@Tag(name = "User controller", description = "user operations")
@RequestMapping("/api/v1/user")
public interface UserApi<PRINCIPAL> {

    @Operation(summary = "Creating ad")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully ad creating",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = AdResponse.class))
            }),
        @ApiResponse(responseCode = "404", description = "Required entity not found",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(responseCode = "401", description = "Invalid auth token",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @PostMapping(value = "/ad", consumes = MULTIPART_FORM_DATA_VALUE,
        produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    AdResponse createAd(@Valid CreateAdRequest newAd, @Parameter(hidden = true) PRINCIPAL principal);

    @Operation(summary = "Updating ad status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully ad status updating",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = AdResponse.class))
            }),
        @ApiResponse(responseCode = "404", description = "Ad not found",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(responseCode = "401", description = "Invalid auth token",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @PutMapping(value = "/ad/{ad-id}/status", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    AdResponse updateAdStatus(@Parameter(description = "ad id") @PathVariable("ad-id") UUID adId,
        @RequestBody @Valid UpdateAdStatusRequest updateAdStatusRequest,
        @Parameter(hidden = true) PRINCIPAL principal);

    @Operation(summary = "Making a deal")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deal making",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = DealResponse.class))
            }),
        @ApiResponse(responseCode = "404", description = "Deal not found",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(responseCode = "401", description = "Invalid auth token",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @PutMapping(value = "/deal/{deal-id}/status", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    DealResponse makeDeal(@Parameter(description = "deal id") @PathVariable("deal-id") UUID dealId,
        @Parameter(hidden = true) PRINCIPAL principal);
}
