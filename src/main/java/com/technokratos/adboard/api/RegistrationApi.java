package com.technokratos.adboard.api;

import javax.validation.Valid;

import com.technokratos.adboard.dto.request.CreateUserRequest;
import com.technokratos.adboard.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author d.gilfanova
 */
@Tag(name = "Auth Controller", description = "auth operations")
@RequestMapping("/api/v1")
public interface RegistrationApi {

    @Operation(summary = "Creating user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully user creating",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class))
            })
    })
    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UserResponse createUser(@RequestBody @Valid CreateUserRequest newUser);
}
