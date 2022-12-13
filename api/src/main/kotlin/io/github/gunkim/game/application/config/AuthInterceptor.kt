package io.github.gunkim.game.application.config

import io.jsonwebtoken.*
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import java.util.stream.Collectors

@Component
class AuthInterceptor(
    private val jwtUtil: JwtUtil
) : ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
        val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)

        if (StompCommand.CONNECT == accessor.command) {
            val bearerToken = accessor.getFirstNativeHeader("Authorization")
                ?: return message

            val claimsJws: Jws<Claims> = jwtUtil.parserToken(bearerToken)
            accessor.user = UsernamePasswordAuthenticationToken(
                claimsJws.body.subject,
                null,
                null
            )
        }
        return message;
    }
}

@Component
class JwtUtil {
    private val secretKey: String = "hello world"
    private val expirationTime: Long = 36000
    private val issuer: String = "2048"

    fun createToken(username: String?, authorities: List<GrantedAuthority>): String {
        val claims = Jwts.claims().setSubject(username)
        claims["roles"] =
            authorities.stream().map { role: GrantedAuthority -> role.toString() }
                .collect(Collectors.toList())
        val currentTime: LocalDateTime = LocalDateTime.now()
        return Jwts.builder()
            .setClaims(claims)
            .setIssuer(issuer)
            .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(
                Date.from(
                    currentTime.plusMinutes(expirationTime)
                        .atZone(ZoneId.systemDefault()).toInstant()
                )
            )
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()
    }

    fun parserToken(token: String): Jws<Claims> = try {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
    } catch (ex: SignatureException) {
        throw BadCredentialsException("Invalid JWT token: ", ex)
    }
}
