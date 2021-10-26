package dev.gunlog.api.security.provider;

import dev.gunlog.api.security.JwtAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
@SuppressWarnings("unchecked")
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Jws<Claims> jwsClaims = (Jws<Claims>)authentication.getCredentials();
        String subject = jwsClaims.getBody().getSubject();
        List<String> roles = jwsClaims.getBody().get("roles", List.class);

        List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new JwtAuthenticationToken(subject, authorities);
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}