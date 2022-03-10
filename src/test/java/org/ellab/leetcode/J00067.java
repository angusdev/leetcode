package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 67 - Add Binary.
 * 
 * Given two binary strings a and b, return their sum as a binary string.
 * 
 * @see <a href="https://leetcode.com/problems/add-binary/">leetcode.com</a>
 */
public class J00067 {
    class Solution {
        public String addBinary(String a, String b) {
            int alen = a.length();
            int blen = b.length();
            int maxlen = Math.max(alen, blen);
            int minlen = Math.min(alen, blen);

            char[] result = new char[maxlen + 1];
            int carry = 0;
            int ridx = maxlen;
            int two0 = '0' + '0';
            for (int i = 1; i <= minlen; i++) {
                int n = a.charAt(alen - i) + b.charAt(blen - i) - two0 + carry;
                // result = last bit of n
                result[ridx--] = (char) ((n & 1) + '0');
                // carry = second last bit
                carry = n >> 1;
            }
            if (a.length() != b.length()) {
                String s = a.length() > b.length() ? a : b;
                for (int i = s.length() - minlen - 1; i >= 0; i--) {
                    int n = (s.charAt(i) - '0') + carry;
                    result[ridx--] = (char) ((n & 1) + '0');
                    carry = n >> 1;
                }
            }
            if (carry > 0) {
                result[0] = '1';
                return String.valueOf(result);
            }
            else {
                return String.copyValueOf(result, 1, maxlen);
            }
        }
    }

    class SolutionX extends Solution {
        public String addBinary(String a, String b) {
            StringBuilder out = new StringBuilder();
            int aLen = a.length(), bLen = b.length();
            int i = aLen - 1, j = bLen - 1;
            int carry = 0, sum = 0;
            while (i >= 0 || j >= 0 || carry > 0) {
                int aBit = (i >= 0) ? bit(a.charAt(i)) : 0;
                int bBit = (j >= 0) ? bit(b.charAt(j)) : 0;
                sum = aBit ^ bBit ^ carry;
                carry = (aBit & bBit) | (carry & (aBit ^ bBit));
                out.append(sum);
                i--;
                j--;
            }
            return out.reverse().toString();
        }

        int bit(char c) {
            if (c == '0') {
                return 0;
            }
            else {
                return 1;
            }
        }
    }

    class Solution2 extends Solution {
        public String addBinary(String a, String b) {
            int alen = a.length();
            int blen = b.length();
            int maxlen = Math.max(alen, blen);
            int minlen = Math.min(alen, blen);

            char[] result = new char[maxlen + 1];

            int carry = 0;
            for (int i = 1; i <= minlen; i++) {
                int n = (a.charAt(alen - i) - '0') + (b.charAt(blen - i) - '0') + carry;
                result[maxlen - i + 1] = (char) ((n % 2) + '0');
                carry = n > 1 ? 1 : 0;
            }
            if (a.length() != b.length()) {
                String s = a.length() > b.length() ? a : b;
                for (int i = s.length() - minlen - 1; i >= 0; i--) {
                    int n = (s.charAt(i) - '0') + carry;
                    result[i + 1] = (char) ((n % 2) + '0');
                    carry = n > 1 ? 1 : 0;
                }
            }
            if (carry > 0) {
                result[0] = '1';
                return String.valueOf(result);
            }
            else {
                return String.copyValueOf(result, 1, maxlen);
            }
        }
    }

    class SolutionUseStringBuilder extends Solution {
        @Override
        public String addBinary(String a, String b) {
            StringBuilder sb = new StringBuilder();

            int alen = a.length();
            int blen = b.length();
            int carry = 0;
            for (int i = 1; i <= Math.min(alen, blen); i++) {
                int n = (a.charAt(alen - i) - '0') + (b.charAt(blen - i) - '0') + carry;
                sb.append(((char) ((n % 2) + '0')));
                carry = n > 1 ? 1 : 0;
            }
            if (a.length() != b.length()) {
                String s = a.length() > b.length() ? a : b;
                for (int i = s.length() - Math.min(alen, blen) - 1; i >= 0; i--) {
                    int n = (s.charAt(i) - '0') + carry;
                    sb.append(((char) ((n % 2) + '0')));
                    carry = n > 1 ? 1 : 0;
                }
            }
            if (carry > 0) {
                sb.append('1');
            }

            return sb.reverse().toString();
        }
    }

    class BruteForce extends Solution {
        @Override
        public String addBinary(String a, String b) {
            String result = "";

            int alen = a.length();
            int blen = b.length();
            int carry = 0;
            for (int i = 1; i <= Math.min(alen, blen); i++) {
                int n = (a.charAt(alen - i) - '0') + (b.charAt(blen - i) - '0') + carry;
                result = ((char) ((n % 2) + '0')) + result;
                carry = n > 1 ? 1 : 0;
            }
            if (a.length() != b.length()) {
                String s = a.length() > b.length() ? a : b;
                for (int i = s.length() - Math.min(alen, blen) - 1; i >= 0; i--) {
                    int n = (s.charAt(i) - '0') + carry;
                    result = ((char) ((n % 2) + '0')) + result;
                    carry = n > 1 ? 1 : 0;
                }
            }
            if (carry > 0) {
                result = '1' + result;
            }

            return result;
        }
    }

    private void doTest(Solution solution, String a, String b, String expected) {
        String actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.addBinary(a, b);
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
    public void testFailCases() {
        final Solution solution = new Solution();

        doTest(solution, "11", "1", "100");
    }

    @Test
    public void testGivenCases() {
        final Solution solution = new Solution();

        doTest(solution, "11", "1", "100");
        doTest(solution, "1010", "1011", "10101");
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, "0", "0", "0");
        doTest(solution, "1", "1", "10");
        // 123456789 + 23456780
        doTest(solution, "111010110111100110100010101", "1011001011110110000001100", "1000110000011011100100100001");
    }

    private void testRandomCases(Solution solution, int tries, int maxLength) {
        for (int i = 0; i < tries; i++) {
            String a = Utils.randomString(1, maxLength, Utils.STR_BINARY, true);
            String b = Utils.randomString(1, maxLength, Utils.STR_BINARY, true);
            doTest(solution, a, b, new BruteForce().addBinary(a, b));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 100, 10000);
    }

    private void testPerformance(Solution solution, int tries, String a, String b) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.addBinary(a, b);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int tries = 1000000;
        String a = "10110110101010101001011010";
        String b = "10001010101010101010101011";
        testPerformance(new Solution(), tries, a, b);
        testPerformance(new Solution2(), tries, a, b);
        testPerformance(new SolutionX(), tries, a, b);
        testPerformance(new SolutionUseStringBuilder(), tries, a, b);
        testPerformance(new BruteForce(), Math.max(1, tries), a, b);
    }
}
