package com.technokratos.adboard.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

import static org.springframework.messaging.simp.SimpMessageType.CONNECT;

/**
 * @author d.gilfanova
 */
@Configuration
public class WebSocketSecurityConfig
    extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
            .simpTypeMatchers(CONNECT).authenticated()
            .simpDestMatchers("/api/v1/app/**").authenticated();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
