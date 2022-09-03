package com.technokratos.adboard.controller;

import com.technokratos.adboard.api.AuthApi;
import com.technokratos.adboard.dto.request.RefreshTokenRequest;
import com.technokratos.adboard.dto.request.SignInRequest;
import com.technokratos.adboard.dto.response.JwtResponse;
import com.technokratos.adboard.service.JwtTokenService;
import com.technokratos.adboard.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final SecurityService securityService;
    private final JwtTokenService jwtTokenService;

    @Override
    public JwtResponse login(SignInRequest signInRequest) {
        return securityService.login(signInRequest);
    }

    @Override
    public JwtResponse updateTokens(RefreshTokenRequest refreshTokenRequest) {
        return jwtTokenService.refreshAccessToken(refreshTokenRequest);
    }
}
