package dev.gunlog.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

@Setter
@Getter
public class Player implements Serializable {
    private int[][] board;
    private int score;
    private static int MAX_ROWS = 4;
    private static int MAX_COLS = 4;

    public Player() {
        Random random = new Random();

        this.board = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        IntStream.rangeClosed(1, 2).forEach(i -> {
            int posX = random.nextInt(MAX_ROWS);
            int posY = random.nextInt(MAX_COLS);
            int value = random.nextInt(2)+1;
            board[posX][posY] = value;
        });
        this.score = 0;
    }
    public Player(int[][] board) {
        this.board = board;
        this.score = 0;
    }
    private void randomNum() {
        Random random = new Random();
        int posX = random.nextInt(MAX_ROWS);
        int posY = random.nextInt(MAX_COLS);
        this.board[posX][posY] = 1;

    }
    public void leftMove() {
        slideLeft();
        combine();
        slideLeft();
        randomNum();
    }
    public void rightMove() {
        slideRight();
        combine();
        slideRight();
        randomNum();
    }
    public void topMove() {
        rotateClockwise();
        rightMove();
        rotateCounterClockwise();
    }
    public void bottomMove() {
        rotateClockwise();
        leftMove();
        rotateCounterClockwise();
    }

    private void combine() {
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLS - 1; j++) {
                if (board[i][j] == board[i][j + 1]) {
                    board[i][j] = board[i][j] + board[i][j + 1];
                    board[i][j + 1] = 0;
                }
            }
        }
    }
    private void slideRight() {
        this.board = Arrays.stream(board).map(row -> {
            int[] remain = Arrays.stream(row).filter(n -> n != 0).toArray();
            int zeroCnt = board.length - remain.length;
            int[] newArr = new int[zeroCnt];
            Arrays.fill(newArr,0);
            return IntStream.concat(Arrays.stream(newArr), Arrays.stream(remain));
        }).map(i -> i.toArray()).toArray(int[][]::new);
    }

    private void slideLeft() {
        this.board = Arrays.stream(board).map(row -> {
            int[] remain = Arrays.stream(row).filter(n -> n != 0).toArray();
            int zeroCnt = board.length - remain.length;
            int[] newArr = new int[zeroCnt];
            Arrays.fill(newArr,0);
            return IntStream.concat(Arrays.stream(remain), Arrays.stream(newArr));
        }).map(i -> i.toArray()).toArray(int[][]::new);
    }
    private void rotateClockwise() {
        int[][] newBoard = new int[MAX_ROWS][MAX_COLS];
        for(int i = 0; i < MAX_ROWS; i++) {
            newBoard[i] = new int[MAX_COLS];
            for(int j = 0; j < MAX_COLS; j++) {
                newBoard[i][MAX_ROWS - j - 1] = board[j][i];
            }
        }
        this.board = newBoard;
    }
    private void rotateCounterClockwise() {
        int[][] newBoard = new int[MAX_ROWS][MAX_COLS];
        for(int i = 0; i < MAX_ROWS; i++) {
            newBoard[i] = new int[MAX_COLS];
            for(int j = 0; j < MAX_COLS; j++) {
                newBoard[i][j] = board[j][MAX_ROWS - i - 1];
            }
        }
        this.board = newBoard;
    }
}