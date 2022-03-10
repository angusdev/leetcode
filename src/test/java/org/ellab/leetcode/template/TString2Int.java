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
public class TString2Int {
    class Solution {
        public String solve(String s, int num) {
            return "";
        }
    }

    class BruteForce extends Solution {
        @Override
        public String solve(String s, int num) {
            return "";
        }
    }

    private void doTest(Solution solution, String s, int num, String expected) {
        String actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.solve(s, num);
            pass = expected.equals(actual);
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.println("Fail     : " + s);
            System.out.println("         : " + num);
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

        doTest(solution, "", 0, "");
    }

    // @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, "", 0, "");
    }

    private void testRandomCases(Solution solution, int tries, int maxLength, int maxNum) {
        for (int i = 0; i < tries; i++) {
            String s = Utils.randomString(1, maxLength, Utils.STR_LOWER, true);
            int num = Utils.randomSigned(maxNum);
            doTest(solution, s, num, new BruteForce().solve(s, num));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 100, 10000, 100);
    }

    private void testPerformance(Solution solution, int tries, String s, int num) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.solve(s, num);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int tries = 10000;
        String s = "The quick brown fox jumps over the lazy dog";
        int num = 100;
        testPerformance(new Solution(), tries, s, num);
        testPerformance(new BruteForce(), Math.max(1, tries / 100), s, num);
    }
}
