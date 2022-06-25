package io.github.gunkim.multi.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("게임 Row 정보 테스트")
public class RowTests {
    @Test
    @DisplayName("값 세팅")
    void init() {
        int[] arr = {0,0,0,0};
        Row row = new Row(arr);
        assertThat(row.getArr()).isEqualTo(arr);
    }
    @Test
    @DisplayName("값 세팅 : 실패 -> 배열 길이가 안 맞음")
    void init_fail() {
        int[] arr = {0,0,0};
        assertThatThrownBy(() -> new Row(arr)).isInstanceOf(IllegalArgumentException.class);
    }
}
