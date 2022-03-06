package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 36 - Valid Sudoku.
 * 
 * Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be validated according to the following
 * rules:
 * 
 * Each row must contain the digits 1-9 without repetition. Each column must contain the digits 1-9 without repetition.
 * Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9 without repetition. Note:
 * 
 * A Sudoku board (partially filled) could be valid but is not necessarily solvable. Only the filled cells need to be
 * validated according to the mentioned rules.
 * 
 * @see <a href="https://leetcode.com/problems/valid-sudoku/">leetcode.com</a>
 */
public class J00036 {
    class Solution {
        public boolean isValidSudoku(char[][] board) {
            boolean[] horz = new boolean[137];
            boolean[] vert = new boolean[137];
            boolean[] square = new boolean[137];

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (board[i][j] == '.') {
                        continue;
                    }
                    int n = board[i][j] - '1';
                    int h = (i << 4) + n;
                    if (horz[h]) {
                        return false;
                    }
                    horz[h] = true;

                    int v = (j << 4) + n;
                    if (vert[v]) {
                        return false;
                    }
                    vert[v] = true;

                    int s = (i / 3) * 3 + (j / 3);
                    s = (s << 4) + n;
                    if (square[s]) {
                        return false;
                    }
                    square[s] = true;
                }
            }

            return true;
        }
    }

    class Solution2 extends Solution {
        @Override
        public boolean isValidSudoku(char[][] board) {
            boolean[] horz = new boolean[81];
            boolean[] vert = new boolean[81];
            boolean[] square = new boolean[81];

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (board[i][j] == '.') {
                        continue;
                    }
                    int n = board[i][j] - '1';
                    int h = i * 9 + n;
                    if (horz[h]) {
                        return false;
                    }
                    horz[h] = true;

                    int v = j * 9 + n;
                    if (vert[v]) {
                        return false;
                    }
                    vert[v] = true;

                    int s = (i / 3) * 3 + (j / 3);
                    s = s * 9 + n;
                    if (square[s]) {
                        return false;
                    }
                    square[s] = true;
                }
            }

            return true;
        }
    }

    class Solution3 extends Solution {
        @Override
        public boolean isValidSudoku(char[][] board) {
            boolean[][] horz = new boolean[9][9];
            boolean[][] vert = new boolean[9][9];
            boolean[][] square = new boolean[9][9];

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (board[i][j] == '.') {
                        continue;
                    }
                    int n = board[i][j] - '1';
                    if (horz[i][n]) {
                        return false;
                    }
                    horz[i][n] = true;

                    if (vert[j][n]) {
                        return false;
                    }
                    vert[j][n] = true;

                    int s = (i / 3) * 3 + (j / 3);
                    if (square[s][n]) {
                        return false;
                    }
                    square[s][n] = true;
                }
            }

            return true;
        }
    }

    private void doTest(Solution solution, char[][] board, boolean expected) {
        Boolean actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.isValidSudoku(board);
            pass = actual == expected;
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.println("Fail     : ");
            for (int i = 0; i < board.length; i++) {
                if (i % 3 == 0) {
                    System.out.println();
                }

                char[] b = board[i];
                System.out.println(String.copyValueOf(b, 0, 3) + " " + String.copyValueOf(b, 3, 3) + " "
                        + String.copyValueOf(b, 6, 3));
            }
            System.out.println();
            System.out.println("Expected : " + expected);
            System.out.println("Actual   : " + actual);
            if (unexpectedEx != null) {
                unexpectedEx.printStackTrace();
            }
            fail("Failed. See console for oupput");
        }
    }

    @Test
    public void testGivenCases() {
        final Solution solution = new Solution();

        doTest(solution, new char[][] { { '5', '3', '.', '.', '7', '.', '.', '.', '.' },
                { '6', '.', '.', '1', '9', '5', '.', '.', '.' }, { '.', '9', '8', '.', '.', '.', '.', '6', '.' },
                { '8', '.', '.', '.', '6', '.', '.', '.', '3' }, { '4', '.', '.', '8', '.', '3', '.', '.', '1' },
                { '7', '.', '.', '.', '2', '.', '.', '.', '6' }, { '.', '6', '.', '.', '.', '.', '2', '8', '.' },
                { '.', '.', '.', '4', '1', '9', '.', '.', '5' }, { '.', '.', '.', '.', '8', '.', '.', '7', '9' } },
                true);
        doTest(solution, new char[][] { { '8', '3', '.', '.', '7', '.', '.', '.', '.' },
                { '6', '.', '.', '1', '9', '5', '.', '.', '.' }, { '.', '9', '8', '.', '.', '.', '.', '6', '.' },
                { '8', '.', '.', '.', '6', '.', '.', '.', '3' }, { '4', '.', '.', '8', '.', '3', '.', '.', '1' },
                { '7', '.', '.', '.', '2', '.', '.', '.', '6' }, { '.', '6', '.', '.', '.', '.', '2', '8', '.' },
                { '.', '.', '.', '4', '1', '9', '.', '.', '5' }, { '.', '.', '.', '.', '8', '.', '.', '7', '9' } },
                false);
    }

    private void testPerformance(Solution solution, int tries, char[][] board) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.isValidSudoku(board);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int tries = 1000000;
        char[][] board = new char[][] { { '5', '3', '.', '.', '7', '.', '.', '.', '.' },
                { '6', '.', '.', '1', '9', '5', '.', '.', '.' }, { '.', '9', '8', '.', '.', '.', '.', '6', '.' },
                { '8', '.', '.', '.', '6', '.', '.', '.', '3' }, { '4', '.', '.', '8', '.', '3', '.', '.', '1' },
                { '7', '.', '.', '.', '2', '.', '.', '.', '6' }, { '.', '6', '.', '.', '.', '.', '2', '8', '.' },
                { '.', '.', '.', '4', '1', '9', '.', '.', '5' }, { '.', '.', '.', '.', '8', '.', '.', '7', '9' } };

        testPerformance(new Solution(), tries, board);
        testPerformance(new Solution2(), tries, board);
        testPerformance(new Solution3(), tries, board);
    }
}
