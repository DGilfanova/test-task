package com.technokratos.adboard.service;

import com.technokratos.adboard.dto.request.SignInRequest;
import com.technokratos.adboard.dto.response.JwtResponse;

public interface SecurityService {
    JwtResponse login(SignInRequest signInRequest);
}
