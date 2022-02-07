package dev.gunlog.game.domain;

import java.util.random.RandomGenerator;

public class Level1RandomGenerator implements GameBoardGenerator {

    private static final RandomGenerator GENERATOR = RandomGenerator.getDefault();

    public int execute(int[][] board, int row, int col) {
        int score = 0;
        for (int i = 0; i < 2; i++) {
            int posX;
            int posY;
            do {
                posX = GENERATOR.nextInt(row);
                posY = GENERATOR.nextInt(col);
            } while (board[posX][posY] != 0);

            int randomTile = GENERATOR.nextInt(2) + 1;
            board[posX][posY] = randomTile;
            score += randomTile;
        }
        return score;
    }
}
