package com.technokratos.adboard.security.interceptor;

import java.util.List;
import java.util.Objects;

import com.technokratos.adboard.exception.security.AuthenticationHeaderException;
import com.technokratos.adboard.exception.security.IrrelevantTokenException;
import com.technokratos.adboard.security.details.UserDetailsImpl;
import com.technokratos.adboard.security.provider.JwtTokenProvider;
import com.technokratos.adboard.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author d.gilfanova
 */
@Slf4j
@RequiredArgsConstructor
public class AuthorizationInterceptor implements ChannelInterceptor {

    private final JwtTokenService jwtTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
            MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(Objects.requireNonNull(accessor).getCommand())) {
            MultiValueMap<String, String> nativeHeaders = accessor
                .getMessageHeaders()
                .get(StompHeaderAccessor.NATIVE_HEADERS, MultiValueMap.class);

            List<String> authHeaders = nativeHeaders.get(AUTHORIZATION);

            String token = jwtTokenProvider.getTokenFromHeader(authHeaders.get(0));

            if (Objects.nonNull(token)) {
                if (!jwtTokenProvider.validateAccessToken(token)) {
                    throw new IrrelevantTokenException("The token is no longer valid");
                }

                UserDetailsImpl principal = new UserDetailsImpl(
                    jwtTokenService.getUserByToken(token)
                );

                UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(principal, null,
                        principal.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                accessor.setUser(authenticationToken);
            } else {
                log.info("Authorization websocket header is missing");

                throw new AuthenticationHeaderException("Authorization websocket header is missing");
            }
        }

        return message;
    }
}
