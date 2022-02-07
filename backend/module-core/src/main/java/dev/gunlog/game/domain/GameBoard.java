package dev.gunlog.game.domain;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;
import lombok.Builder;

public class GameBoard {

    private int[][] board;
    private int score;
    private final int row, col;
    private boolean isGameOver;
    private final GameBoardGenerator generator;

    @Builder
    public GameBoard(GameBoardGenerator generator, int row, int col) {
        if (row < 1) {
            throw new IllegalArgumentException("게임판의 가로 길이는 1 이상이어야 합니다.");
        }
        if (col < 1) {
            throw new IllegalArgumentException("게임판의 세로 길이는 1 이상이어야 합니다.");
        }
        int[][] board = new int[row][col];
        this.score = generator.execute(board, row, col);
        this.generator = generator;
        this.board = board;
        this.row = row;
        this.col = col;
    }

    public int[][] currentBoard() {
        return Arrays.copyOfRange(this.board, 0, row);
    }

    public boolean move(MoveType type) {
        if (isGameOver) {
            return false;
        }
        boolean isMove = switch (type) {
            case TOP -> top();
            case LEFT -> left();
            case RIGHT -> right();
            case BOTTOM -> bottom();
        };
        overCheck();

        return isMove;
    }

    private boolean left() {
        boolean isMove = slideLeft();
        combine();
        slideLeft();
        if (isMove) {
            score += generator.execute(board, row, col);
        }
        return isMove;
    }

    private boolean right() {
        boolean isMove = slideRight();
        combine();
        slideRight();
        if (isMove) {
            score += generator.execute(board, row, col);
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
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col - 1; j++) {
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
            Arrays.fill(newArr, 0);
            int[] result = IntStream.concat(Arrays.stream(newArr), Arrays.stream(remain)).toArray();

            if (!isMove.get()) {
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
            Arrays.fill(newArr, 0);
            int[] result = IntStream.concat(Arrays.stream(remain), Arrays.stream(newArr)).toArray();

            if (!isMove.get()) {
                isMove.set(!Arrays.equals(row, result));
            }
            return result;
        }).toArray(int[][]::new);

        return isMove.get();
    }

    private void rotateClockwise() {
        int[][] newBoard = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                newBoard[i][j] = this.board[row - j - 1][i];
            }
        }
        this.board = newBoard;
    }

    private void rotateCounterClockwise() {
        int[][] newBoard = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                newBoard[i][j] = this.board[j][row - i - 1];
            }
        }
        this.board = newBoard;
    }

    private void overCheck() {
        int[][] tempBoard = this.board;
        int tempScore = this.score;

        if (!this.left() && !this.right() &&
            !this.top() && !this.bottom()) {
            this.isGameOver = true;
        }
        this.board = tempBoard;
        this.score = tempScore;
    }
}