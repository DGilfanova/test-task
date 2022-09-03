package com.technokratos.adboard.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import com.technokratos.adboard.dto.request.CreateDealRequest;
import com.technokratos.adboard.dto.request.FilterAdRequest;
import com.technokratos.adboard.dto.response.AdResponse;
import com.technokratos.adboard.dto.response.DealResponse;
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

/**
 * @author d.gilfanova
 */
@Tag(name = "Advertisement controller", description = "advertisement operations")
@RequestMapping("/api/v1/advertisement")
public interface AdApi {

    @Operation(summary = "List of ads with filters")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully list of ads getting",
            content = {@Content(mediaType = "application/json")
            })
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<AdResponse> getFilteredAds(@RequestBody FilterAdRequest filterAdRequest);

    @Operation(summary = "Getting ad")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully ad getting",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = AdResponse.class))
            }),
        @ApiResponse(responseCode = "404", description = "Ad not found")
    })
    @GetMapping(value = "/{ad-id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    AdResponse getAd(@Parameter(description = "ad id") @PathVariable("ad-id") UUID adId);

    @Operation(summary = "Creating deal")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully deal creating",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = DealResponse.class))
            }),
        @ApiResponse(responseCode = "404", description = "Ad not found")
    })
    @PostMapping(value = "/{ad-id}/deal", consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    DealResponse createDeal(@Parameter(description = "ad id") @PathVariable("ad-id") UUID adId,
        @RequestBody @Valid CreateDealRequest newDeal);
}
