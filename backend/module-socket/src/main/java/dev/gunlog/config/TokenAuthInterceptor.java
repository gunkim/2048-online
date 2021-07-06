package dev.gunlog.config;

import dev.gunlog.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthInterceptor implements ChannelInterceptor {
    private final JwtUtil jwtUtil;
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
}
