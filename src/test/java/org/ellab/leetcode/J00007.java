package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 7 - Reverse Integer.
 * 
 * Given a signed 32-bit integer x, return x with its digits reversed. If reversing x causes the value to go outside the
 * signed 32-bit integer range [-231, 231 - 1], then return 0.
 * 
 * Assume the environment does not allow you to store 64-bit integers (signed or unsigned).
 * 
 * @see <a href="https://leetcode.com/problems/reverse-integer/">leetcode.com</a>
 */
public class J00007 {
    class Solution {
        public int reverse(int x) {
            boolean isneg = false;
            if (x < 0) {
                isneg = true;
                x = -x;
            }

            int newx = 0;
            while (x > 0) {
                if (newx * 10 / 10 != newx) {
                    // overflow
                    return 0;
                }
                newx = newx * 10 + x % 10;
                x = x / 10;
            }

            return isneg ? -newx : newx;
        }
    }

    private void doTest(Solution solution, int x, int expected) {
        Integer actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.reverse(x);
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

        doTest(solution, 123, 321);
        doTest(solution, -123, -321);
        doTest(solution, 120, 21);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, 1, 1);
        doTest(solution, 10, 1);
        doTest(solution, 1234, 4321);
        doTest(solution, 23332, 23332);
        doTest(solution, 23322, 22332);
        doTest(solution, 2147483647, 0);
        doTest(solution, 2147483622, 0);
        doTest(solution, 2147347412, 2147437412);
        doTest(solution, 1534236469, 0);
    }
}
