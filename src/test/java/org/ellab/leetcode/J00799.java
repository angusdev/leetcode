package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 799 - Champagne Tower.
 * 
 * We stack glasses in a pyramid, where the first row has 1 glass, the second row has 2 glasses, and so on until the
 * 100th row. Each glass holds one cup of champagne.
 * 
 * Then, some champagne is poured into the first glass at the top. When the topmost glass is full, any excess liquid
 * poured will fall equally to the glass immediately to the left and right of it. When those glasses become full, any
 * excess champagne will fall equally to the left and right of those glasses, and so on. (A glass at the bottom row has
 * its excess champagne fall on the floor.)
 * 
 * For example, after one cup of champagne is poured, the top most glass is full. After two cups of champagne are
 * poured, the two glasses on the second row are half full. After three cups of champagne are poured, those two cups
 * become full - there are 3 full glasses total now. After four cups of champagne are poured, the third row has the
 * middle glass half full, and the two outside glasses are a quarter full, as pictured below.
 * 
 * <img src="https://s3-lc-upload.s3.amazonaws.com/uploads/2018/03/09/tower.png"></img>
 * 
 * Now after pouring some non-negative integer cups of champagne, return how full the jth glass in the ith row is (both
 * i and j are 0-indexed.)
 * 
 * @see <a href="https://leetcode.com/problems/add-two-numbers/">leetcode.com</a>
 */
public class J00799 {
    /**
     * Based on SecondSolution. But there is no need to keep the previous row and use a single array to keep track the
     * latest row.
     * 
     * <pre>
     * 2 6 2 0 0 0 0
     * 1 4 4 1 0 0 0
     * </pre>
     * 
     * The current element is suppose the upper-right of current glass, and need to traverse backward to prevent the
     * row[j-1] is overwritten
     */
    class Solution {
        public double champagneTower(int poured, int query_row, int query_glass) {
            if (query_row == 0) {
                return Math.min(poured, 1);
            }

            double[] row = new double[100];
            row[0] = poured;
            // pour now
            for (int i = 1; i <= query_row; i++) {
                // need loop from backward so row[j - 1] won't be overwritten
                for (int j = i; j >= 0; j--) {
                    // right upper, if j==i, the upper right is always 0, just like a virtual glass
                    row[j] = row[j] > 1 ? row[j] / 2 - .5 : 0;
                    // left upper, the first glass doesn't have upper left glass
                    row[j] += j > 0 && row[j - 1] > 1 ? row[j - 1] / 2 - .5 : 0;
                }
            }

            return Math.min(row[query_glass], 1);
        }
    }

    /**
     * In Solution, there need to check j=0 otherwise we will hit row[-1] when j==0, so we want to consider a solution
     * that won't need to reference j-1.
     * 
     * Considering the tower, where x is an virtual glass (always = 0 as it is not assigned in previous row)
     * 
     * <pre>
     * row0     a b c x
     * row1    d e f g
     * </pre>
     *
     * In previous solution we are considering row1 = sum of data from row0. How about if we considering row0 add the
     * right hand side to row1 (the champagne goes from the glass to left and right lower level glass) I.e.
     * 
     * <pre>
     * row[j + 1] = (row[j] - 1) / 2 instead of row[j] = (row[j - 1] - 1) / 2
     * row[j] = (row[ j ] - 1) / 2
     * </pre>
     * 
     * So <code>(c - 1) / 2</code> will add to g, which is 0 originally as it is a new item. Also f becomes
     * <code>(c - 1) / 2</code> also.
     */
    class ShortSolution extends Solution {
        @Override
        public double champagneTower(int poured, int query_row, int query_glass) {
            double[] row = new double[100];
            row[0] = poured;
            for (int i = 0; i <= query_row; i++) {
                for (int j = i - 1; j >= 0; j--) {
                    row[j + 1] += row[j] = row[j] > 1 ? row[j] / 2 - .5 : 0;
                }
            }
            return row[query_glass] > 1 ? 1 : row[query_glass];
        }
    }

