package io.github.gunkim.multi.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class Row {
    private int[] arr;

    public Row(int[] arr) throws IllegalArgumentException {
        if(arr.length != 4) {
            throw new IllegalArgumentException("배열 길이는 4이어야 합니다.");
        }
        this.arr = arr;
    }

    @Override
    public String toString() {
        return Arrays.toString(arr);
    }
}