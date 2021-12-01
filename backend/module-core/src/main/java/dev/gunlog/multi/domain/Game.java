package dev.gunlog.multi.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Getter
@ToString
public class Game implements Serializable {
    private Row[] board;
    private int score;
    private boolean isGameOver;
    private static final Random random = new Random();
    private static int MAX_ROWS = 4;
    private static int MAX_COLS = 4;

    public int[][] getBoard() {
        return new int[][]{
                this.board[0].getArr(),
                this.board[1].getArr(),
                this.board[2].getArr(),
                this.board[3].getArr()
        };
    }

    public Game() {
        Row[] board = new Row[]{
                new Row(new int[]{0,0,0,0}),
                new Row(new int[]{0,0,0,0}),
                new Row(new int[]{0,0,0,0}),
                new Row(new int[]{0,0,0,0})
        };
        IntStream.rangeClosed(1, 2).forEach(i -> {
            int posX;
            int posY;
            do {
                posX = random.nextInt(MAX_ROWS);
                posY = random.nextInt(MAX_COLS);
            } while(board[posX].getArr()[posY] != 0);
            int randomTile = random.nextInt(2)+1;
            score += (int) Math.pow(2, (randomTile));
            board[posX].getArr()[posY] = randomTile;
        });
        this.board = board;
        this.isGameOver = false;
    }
    public Game(Row[] board, int score) {
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
        } while(board[posX].getArr()[posY] != 0);
        this.board[posX].getArr()[posY] = 1;
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
                if (board[i].getArr()[j] != 0 && board[i].getArr()[j] == board[i].getArr()[j + 1]) {
                    board[i].getArr()[j] = board[i].getArr()[j] + 1;
                    score += (int) Math.pow(2, (board[i].getArr()[j]));
                    board[i].getArr()[j + 1] = 0;
                }
            }
        }
    }
    private boolean slideRight() {
        AtomicBoolean isMove = new AtomicBoolean(false);
        this.board = Arrays.stream(board).map(row -> {
            int[] remain = Arrays.stream(row.getArr()).filter(n -> n != 0).toArray();
            int zeroCnt = board.length - remain.length;
            int[] newArr = new int[zeroCnt];
            Arrays.fill(newArr,0);
            int[] result = IntStream.concat(Arrays.stream(newArr), Arrays.stream(remain)).toArray();

            if(!isMove.get()) {
                isMove.set(!Arrays.equals(row.getArr(), result));
            }
            return new Row(result);
        }).toArray(Row[]::new);

        return isMove.get();
    }

    private boolean slideLeft() {
        AtomicBoolean isMove = new AtomicBoolean(false);
        this.board = Arrays.stream(board).map(row -> {
            int[] remain = Arrays.stream(row.getArr()).filter(n -> n != 0).toArray();
            int zeroCnt = board.length - remain.length;
            int[] newArr = new int[zeroCnt];
            Arrays.fill(newArr,0);
            int[] result = IntStream.concat(Arrays.stream(remain), Arrays.stream(newArr)).toArray();

            if(!isMove.get()) {
                isMove.set(!Arrays.equals(row.getArr(), result));
            }
            return new Row(result);
        }).toArray(Row[]::new);

        return isMove.get();
    }
    private void rotateClockwise() {
        Row[] newBoard = new Row[MAX_ROWS];
        for (int i = 0; i < newBoard.length; i++) {
            newBoard[i] = new Row(new int[MAX_COLS]);
        }

        for(int i = 0; i < MAX_ROWS; i++){
            for(int j = 0; j < MAX_COLS; j++){
                newBoard[i].getArr()[j] = this.board[MAX_ROWS-j-1].getArr()[i];
            }
        }
        this.board = newBoard;
    }
    private void rotateCounterClockwise() {
        Row[] newBoard = new Row[MAX_ROWS];
        for (int i = 0; i < newBoard.length; i++) {
            newBoard[i] = new Row(new int[MAX_COLS]);
        }

        for(int i = 0; i < MAX_ROWS; i++){
            for(int j = 0; j < MAX_COLS; j++){
                newBoard[i].getArr()[j] = this.board[j].getArr()[MAX_ROWS-i-1];
            }
        }
        this.board = newBoard;
    }
    private void overCheck() {
        Row[] tempBoard = this.board;
        int tempScore = this.score;

        if(!this.left() && !this.right() &&
                !this.top() && !this.bottom()) {
            this.isGameOver = true;
        }
        this.board = tempBoard;
        this.score = tempScore;
    }
}