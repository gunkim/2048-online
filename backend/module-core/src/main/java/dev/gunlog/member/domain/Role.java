package dev.gunlog.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum Role {
    USER("회원", "ROLE_USER"),
    ADMIN("관리자", "ROLE_ADMIN");

    private String title;
    private String value;
}
