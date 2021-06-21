package dev.gunlog.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
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
    public void leftMove() {
        this.board = Arrays.stream(board).map(row -> {
            int[] remain = Arrays.stream(row).filter(n -> n != 0).toArray();
            int zeroCnt = board.length - remain.length;
            int[] newArr = new int[zeroCnt];
            Arrays.fill(newArr,0);
            return IntStream.concat(Arrays.stream(remain), Arrays.stream(newArr));
        }).map(i -> i.toArray()).toArray(int[][]::new);
    }
    public void rightMove() {
        this.board = Arrays.stream(board).map(row -> {
            int[] remain = Arrays.stream(row).filter(n -> n != 0).toArray();
            int zeroCnt = board.length - remain.length;
            int[] newArr = new int[zeroCnt];
            Arrays.fill(newArr,0);
            return IntStream.concat(Arrays.stream(newArr), Arrays.stream(remain));
        }).map(i -> i.toArray()).toArray(int[][]::new);
    }
    public void rotateCw() {
        int version = this.board.length;
    }
}