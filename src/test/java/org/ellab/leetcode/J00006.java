package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 6 - Zigzag Conversion.
 * 
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to
 * display this pattern in a fixed font for better legibility)
 * 
 * <pre>
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * </pre>
 * 
 * And then read line by line: "PAHNAPLSIIGYIR"
 * 
 * Write the code that will take a string and make this conversion given a number of rows:
 * 
 * string convert(string s, int numRows);
 * 
 * @see <a href="https://leetcode.com/problems/zigzag-conversion/">leetcode.com</a>
 */
public class J00006 {
    /**
     * From below example:
     * 
     * <pre>
     * numRows=5   
     * 0        8          16
     * 1     7  9       15 17       23
     * 2   6   10    14    18    22
     * 3 5     11 13       19 21
     * 4       12          20
     * </pre>
     * 
     * The increment is (numRow - 1 - row) * 2, row * 2 alternatively.
     * 
     * Note that for first and last row, if apply the above formula, the increment will be (numRows-1)*x, 0
     * alternatively, which need to be handled. In below code, first and last row is handled separately, or you can add
     * a checking in main loop. But I do it separately for max speed by reducing the extract if-then-else checking.
     */
    class Solution {
        public String convert(String s, int numRows) {
            if (numRows == 1) {
                return s;
            }

            int len = s.length();
            char[] result = new char[len];
            int i = 0;

            int c = 0;
            while (c < len) {
                result[i++] = s.charAt(c);
                c += (numRows - 1) * 2;
            }

            for (int r = 1; r < numRows - 1; r++) {
                int x = 0;
                c = r;
                while (c < len) {
                    result[i++] = s.charAt(c);
                    c += x == 0 ? ((numRows - r - 1) * 2) : (r * 2);
                    x = 1 - x;
                }
            }

            c = numRows - 1;
            while (c < len) {
                result[i++] = s.charAt(c);
                c += (numRows - 1) * 2;
            }

            return String.valueOf(result);
        }
    }

    class BruteForce extends Solution {
        public String convert(String s, int numRows) {
            if (numRows == 1) {
                return s;
            }

            char[][] array = new char[numRows][(s.length() / (2 * numRows - 2) + 1) * 2];
            int[] index = new int[numRows];
            boolean down = true;
            int row = 0;
            int len = s.length();
            for (int i = 0; i < len; i++) {
                array[row][index[row]++] = s.charAt(i);
                if (down) {
                    row++;
                    if (row == numRows - 1) {
                        down = false;
                    }
                }
                else {
                    row--;
                    if (row == 0) {
                        down = true;
                    }
                }
            }

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < numRows; i++) {
                result.append(String.copyValueOf(array[i], 0, index[i]));
            }

            return result.toString();
        }
    }

    private void doTest(Solution solution, String s, int num, String expected) {
        String actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.convert(s, num);
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

        doTest(solution, "PAYPALISHIRING", 3, "PAHNAPLSIIGYIR");
        doTest(solution, "PAYPALISHIRING", 4, "PINALSIGYAHRPI");
        doTest(solution, "A", 1, "A");
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
            doTest(solution, s, num, new BruteForce().convert(s, num));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 100, 10000, 100);
    }

    private void testPerformance(Solution solution, int tries, String s, int num) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.convert(s, num);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int tries = 100000;
        String s = "The quick brown fox jumps over the lazy dog";
        int num = 100;
        testPerformance(new Solution(), tries, s, num);
        testPerformance(new BruteForce(), Math.max(1, tries), s, num);
    }
}
