package com.technokratos.adboard.service;

import com.technokratos.adboard.dto.request.RefreshTokenRequest;
import com.technokratos.adboard.dto.response.JwtResponse;
import com.technokratos.adboard.dto.response.UserResponse;
import com.technokratos.adboard.model.RefreshToken;
import com.technokratos.adboard.model.User;

public interface JwtTokenService {
    JwtResponse refreshAccessToken(RefreshTokenRequest refreshTokenRequest);
    JwtResponse generateTokenPair(UserResponse userResponse);
    RefreshToken addRefreshToken(UserResponse userResponse);
    User getUserByToken(String token);
}
