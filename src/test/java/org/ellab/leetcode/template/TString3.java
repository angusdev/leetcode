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
public class TString3 {
    class Solution {
        public String solve(String a, String b) {
            return "";
        }
    }

    class BruteForce extends Solution {
        @Override
        public String solve(String a, String b) {
            return "";
        }
    }

    private void doTest(Solution solution, String a, String b, String expected) {
        String actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.solve(a, b);
            pass = expected.equals(actual);
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.println("Fail     : " + a);
            System.out.println("         : " + b);
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

        doTest(solution, "", "", "");
    }

    // @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, "", "", "");
    }

    private void testRandomCases(Solution solution, int tries, int maxLength) {
        for (int i = 0; i < tries; i++) {
            String a = Utils.randomString(1, maxLength, Utils.STR_LOWER, true);
            String b = Utils.randomString(1, maxLength, Utils.STR_LOWER, true);
            doTest(solution, a, b, new BruteForce().solve(a, b));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 100, 10000);
    }

    private void testPerformance(Solution solution, int tries, String a, String b) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.solve(a, b);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int tries = 10000;
        String a = "";
        String b = "";
        testPerformance(new Solution(), tries, a, b);
        testPerformance(new BruteForce(), Math.max(1, tries / 100), a, b);
    }
}
