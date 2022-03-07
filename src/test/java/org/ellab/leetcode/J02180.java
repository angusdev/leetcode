package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 2180 - Count Integers With Even Digit Sum.
 * 
 * Given a positive integer num, return the number of positive integers less than or equal to num whose digit sums are
 * even.
 * 
 * The digit sum of a positive integer is the sum of all its digits.
 * 
 * @see <a href="https://leetcode.com/problems/count-integers-with-even-digit-sum/">leetcode.com</a>
 */
public class J02180 {
    class Solution {
        public int countEven(int num) {
            int digit = num / 1000 + num / 100 + num / 10;
            return digit % 2 == 0 ? num / 2 : (num - 1) / 2;
        }
    }

    class BruteForce extends Solution {
        @Override
        public int countEven(int num) {
            int count = 0;

            for (int i = 2; i <= num; i++) {
                String s = String.valueOf(i);
                int sum = 0;
                for (int j = 0; j < s.length(); j++) {
                    sum += (s.charAt(j) - '0');
                }
                if (sum % 2 == 0) {
                    count++;
                }
            }

            return count;
        }
    }

    private void doTest(Solution solution, int num, int expected) {
        Integer actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.countEven(num);
            pass = actual == expected;
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.println("Fail     : " + num);
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

        doTest(solution, 4, 2);
        doTest(solution, 30, 14);
    }

    @Test
    public void testAllCases() {
        for (int i = 1; i <= 1000; i++) {
            int actual = new Solution().countEven(i);
            int expected = new BruteForce().countEven(i);
            if (actual != expected) {
                System.out.println(i + "   " + expected + "   " + actual);
            }
        }
    }
}
