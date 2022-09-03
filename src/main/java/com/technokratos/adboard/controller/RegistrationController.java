package com.technokratos.adboard.controller;

import com.technokratos.adboard.api.RegistrationApi;
import com.technokratos.adboard.dto.request.CreateUserRequest;
import com.technokratos.adboard.dto.response.UserResponse;
import com.technokratos.adboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author d.gilfanova
 */
@RestController
@RequiredArgsConstructor
public class RegistrationController implements RegistrationApi {

    private final UserService userService;

    @Override
    public UserResponse createUser(CreateUserRequest newUser) {
        return userService.createUser(newUser);
    }
}
