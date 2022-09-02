package com.technokratos.adboard.service;

import com.technokratos.adboard.dto.request.CreateUserRequest;
import com.technokratos.adboard.dto.response.UserResponse;

/**
 * @author d.gilfanova
 */
public interface UserService {
    UserResponse createUser(CreateUserRequest newUser);
}
