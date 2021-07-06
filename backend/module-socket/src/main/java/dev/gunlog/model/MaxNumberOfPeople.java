package dev.gunlog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MaxNumberOfPeople {
    TWO(2),
    FOUR(4);

    private Integer value;
}
