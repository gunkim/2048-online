package dev.gunlog.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

@Setter
@Getter
public class Game {
    private int[][] board;
    private int score;
    private boolean isGameOver;
    private static final Random random = new Random();
    private static int MAX_ROWS = 4;
    private static int MAX_COLS = 4;

    public Game() {
        int[][] board = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        IntStream.rangeClosed(1, 2).forEach(i -> {
            int posX;
            int posY;
            do {
                posX = random.nextInt(MAX_ROWS);
                posY = random.nextInt(MAX_COLS);
            } while(board[posX][posY] != 0);
            int randomTile = random.nextInt(2)+1;
            score += (int) Math.pow(2, (randomTile));
            board[posX][posY] = randomTile;
        });
        this.board = board;
        this.isGameOver = false;
    }
    public Game(int[][] board, int score) {
        this.board = board;
        this.score = score;
        this.isGameOver = false;
    }
    private void randomNum() {
        int posX;
        int posY;
        do {
            posX = random.nextInt(MAX_ROWS);
            posY = random.nextInt(MAX_COLS);
        } while(board[posX][posY] != 0);
        this.board[posX][posY] = 1;
    }
    public void leftMove() {
        if(this.isGameOver) {
            return;
        }
        this.left();
        this.overCheck();
    }
    public void rightMove() {
        if(this.isGameOver) {
            return;
        }
        this.right();
        this.overCheck();
    }
    public void topMove() {
        if(this.isGameOver) {
            return;
        }
        this.top();
        this.overCheck();
    }
    public void bottomMove() {
        if(this.isGameOver) {
            return;
        }
        this.bottom();
        this.overCheck();
    }
    private boolean left() {
        boolean isMove = slideLeft();
        combine();
        slideLeft();
        if(isMove) {
            randomNum();
        }
        return isMove;
    }
    private boolean right() {
        boolean isMove = slideRight();
        combine();
        slideRight();
        if(isMove) {
            randomNum();
        }
        return isMove;
    }
    private boolean top() {
        rotateClockwise();
        boolean isMove = right();
        rotateCounterClockwise();
        return isMove;
    }
    private boolean bottom() {
        rotateClockwise();
        boolean isMove = left();
        rotateCounterClockwise();
        return isMove;
    }

    private void combine() {
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLS - 1; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i][j + 1]) {
                    board[i][j] = board[i][j] + 1;
                    score += (int) Math.pow(2, (board[i][j]));
                    board[i][j + 1] = 0;
                }
            }
        }
    }
    private boolean slideRight() {
        AtomicBoolean isMove = new AtomicBoolean(false);
        this.board = Arrays.stream(board).map(row -> {
            int[] remain = Arrays.stream(row).filter(n -> n != 0).toArray();
            int zeroCnt = board.length - remain.length;
            int[] newArr = new int[zeroCnt];
            Arrays.fill(newArr,0);
            int[] result = IntStream.concat(Arrays.stream(newArr), Arrays.stream(remain)).toArray();

            if(!isMove.get()) {
                isMove.set(!Arrays.equals(row, result));
            }
            return result;
        }).toArray(int[][]::new);

        return isMove.get();
    }

    private boolean slideLeft() {
        AtomicBoolean isMove = new AtomicBoolean(false);
        this.board = Arrays.stream(board).map(row -> {
            int[] remain = Arrays.stream(row).filter(n -> n != 0).toArray();
            int zeroCnt = board.length - remain.length;
            int[] newArr = new int[zeroCnt];
            Arrays.fill(newArr,0);
            int[] result = IntStream.concat(Arrays.stream(remain), Arrays.stream(newArr)).toArray();

            if(!isMove.get()) {
                isMove.set(!Arrays.equals(row, result));
            }
            return result;
        }).toArray(int[][]::new);

        return isMove.get();
    }
    private void rotateClockwise() {
        int[][] newBoard = new int[MAX_ROWS][MAX_COLS];

        for(int i=0; i<MAX_ROWS; i++){
            for(int j=0; j<MAX_COLS; j++){
                newBoard[i][j] = this.board[MAX_ROWS-j-1][i];
            }
        }
        this.board = newBoard;
    }
    private void rotateCounterClockwise() {
        int[][] newBoard = new int[MAX_ROWS][MAX_COLS];

        for(int i=0; i<MAX_ROWS; i++){
            for(int j=0; j<MAX_COLS; j++){
                newBoard[i][j] = this.board[j][MAX_ROWS-i-1];
            }
        }
        this.board = newBoard;
    }
    private void overCheck() {
        int[][] tempBoard = this.board;
        int tempScore = this.score;

        if(!this.left() && !this.right() &&
                !this.top() && !this.bottom()) {
            this.isGameOver = true;
        }
        this.board = tempBoard;
        this.score = tempScore;
    }
}