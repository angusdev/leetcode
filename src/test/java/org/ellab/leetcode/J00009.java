package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 9 - Palindrome Number.
 * 
 * Given an integer x, return true if x is palindrome integer.
 * 
 * An integer is a palindrome when it reads the same backward as forward.
 * 
 * For example, 121 is a palindrome while 123 is not.
 * 
 * @see J00007
 * @see <a href="https://leetcode.com/problems/palindrome-number/">leetcode.com</a>
 */
public class J00009 {
    class Solution {
        public boolean isPalindrome(int x) {
            if (x < 0) {
                return false;
            }
            if (x < 10) {
                return true;
            }
            if (x % 10 == 0) {
                return false;
            }

            // Don't need to reverse all digits.
            // E.g. 1221, in the middle, x = 12 and newx = 12, so we can stop here
            // Also 12321, in the middle, x = 12, newx = 123, so we compare newx/10 = x
            // But need to special handle 20, since x = 0, newx = 2, and 2/10 = 0
            int newx = 0;
            while (x > newx) {
                newx = newx * 10 + x % 10;
                x = x / 10;
            }

            return newx == x || newx / 10 == x;
        }
    }

    class SolutionUseReverse extends Solution {
        @Override
        public boolean isPalindrome(int x) {
            if (x < 0) {
                return false;
            }
            if (x < 10) {
                return true;
            }

            // invert the x, e.g. 1234 -> 4321
            int oldx = x;
            int newx = 0;
            while (x > 0) {
                newx = newx * 10 + x % 10;
                x = x / 10;
            }

            return newx == oldx;
        }
    }

    class SolutionUseIntArray extends Solution {
        @Override
        public boolean isPalindrome(int x) {
            if (x < 0) {
                return false;
            }
            if (x < 10) {
                return true;
            }

            int[] buf = new int[10];
            int q, r;
            int len = 0;
            while (x > 0) {
                q = x / 10;
                r = x - (q * 10);
                x = q;
                buf[len++] = r;
            }

            // buf[] is inverted, but not affect the checking
            for (int i = 0; i < len / 2; i++) {
                if (buf[i] != buf[len - i - 1]) {
                    return false;
                }
            }

            return true;
        }
    }

    class SolutionUseString extends Solution {
        @Override
        public boolean isPalindrome(int x) {
            if (x < 0) {
                return false;
            }
            if (x < 10) {
                return true;
            }
            char[] ch = String.valueOf(x).toCharArray();
            for (int i = 0; i < ch.length / 2; i++) {
                if (ch[i] != ch[ch.length - i - 1]) {
                    return false;
                }
            }
            return true;
        }
    }

    private void doTest(Solution solution, int x, boolean expected) {
        Boolean actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.isPalindrome(x);
            pass = actual == expected;
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.println("Fail     : " + x);
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

        doTest(solution, 121, true);
        doTest(solution, -121, false);
        doTest(solution, 10, false);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, 1, true);
        doTest(solution, 10, false);
        doTest(solution, 100, false);
        doTest(solution, 1000, false);
        doTest(solution, 2332, true);
        doTest(solution, 23332, true);
        doTest(solution, 23322, false);
        doTest(solution, 2147447412, true);
    }

    private void testRandomCases(Solution solution, int tries, int maxNum) {
        for (int i = 0; i < tries; i++) {
            int x = Utils.randomSigned(maxNum);
            doTest(solution, x, new Solution().isPalindrome(x));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 100000, Integer.MAX_VALUE);
    }

    private void testPerformance(Solution solution, int tries, int x) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.isPalindrome(x);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int tries = 10000000;
        int x = 122232221;
        testPerformance(new Solution(), tries, x);
        testPerformance(new SolutionUseReverse(), tries, x);
        testPerformance(new SolutionUseIntArray(), tries, x);
        testPerformance(new SolutionUseString(), tries, x);
    }
}