    /**
     * Based on Simple Solution, just reduce the calculation to make it faster.
     */
    class SecondSolution extends Solution {
        @Override
        public double champagneTower(int poured, int query_row, int query_glass) {
            if (query_row == 0) {
                return Math.min(poured, 1);
            }

            double[][] tower = new double[query_row + 1][];
            tower[0] = new double[] { poured };
            // pour now
            for (int i = 1; i <= query_row; i++) {
                tower[i] = new double[i + 1];
                for (int j = 0; j <= i; j++) {
                    // left upper
                    if (j > 0 && tower[i - 1][j - 1] > 1) {
                        tower[i][j] = tower[i - 1][j - 1] / 2 - .5;
                    }
                    // right upper
                    if (j < i && tower[i - 1][j] > 1) {
                        tower[i][j] += tower[i - 1][j] / 2 - .5;
                    }

                }
            }

            return Math.min(tower[query_row][query_glass], 1);
        }
    }

    /**
     * Each glass will receive (left-1)/2 + (right-1)/2 champagne. Keep in array and simulate level by level.
     * 
     * <pre>
     *     8
     *    4 4
     *   2 6 2     - 6 = 3 + 3
     *  1 4 4 1    - 4 = 1 + 3
     *  
     *  8
     *  4 4
     *  2 6 2
     *  1 4 4 1
     * </pre>
     */
    class SimpleSolution extends Solution {
        @Override
        public double champagneTower(int poured, int query_row, int query_glass) {
            if (query_row == 0) {
                return Math.min(poured, 1);
            }

            double[][] tower = new double[query_row + 1][];
            tower[0] = new double[] { poured };
            // pour now
            for (int i = 1; i <= query_row; i++) {
                tower[i] = new double[i + 1];
                for (int j = 0; j <= i; j++) {
                    // left upper
                    if (j > 0) {
                        tower[i][j] = tower[i - 1][j - 1] > 1 ? (tower[i - 1][j - 1] - 1) / 2 : 0;
                    }
                    // low upper
                    if (j < i) {
                        tower[i][j] += tower[i - 1][j] > 1 ? (tower[i - 1][j] - 1) / 2 : 0;
                    }

                    if (i == query_row && j == query_glass) {
                        return tower[i][j] > 1 ? 1 : tower[i][j];
                    }
                }
            }

            return 0;
        }
    }

    private void doTest(Solution sulution, int poured, int query_row, int query_glass, double expected) {
        Double actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = sulution.champagneTower(poured, query_row, query_glass);
            pass = actual == expected;
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(
                    "Fail     : poured=" + poured + ", query_row=" + query_row + ", query_glass=" + query_glass);
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

        doTest(solution, 1, 1, 1, 0);
        doTest(solution, 2, 1, 1, 0.5);
        doTest(solution, 100000009, 33, 17, 1);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new ShortSolution();

        doTest(solution, 0, 0, 0, 0);
        doTest(solution, 0, 1, 0, 0);
        doTest(solution, 1, 0, 0, 1);
        doTest(solution, 3, 0, 0, 1);
        doTest(solution, 3, 1, 0, 1);
        doTest(solution, 3, 1, 1, 1);
        doTest(solution, 3, 2, 0, 0);
        doTest(solution, 3, 2, 1, 0);
        doTest(solution, 3, 2, 2, 0);
        doTest(solution, 4, 2, 0, .25);
        doTest(solution, 4, 2, 1, .5);
        doTest(solution, 4, 2, 2, .25);
        doTest(solution, 5, 2, 0, .5);
        doTest(solution, 5, 2, 1, 1);
        doTest(solution, 5, 2, 2, .5);
        doTest(solution, 6, 2, 0, .75);
        doTest(solution, 6, 2, 1, 1);
        doTest(solution, 6, 2, 2, .75);
        doTest(solution, 6, 3, 1, .25);
        doTest(solution, 6, 3, 2, .25);
        doTest(solution, 7, 2, 0, 1);
        doTest(solution, 7, 2, 1, 1);
        doTest(solution, 7, 2, 2, 1);
        doTest(solution, 7, 3, 0, 0);
        doTest(solution, 7, 3, 1, .5);
        doTest(solution, 7, 3, 2, .5);
        doTest(solution, 7, 3, 3, 0);
        doTest(solution, 8, 3, 0, .125);
        doTest(solution, 8, 3, 1, .875);
        doTest(solution, 8, 3, 2, .875);
        doTest(solution, 8, 3, 3, .125);
    }
}
