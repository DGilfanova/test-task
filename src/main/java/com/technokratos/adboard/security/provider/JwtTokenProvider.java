package com.technokratos.adboard.security.provider;

import java.util.Map;
import java.util.UUID;

import com.technokratos.adboard.model.RefreshToken;
import io.jsonwebtoken.Claims;

public interface JwtTokenProvider {
    String generateAccessToken(String username, Map<String, Object> data);
    boolean validateAccessToken(String accessToken);
    RefreshToken getValidatedRefreshToken(UUID refreshToken);
    String getTokenFromHeader(String headerToken);
    Claims parseAccessToken(String accessToken);
}
