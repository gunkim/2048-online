package dev.gunlog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gunlog.SkipPathRequestMatcher;
import dev.gunlog.filter.AsyncLoginProcessingFilter;
import dev.gunlog.filter.JwtTokenAuthenticationProcessingFilter;
import dev.gunlog.provider.AsyncAuthenticationProvider;
import dev.gunlog.provider.JwtAuthenticationProvider;
import dev.gunlog.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    public static final String API_ROOT_URL = "/**";
    public static final String AUTHENTICATION_URL = "/api/v2/user/signIn";

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    private final AsyncAuthenticationProvider asyncAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(buildAsyncLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthenticationProvider);
        auth.authenticationProvider(asyncAuthenticationProvider);
    }
    private AsyncLoginProcessingFilter buildAsyncLoginProcessingFilter() throws Exception {
        AsyncLoginProcessingFilter filter = new AsyncLoginProcessingFilter(AUTHENTICATION_URL, objectMapper, successHandler, failureHandler);
        filter.setAuthenticationManager(this.authenticationManager());
        return filter;
    }
    private JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(getPermitAllPathList(), API_ROOT_URL);
        JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(matcher, failureHandler, jwtUtil);
        filter.setAuthenticationManager(this.authenticationManager());
        return filter;
    }
    private List<String> getPermitAllPathList() {
        return Arrays.asList(
                AUTHENTICATION_URL,
                "/webSocket/**",
                "/play/**",
                "/game/**"

        );
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
