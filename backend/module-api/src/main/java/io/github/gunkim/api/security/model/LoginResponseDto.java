package io.github.gunkim.api.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String accessToken;

    @Builder
    public LoginResponseDto(@JsonProperty("accessToken") String accessToken) {
        this.accessToken = accessToken;
    }
}