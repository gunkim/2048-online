package dev.gunlog.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
public class PlayerTest {
    @Test
    void leftMove() {
        Player player = new Player(new int[][]{
                {0, 0, 1, 1},
                {0, 0, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        });
        printBoard(player.getBoard());
        player.leftMove();
        printBoard(player.getBoard());
        assertThat(player.getBoard()[0][0]).isEqualTo(2);
        assertThat(player.getBoard()[1][0]).isEqualTo(1);
    }
    @Test
    void rightMove() {
        Player player = new Player(new int[][]{
                {0, 0, 1, 1},
                {0, 0, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        });
        printBoard(player.getBoard());
        player.rightMove();
        printBoard(player.getBoard());
        assertThat(player.getBoard()[0][3]).isEqualTo(2);
        assertThat(player.getBoard()[1][3]).isEqualTo(1);
    }
    @Test
    void topMove() {
        Player player = new Player(new int[][]{
                {0, 0, 1, 1},
                {0, 0, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        });
        printBoard(player.getBoard());
        player.topMove();
        printBoard(player.getBoard());
        assertThat(player.getBoard()[0][2]).isEqualTo(2);
        assertThat(player.getBoard()[0][3]).isEqualTo(1);
    }
    @Test
    void bottomMove() {
        Player player = new Player(new int[][]{
                {0, 0, 1, 1},
                {0, 0, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        });
        printBoard(player.getBoard());
        player.bottomMove();
        printBoard(player.getBoard());
        assertThat(player.getBoard()[3][2]).isEqualTo(2);
        assertThat(player.getBoard()[3][3]).isEqualTo(1);
    }
    private void printBoard(int[][] board) {
        System.out.println("====");
        for(int[] a: board) {
            for(int b: a) {
                System.out.print(b);
            }
            System.out.println();
        }
        System.out.println("====");
    }
}
