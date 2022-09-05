package com.technokratos.adboard.service.impl;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import com.technokratos.adboard.dto.request.RefreshTokenRequest;
import com.technokratos.adboard.dto.response.JwtResponse;
import com.technokratos.adboard.dto.response.UserResponse;
import com.technokratos.adboard.exception.UserNotFoundException;
import com.technokratos.adboard.exception.security.AuthenticationHeaderException;
import com.technokratos.adboard.model.RefreshToken;
import com.technokratos.adboard.model.User;
import com.technokratos.adboard.repository.RefreshTokenRepository;
import com.technokratos.adboard.repository.UserRepository;
import com.technokratos.adboard.security.provider.JwtTokenProvider;
import com.technokratos.adboard.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.technokratos.adboard.constant.Constant.BEARER;
import static com.technokratos.adboard.constant.Constant.NOT_DELETED;
import static com.technokratos.adboard.constant.Constant.ROLE;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${jwt.expiration.refresh.mills}")
    private long expirationRefreshInMills;

    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    @Transactional
    @Override
    public RefreshToken addRefreshToken(UserResponse userResponse) {
        refreshTokenRepository.findByUserId(userResponse.getId()).ifPresent(
            refreshToken -> refreshTokenRepository.removeById(refreshToken.getId()));

        return refreshTokenRepository.save(
                RefreshToken.builder()
                        .token(UUID.randomUUID())
                        .expiryDate(Instant.now().plusMillis(expirationRefreshInMills))
                        .user(userRepository.findByIdAndIsDeleted(userResponse.getId(), NOT_DELETED)
                                .orElseThrow(UserNotFoundException::new))
                        .build());
    }

    @Transactional
    @Override
    public JwtResponse refreshAccessToken(RefreshTokenRequest refreshTokenRequest) {

        RefreshToken verifiedRefreshToken =
            jwtTokenProvider.verifyRefreshToken(refreshTokenRequest.getRefreshToken());

        String accessToken = jwtTokenProvider.generateAccessToken(verifiedRefreshToken.getUser().getEmail(),
                Collections.singletonMap(ROLE, verifiedRefreshToken.getUser().getRole().name()));

        return JwtResponse.builder()
                .refreshToken(verifiedRefreshToken.getToken())
                .accessToken(BEARER.concat(accessToken))
                .build();
    }

    @Override
    public JwtResponse generateTokenPair(UserResponse userResponse) {

        String accessToken = jwtTokenProvider.generateAccessToken(userResponse.getEmail(),
                Collections.singletonMap(ROLE, userResponse.getRole()));

        UUID refreshToken = addRefreshToken(userResponse).getToken();

        return JwtResponse.builder()
                .accessToken(BEARER.concat(accessToken))
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public User getUserByToken(String token) {
        try {
            Claims claims = jwtTokenProvider.parseAccessToken(token);
            return userRepository.findByEmailAndIsDeleted(claims.getSubject(), NOT_DELETED)
                    .orElseThrow(() ->
                        new AuthenticationHeaderException("User with this username was not found"));
        } catch (ExpiredJwtException e) {
            log.warn("Can't get user info because token {} is expired", token);

            throw new AuthenticationHeaderException("Token expired date error");
        }
    }
}
