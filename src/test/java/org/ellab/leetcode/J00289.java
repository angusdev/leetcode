package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 289 - Game of Life.
 * 
 * According to Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton devised by
 * the British mathematician John Horton Conway in 1970."
 * 
 * The board is made up of an m x n grid of cells, where each cell has an initial state: live (represented by a 1) or
 * dead (represented by a 0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the
 * following four rules (taken from the above Wikipedia article):
 * 
 * 1. Any live cell with fewer than two live neighbors dies as if caused by under-population.
 * 
 * 2. Any live cell with two or three live neighbors lives on to the next generation.
 * 
 * 3. Any live cell with more than three live neighbors dies, as if by over-population.
 * 
 * 4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 * 
 * The next state is created by applying the above rules simultaneously to every cell in the current state, where births
 * and deaths occur simultaneously. Given the current state of the m x n grid board, return the next state.
 * 
 * @see <a href="https://leetcode.com/problems/game-of-life/">leetcode.com</a>
 */
public class J00289 {
    class Solution {
        // use bit to store the current state and next state
        // bit0 = current state, bit1 = next state
        // 0 = 00 = current dead, next dead, not 3 neighbors
        // 1 = 01 = current live, next dead, not 2-3 neighbors
        // 2 = 10 = current dead, next live, 3 neighbors
        // 3 = 11 = current live, next dead, 2-3 neighbors
        //
        // use bitwise &1 to get the current state
        // use bitwise shift >>1 to update the state from next state to current state
        public void gameOfLife(int[][] board) {
            // {row, column}, up-left, up, up-right, left, right, down-left, down, down-right
            int[][] dir = new int[][] { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 },
                    { 1, 1 }, };
            int height = board.length;
            int width = board[0].length;
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++) {
                    int neighbors = 0;
                    for (int[] d : dir) {
                        int nr = r + d[0];
                        int nc = c + d[1];
                        if (nr >= 0 && nr < height && nc >= 0 && nc < width && (board[nr][nc] & 1) > 0) {
                            neighbors++;
                        }
                    }
                    if (neighbors == 3) {
                        // always new live
                        board[r][c] |= 2;
                    }
                    else if (neighbors == 2) {
                        // new state = current state
                        board[r][c] |= (board[r][c] << 1);
                    }
                    // otherwise new state always be dead

                    // if ((board[r][c] & 1) > 0) {
                    // board[r][c] = (neighbors == 2 || neighbors == 3) ? 3 : 1;
                    // }
                    // else {
                    // board[r][c] = neighbors == 3 ? 2 : 0;
                    // }
                }
            }

            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board[r].length; c++) {
                    board[r][c] >>= 1;
                }
            }
        }
    }

    private void doTest(Solution solution, int[][] board, int[][] expected) {
        int[][] actual = Utils.clone(board);
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            solution.gameOfLife(actual);
            pass = Utils.equals(expected, actual);
        }
        catch (

        Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.println("Fail     : ");
            for (int[] r : board) {
                Utils.printlnArray(r);
            }
            System.out.println("Expected : ");
            for (int[] r : expected) {
                Utils.printlnArray(r);
            }
            System.out.println("Actual   : ");
            for (int[] r : actual) {
                Utils.printlnArray(r);
            }
            if (unexpectedEx != null) {
                unexpectedEx.printStackTrace();
            }
            fail("Failed. See console for oupput");
        }
    }

    @Test
    public void testGivenCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[][] { { 0, 1, 0 }, { 0, 0, 1 }, { 1, 1, 1 }, { 0, 0, 0 } },
                new int[][] { { 0, 0, 0 }, { 1, 0, 1 }, { 0, 1, 1 }, { 0, 1, 0 } });
        doTest(solution, new int[][] { { 1, 1 }, { 1, 0 } }, new int[][] { { 1, 1 }, { 1, 1 } });
    }
}
