package dev.gunlog.member.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordMatchResponseDto {
    private boolean isMatchPassword;

    @JsonCreator
    public PasswordMatchResponseDto(@JsonProperty("matchPassword") boolean isMatchPassword) {
        this.isMatchPassword = isMatchPassword;
    }
}