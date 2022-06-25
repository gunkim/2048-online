package io.github.gunkim.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class SocketConfig implements WebSocketMessageBrokerConfigurer {
    private final TokenAuthInterceptor tokenAuthInterceptor;
    private final String allowIp;

    public SocketConfig(TokenAuthInterceptor tokenAuthInterceptor, @Value("${allow-ip}") String allowIp) {
        this.tokenAuthInterceptor = tokenAuthInterceptor;
        this.allowIp = allowIp;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/webSocket")
                .setAllowedOrigins(allowIp)
                .withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");
    }
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) throws BadCredentialsException {
        registration.interceptors(tokenAuthInterceptor);
    }
}
