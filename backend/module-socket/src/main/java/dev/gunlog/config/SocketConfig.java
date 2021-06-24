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
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class SocketConfig implements WebSocketMessageBrokerConfigurer {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtUtil jwtUtil;
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
        registration.interceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String bearerToken = Optional.ofNullable(accessor.getNativeHeader(SecurityConfig.AUTHENTICATION_HEADER_NAME))
                            .orElseThrow(() -> new BadCredentialsException("토큰 값이 없습니다.")).get(0);
                    Jws<Claims> claimsJws = jwtUtil.parserToken(bearerToken);
                    final UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(claimsJws.getBody().getSubject(), null);

                    accessor.setUser(user);
                }
                return message;
            }
        });
    }
}
