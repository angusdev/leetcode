package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 37 - Sudoku Solver.
 * 
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 * 
 * A sudoku solution must satisfy all of the following rules:
 * 
 * Each of the digits 1-9 must occur exactly once in each row. Each of the digits 1-9 must occur exactly once in each
 * column. Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid. The '.' character
 * indicates empty cells.
 * 
 * @see J00036
 * @see <a href="https://leetcode.com/problems/sudoku-solver/">leetcode.com</a>
 */
public class J00037 {
    class Solution {
        // rowUsed[0][4]==true means in 1st row, number 5 is already used, don't need to try this number
        // So if we put board[4][7] = '2', rowUsed[4][1] = colUsed[7][1] = boxUsed[5][1] = true
        private boolean[][] rowUsed = new boolean[9][9];
        private boolean[][] colUsed = new boolean[9][9];
        // box index is floor(r/3)*3 + column
        private boolean[][] boxUsed = new boolean[9][9];

        public void solveSudoku(char[][] board) {
            // Fill up with given number
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (board[r][c] != '.') {
                        int num = board[r][c] - '1';
                        rowUsed[r][num] = true;
                        colUsed[c][num] = true;
                        boxUsed[r / 3 * 3 + c / 3][num] = true;
                    }
                }
            }

            solveSudoku(board, 0, 0);
        }

        private boolean solveSudoku(final char[][] board, final int row, final int col) {
            int r = row;
            int c = col;

            // find next empty space
            while (r < 9 && board[r][c] != '.') {
                r = c == 8 ? r + 1 : r;
                c = c == 8 ? 0 : c + 1;
            }

            if (r == 9) {
                // fill up all spaces
                return true;
            }

            // the index of 3x3 box
            int b = r / 3 * 3 + c / 3;

            // either acceding or descending is ok, just happen descending is faster in leetcode.com. Seem the numbers
            // in top right box are large number.

            // for (int guess = 0; guess < 9; guess++) {
            for (int guess = 8; guess >= 0; guess--) {
                if (rowUsed[r][guess] || colUsed[c][guess] || boxUsed[b][guess]) {
                    continue;
                }
                rowUsed[r][guess] = colUsed[c][guess] = boxUsed[b][guess] = true;
                board[r][c] = (char) (guess + '1');
                if (solveSudoku(board, c == 8 ? r + 1 : r, c == 8 ? 0 : c + 1)) {
                    return true;
                }
                rowUsed[r][guess] = colUsed[c][guess] = boxUsed[b][guess] = false;
            }

            // no solution, erase the guess
            board[r][c] = '.';

            return false;
        }
    }

    class Solution2 extends Solution {
        public void solveSudoku(char[][] board) {
            solveSudoku(board, 0, 0);
        }

        private boolean solveSudoku(final char[][] board, final int row, final int col) {
            int r = row;
            int c = col;

            // find next empty space
            while (r < 9 && board[r][c] != '.') {
                r = c == 8 ? r + 1 : r;
                c = c == 8 ? 0 : c + 1;
            }

            if (r == 9)

            {
                // fill up all spaces
                return true;
            }

            boolean[] used = new boolean[9];

            // mark the used numbers in same row
            for (int i = 0; i < 9; i++) {
                if (board[r][i] != '.') {
                    used[board[r][i] - '1'] = true;
                }
            }

            // mark the used numbers in same column
            for (int i = 0; i < 9; i++) {
                if (board[i][c] != '.') {
                    used[board[i][c] - '1'] = true;
                }
            }

            // mark the used numbers in same box
            int boxTopLeftRow = r / 3 * 3;
            int boxTopLeftColumn = c / 3 * 3;
            for (int i = boxTopLeftRow; i < boxTopLeftRow + 3; i++) {
                for (int j = boxTopLeftColumn; j < boxTopLeftColumn + 3; j++) {
                    if (board[i][j] != '.') {
                        used[board[i][j] - '1'] = true;
                    }
                }
            }

            for (int guess = 0; guess < 9; guess++) {
                if (used[guess]) {
                    continue;
                }
                board[r][c] = (char) (guess + '1');
                if (solveSudoku(board, c == 8 ? r + 1 : r, c == 8 ? 0 : c + 1)) {
                    return true;
                }
            }

            // no solution, erase the guess
            board[r][c] = '.';

            return false;
        }
    }

    class BruteForce extends Solution {
        @Override
        public void solveSudoku(char[][] board) {
            solveSudoku(board, 0, 0);
        }

        private boolean solveSudoku(char[][] board, int r, int c) {
            if (r == 9) {
                return true;
            }

            if (board[r][c] != '.') {
                return solveSudoku(board, c == 8 ? r + 1 : r, c == 8 ? 0 : c + 1);
            }

            for (char guess = '1'; guess <= '9'; guess++) {
                board[r][c] = guess;
                if (isValidSudoku(board)) {
                    if (solveSudoku(board, c == 8 ? r + 1 : r, c == 8 ? 0 : c + 1)) {
                        return true;
                    }
                }
            }

            // no solution, erase the guess
            board[r][c] = '.';

            return false;
        }

        public boolean isValidSudoku(char[][] board) {
            boolean[][] horz = new boolean[9][9];
            boolean[][] vert = new boolean[9][9];
            boolean[][] box = new boolean[9][9];

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

                    int b = (i / 3) * 3 + (j / 3);
                    if (box[b][n]) {
                        return false;
                    }
                    box[b][n] = true;
                }
            }

            return true;
        }

    }

    private void printBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            if (i % 3 == 0) {
                System.out.println();
            }

            char[] b = board[i];
            System.out.println(String.copyValueOf(b, 0, 3) + " " + String.copyValueOf(b, 3, 3) + " "
                    + String.copyValueOf(b, 6, 3));
        }
        System.out.println();
    }

    private void doTest(Solution solution, char[][] board, char[][] expected) {
        char[][] actual = Utils.clone(board);

        boolean pass = false;
        int failRow = -1;
        Exception unexpectedEx = null;
        try {
            solution.solveSudoku(actual);
            pass = Utils.equals(expected, actual);
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.println("Fail     : Row " + failRow);
            printBoard(board);
            System.out.println("Expected : ");
            printBoard(expected);
            System.out.println("Actual   : ");
            printBoard(actual);
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
                new char[][] { { '5', '3', '4', '6', '7', '8', '9', '1', '2' },
                        { '6', '7', '2', '1', '9', '5', '3', '4', '8' },
                        { '1', '9', '8', '3', '4', '2', '5', '6', '7' },
                        { '8', '5', '9', '7', '6', '1', '4', '2', '3' },
                        { '4', '2', '6', '8', '5', '3', '7', '9', '1' },
                        { '7', '1', '3', '9', '2', '4', '8', '5', '6' },
                        { '9', '6', '1', '5', '3', '7', '2', '8', '4' },
                        { '2', '8', '7', '4', '1', '9', '6', '3', '5' },
                        { '3', '4', '5', '2', '8', '6', '1', '7', '9' } });
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new char[][] { { '.', '.', '.', '.', '.', '.', '2', '.', '.' },
                { '.', '8', '.', '.', '.', '7', '.', '9', '.' }, { '6', '.', '2', '.', '.', '.', '5', '.', '.' },
                { '.', '7', '.', '.', '6', '.', '.', '.', '.' }, { '.', '.', '.', '9', '.', '1', '.', '.', '.' },
                { '.', '.', '.', '.', '2', '.', '.', '4', '.' }, { '.', '.', '5', '.', '.', '.', '6', '.', '3' },
                { '.', '9', '.', '4', '.', '.', '.', '7', '.' }, { '.', '.', '6', '.', '.', '.', '.', '.', '.' } },
                new char[][] { { '9', '5', '7', '6', '1', '3', '2', '8', '4' },
                        { '4', '8', '3', '2', '5', '7', '1', '9', '6' },
                        { '6', '1', '2', '8', '4', '9', '5', '3', '7' },
                        { '1', '7', '8', '3', '6', '4', '9', '5', '2' },
                        { '5', '2', '4', '9', '7', '1', '3', '6', '8' },
                        { '3', '6', '9', '5', '2', '8', '7', '4', '1' },
                        { '8', '4', '5', '7', '9', '2', '6', '1', '3' },
                        { '2', '9', '1', '4', '3', '6', '8', '7', '5' },
                        { '7', '3', '6', '1', '8', '5', '4', '2', '9' } });
    }

    private void testPerformance(Solution solution, int tries, char[][] board) {
        char[][] b = Utils.clone(board);
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.solveSudoku(b);
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
        testPerformance(new BruteForce(), tries / 10, board);
    }
}
