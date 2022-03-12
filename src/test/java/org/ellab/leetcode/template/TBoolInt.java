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
public class TBoolInt {
    class Solution {
        public boolean solve(int n) {
            return true;
        }
    }

    class BruteForce extends Solution {
        @Override
        public boolean solve(int n) {
            return true;
        }
    }

    private void doTest(Solution solution, int n, boolean expected) {
        Boolean actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.solve(n);
            pass = expected == actual;
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.println("Fail     : " + n);
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

        doTest(solution, 1, true);
        doTest(solution, 2, false);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, 1, true);
    }

    private void testRandomCases(Solution solution, int tries, int maxNum) {
        for (int i = 0; i < tries; i++) {
            int n = Utils.randomSigned(maxNum);
            doTest(solution, n, new BruteForce().solve(n));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 100, Integer.MAX_VALUE);
    }

    private void testPerformance(Solution solution, int tries, int n) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.solve(n);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int tries = 10000;
        int n = 10000;
        testPerformance(new Solution(), tries, n);
        testPerformance(new BruteForce(), Math.max(1, tries / 100), n);
    }
}
