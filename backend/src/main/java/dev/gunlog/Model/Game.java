package dev.gunlog.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.stream.IntStream;

@Setter
@Getter
public class Game {
    private int[][] board;
    private int score;

    public Game() {
        Random random = new Random();

        this.board = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        IntStream.rangeClosed(1, 2).forEach(i -> {
            int posX = random.nextInt(4);
            int posY = random.nextInt(4);
            int value = random.nextInt(2);
            board[posX][posY] = value;
        });
        this.score = 0;
    }
}