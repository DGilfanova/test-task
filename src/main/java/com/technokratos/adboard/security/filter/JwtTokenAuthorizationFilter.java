package com.technokratos.adboard.security.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technokratos.adboard.dto.response.ErrorResponse;
import com.technokratos.adboard.exception.security.AuthenticationHeaderException;
import com.technokratos.adboard.security.details.UserDetailsImpl;
import com.technokratos.adboard.security.provider.JwtTokenProvider;
import com.technokratos.adboard.service.JwtTokenService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final JwtTokenService jwtTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = jwtTokenProvider.getTokenFromHeader(request.getHeader(AUTHORIZATION));

            if (Objects.nonNull(token)) {
                if (!jwtTokenProvider.validateAccessToken(token)) {
                    sendError(response, "The token is no longer valid");
                }

                UserDetailsImpl principal = new UserDetailsImpl(
                        jwtTokenService.getUserByToken(token)
                );

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(principal, null,
                            principal.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (JwtException | AuthenticationHeaderException e) {
            SecurityContextHolder.clearContext();
            sendError(response, e.getMessage());
        }
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        objectMapper.writeValue(response.getWriter(),
                ErrorResponse.builder()
                                .errors(Collections.singletonList(
                                        ErrorResponse.ErrorDto.builder()
                                                .object("Authorization error")
                                                .message(message)
                                                .build()))
                                .build());
    }
}
