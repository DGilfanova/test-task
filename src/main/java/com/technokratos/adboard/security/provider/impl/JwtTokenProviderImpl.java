package com.technokratos.adboard.security.provider.impl;

import java.time.Instant;
import java.util.*;

import com.technokratos.adboard.exception.security.AuthenticationHeaderException;
import com.technokratos.adboard.exception.security.RefreshTokenException;
import com.technokratos.adboard.model.RefreshToken;
import com.technokratos.adboard.repository.RefreshTokenRepository;
import com.technokratos.adboard.security.provider.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.technokratos.adboard.constant.Constant.BEARER;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProviderImpl implements JwtTokenProvider {

    @Value("${jwt.expiration.access.mills}")
    private long expirationAccessInMills;

    @Value("${jwt.secretKey}")
    private String jwtSecret;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public String generateAccessToken(String username, Map<String, Object> data) {
        Map<String, Object> claims = new HashMap<>(data);
        claims.put(Claims.SUBJECT, username);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date.from(Instant.now().plusMillis(expirationAccessInMills)))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    @Override
    public boolean validateAccessToken(String accessToken) {
        try {
            Claims claims = parseAccessToken(accessToken);

            Date date = claims.getExpiration();

            return  date.after(new Date());
        } catch (ExpiredJwtException e) {
            log.warn("Access token is expired");

            throw new AuthenticationHeaderException("Token is expired");
        }
    }

    @Override
    public RefreshToken getValidatedRefreshToken(UUID refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken).map(token -> {

                    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
                        log.warn("Refresh token is expired");

                        throw new RefreshTokenException("Refresh token time expired");
                    }
                    return token;
                }).orElseThrow(() -> new RefreshTokenException("Refresh token not found"));
    }

    @Override
    public String getTokenFromHeader(String tokenHeader) {
        if (tokenHeader == null) {
            log.warn("Token is missing");

            return null;
        }

        if (!tokenHeader.startsWith(BEARER)) {
            log.warn("Invalid token format for token: {}", tokenHeader);

            throw new AuthenticationHeaderException("Invalid token format for token: " + tokenHeader);
        }

        return Optional.of(tokenHeader)
                .filter(StringUtils::isNotBlank)
                .map(bearer -> StringUtils.removeStart(bearer, BEARER).trim())
                .filter(StringUtils::isNotBlank)
                .orElse(null);
    }

    @Override
    public Claims parseAccessToken(String accessToken) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(accessToken).getBody();
    }
}
