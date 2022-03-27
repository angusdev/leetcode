package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 1337 - The K Weakest Rows in a Matrix.
 * 
 * You are given an m x n binary matrix mat of 1's (representing soldiers) and 0's (representing civilians). The
 * soldiers are positioned in front of the civilians. That is, all the 1's will appear to the left of all the 0's in
 * each row.
 * 
 * A row i is weaker than a row j if one of the following is true:
 * <ul>
 * <li>The number of soldiers in row i is less than the number of soldiers in row j.</li>
 * <li>Both rows have the same number of soldiers and i < j.</li>
 * </ul>
 * 
 * Return the indices of the k weakest rows in the matrix ordered from weakest to strongest.
 * 
 * @see <a href="https://leetcode.com/problems/the-k-weakest-rows-in-a-matrix/">leetcode.com</a>
 */
public class J01337 {
    class Solution {
        public int[] kWeakestRows(int[][] mat, int k) {
            int rows = mat.length;
            int cols = mat[0].length;

            int[] result = new int[k];
            boolean[] used = new boolean[rows];

            int i = 0;
            for (int c = 0; c < cols; c++) {
                for (int r = 0; r < rows; r++) {
                    if (!used[r] && mat[r][c] == 0) {
                        result[i++] = r;
                        used[r] = true;
                        if (i == k) {
                            return result;
                        }
                    }
                }
            }

            for (int r = 0; r < rows && i < k; r++) {
                if (!used[r]) {
                    result[i++] = r;
                }
            }

            return result;
        }
    }

    private void doTest(Solution solution, int[][] mat, int k, int[] expected) {
        int[] actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.kWeakestRows(mat, k);
            pass = Utils.equals(expected, actual);
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.println("Fail     : " + k);
            for (int[] row : mat) {
                Utils.printlnArray(row);
            }
            System.out.print("Expected : ");
            Utils.printlnArray(expected);
            System.out.print("Actual   : ");
            Utils.printlnArray(actual);
            if (unexpectedEx != null) {
                unexpectedEx.printStackTrace();
            }
            fail("Failed. See console for oupput");
        }
    }

    @Test
    public void testGivenCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[][] { { 1, 1, 0, 0, 0 }, { 1, 1, 1, 1, 0 }, { 1, 0, 0, 0, 0 }, { 1, 1, 0, 0, 0 },
                { 1, 1, 1, 1, 1 } }, 3, new int[] { 2, 0, 3 });
        doTest(solution, new int[][] { { 1, 0, 0, 0 }, { 1, 1, 1, 1 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 } }, 2,
                new int[] { 0, 2 });
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[][] { { 1, 0 }, { 1, 0 } }, 2, new int[] { 0, 1 });
        doTest(solution, new int[][] { { 1, 0 }, { 1, 1 } }, 2, new int[] { 0, 1 });
        doTest(solution, new int[][] { { 1, 1 }, { 1, 0 } }, 2, new int[] { 1, 0 });
    }
}
