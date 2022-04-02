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
public class TBoolString {
    class Solution {
        public boolean solve(String s) {
            return true;
        }
    }

    class BruteForce extends Solution {
        @Override
        public boolean solve(String s) {
            return true;
        }
    }

    private void doTest(Solution solution, String s, boolean expected) {
        Boolean actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.solve(s);
            pass = expected == actual;
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.println("Fail     : " + s);
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

        doTest(solution, "", true);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, "", true);
    }

    private void testRandomCases(Solution solution, int tries, int minLen, int maxLen) {
        for (int i = 0; i < tries; i++) {
            String s = Utils.randomString(maxLen, maxLen, Utils.STR_LETER, true);
            doTest(solution, s, new BruteForce().solve(s));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 100, 5, 10);
    }

    private void testPerformance(Solution solution, int tries, String s) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.solve(s);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int tries = 10000;
        String s = "";
        testPerformance(new Solution(), tries, s);
        testPerformance(new BruteForce(), Math.max(1, tries / 100), s);
    }
}
