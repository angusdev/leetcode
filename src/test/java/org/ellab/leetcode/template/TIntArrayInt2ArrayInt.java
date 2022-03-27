package org.ellab.leetcode.template;

import static org.junit.jupiter.api.Assertions.fail;

import org.ellab.leetcode.Utils;
import org.junit.jupiter.api.Test;

/**
 * n - XXXXXXXX.
 * 
 * 
 * 
 * @see <a href="">leetcode.com</a>
 */
public class TIntArrayInt2ArrayInt {
    class Solution {
        public int[] solve(int[][] array, int n) {
            return new int[0];
        }
    }

    class BruteForce extends Solution {
        @Override
        public int[] solve(int[][] array, int n) {
            return new int[0];
        }
    }

    private void doTest(Solution solution, int[][] array, int n, int[] expected) {
        int[] actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.solve(array, n);
            pass = Utils.equals(expected, actual);
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.println("Fail     : " + n);
            for (int[] row : array) {
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

        doTest(solution, new int[][] {}, 1, new int[0]);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[][] {}, 1, new int[0]);
    }

    private void testPerformance(Solution solution, int tries, int[][] array, int n) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.solve(array, n);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int tries = 10000;
        int[][] array = new int[][] {};
        int n = 100;
        testPerformance(new Solution(), tries, array, n);
        testPerformance(new BruteForce(), Math.max(1, tries / 100), array, n);
    }
}
