package dev.gunlog.multi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
public class Row {
    private int[] arr;

    @Override
    public String toString() {
        return Arrays.toString(arr);
    }
}