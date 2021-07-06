package dev.gunlog.config;

import dev.gunlog.JwtAuthenticationToken;
import dev.gunlog.filter.JwtTokenAuthenticationProcessingFilter;
import dev.gunlog.provider.JwtAuthenticationProvider;
import dev.gunlog.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class SocketConfig implements WebSocketMessageBrokerConfigurer {
    private final TokenAuthInterceptor tokenAuthInterceptor;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/webSocket")
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/play");
        registry.setApplicationDestinationPrefixes("/game");
    }
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) throws BadCredentialsException {
        registration.interceptors(tokenAuthInterceptor);
    }
}
