package com.technokratos.adboard.api;

import java.util.UUID;

import javax.validation.Valid;

import com.technokratos.adboard.dto.request.UpdateAdStatusRequest;
import com.technokratos.adboard.dto.request.CreateAdRequest;
import com.technokratos.adboard.dto.request.CreateUserRequest;
import com.technokratos.adboard.dto.response.AdResponse;
import com.technokratos.adboard.dto.response.DealResponse;
import com.technokratos.adboard.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
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
public interface UserApi {

    @Operation(summary = "Creating user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully user creating",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class))
            })
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UserResponse createUser(@RequestBody @Valid CreateUserRequest newUser);

    @Operation(summary = "Creating ad")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully ad creating",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = AdResponse.class))
            })
    })
    @PostMapping(value = "/ad", consumes = MULTIPART_FORM_DATA_VALUE,
        produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    AdResponse createAd(@Valid CreateAdRequest newAd);

    @Operation(summary = "Updating ad status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully ad status updating",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = AdResponse.class))
            })
    })
    @PutMapping(value = "/ad/{ad-id}/status", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    AdResponse updateAdStatus(@PathVariable("ad-id") UUID adId,
        @RequestBody @Valid UpdateAdStatusRequest updateAdStatusRequest);

    @Operation(summary = "Making a deal")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deal making",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = DealResponse.class))
            })
    })
    @PutMapping(value = "/deal/{deal-id}/status", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    DealResponse makeDeal(@PathVariable("deal-id") UUID dealId);
}
