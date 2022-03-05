package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * 3 - Longest Substring Without Repeating Characters.
 * 
 * Given a string s, find the length of the longest substring without repeating characters.
 * 
 * @see <a href="https://leetcode.com/problems/longest-substring-without-repeating-characters/">leetcode.com</a>
 */
public class J00003 {
    /**
     * Use an array to store the last occurrence of each char. When a char occur before, start counting from the next
     * char of previous occurrence.
     * 
     * <pre>
     *              i   start    abcdef     maxlen
     * <init>               0    000000          1  
     * a            1       1    100000          1
     * ab           2       1    120000          1
     * abc          3       1    123000          1
     * abca         4       2    423000          3
     * abcac        5       4    425000          3
     * abcacd       6       4    425600          3
     * abcacde      7       4    425670          3
     * abcacdef     8       4    425678          3              
     * abcacdefb    9       4    495678          3   (b won't reset start since it's prev pos is < start
     * end                                       6   s.length() - start = 10 - 4 = 6
     *
     * abcacdefb    9       4    495678          3
     * abcacdefbb  10      10                    6   i - start = 10 - 4 = 6
     * </pre>
     */
    class Solution {
        public int lengthOfLongestSubstring(String s) {
            if (s.length() == 0) {
                return 0;
            }

            int[] pos = new int[256];

            int maxlen = 1;
            int start = 0;
            // use 1-index since pos[] is all 0 when created
            for (int i = 1; i <= s.length(); i++) {
                int ch = (int) s.charAt(i - 1);
                if (pos[ch] == 0) {
                    // not found before
                    pos[ch] = i;
                    if (start == 0) {
                        start = i;
                    }
                }
                else {
                    if (pos[ch] >= start) {
                        // already exist, calculate the length
                        maxlen = Math.max(maxlen, i - start);
                        // restart from next char of the previous occurrence
                        start = pos[ch] + 1;
                    }
                    pos[ch] = i;
                }
            }

            if (start > 0) {
                maxlen = Math.max(maxlen, s.length() - start + 1);
            }

            return maxlen;
        }
    }

    /**
     * Based on Solution, with some micro optimization (e.g. dont's use s.length() and Math.max().
     */
    class FasterSolution extends Solution {
        public int lengthOfLongestSubstring(String s) {
            if (s.length() == 0) {
                return 0;
            }

            int[] pos = new int[256];

            int maxlen = 1;
            int start = 0;
            int len = s.length();
            // use 1-index since pos[] is all 0 when created
            for (int i = 1; i <= len; i++) {
                int ch = (int) s.charAt(i - 1);
                if (pos[ch] == 0) {
                    // not found before
                    pos[ch] = i;
                    if (start == 0) {
                        start = i;
                    }
                }
                else {
                    if (pos[ch] >= start) {
                        // already exist, calculate the length
                        int thislen = i - start;
                        maxlen = thislen > maxlen ? thislen : maxlen;
                        // restart from next char of the previous occurrence
                        start = pos[ch] + 1;
                    }
                    pos[ch] = i;
                }
            }

            if (start > 0) {
                maxlen = Math.max(maxlen, len - start + 1);
            }

            return maxlen;
        }
    }

    class BruteForce extends Solution {
        @Override
        public int lengthOfLongestSubstring(String s) {
            if (s.length() == 0) {
                return 0;
            }

            int result = 1;

            for (int i = 0; i < s.length(); i++) {
                Set<Character> set = new HashSet<>();
                set.add(s.charAt(i));
                for (int j = i + 1; j < s.length(); j++) {
                    if (set.contains(s.charAt(j))) {
                        result = Math.max(result, j - i);
                        break;
                    }
                    else if (j == s.length() - 1) {
                        // already reach end and no repeat, finish the checking
                        return Math.max(result, j - i + 1);
                    }
                    set.add(s.charAt(j));
                }
            }

            return result;
        }
    }

    private void doTest(Solution solution, String s, int expected) {
        Integer actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.lengthOfLongestSubstring(s);
            pass = actual == expected;
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

        doTest(solution, "abcabcbb", 3);
        doTest(solution, "bbbbb", 1);
        doTest(solution, "pwwkew", 3);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, "", 0);
        doTest(solution, "a", 1);
        doTest(solution, "abcdefghijklmnopqrstuvwxyz", 26);
        doTest(solution, "abcacdefb", 6);
        doTest(solution, "abcacdef", 5);
        doTest(solution, "abcacdefbcd", 6);
        doTest(solution, "aabaabcbb", 3);
        doTest(solution, "aabaabcb", 3);
    }

    private void testRandomCases(Solution solution, int tries, int maxArraySize) {
        for (int i = 0; i < tries; i++) {
            final int arraySize = NumberUtils.random(2, maxArraySize);
            char[] array = new char[arraySize];
            for (int j = 0; j < arraySize; j++) {
                array[i] = (char) (NumberUtils.random(25) + 'a');
            }
            String s = String.valueOf(array);

            doTest(solution, s, new BruteForce().lengthOfLongestSubstring(s));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 1000, 100);
    }

    private void testPerformance(Solution solution, int tries, String s) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.lengthOfLongestSubstring(s);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int tries = 100000;
        int arraySize = 10000;
        char[] array = new char[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = (char) (NumberUtils.random(25) + 'a');
        }
        String s = String.valueOf(array);
        testPerformance(new Solution(), tries, s);
        testPerformance(new FasterSolution(), tries, s);
        testPerformance(new BruteForce(), tries / 10, s);
    }
}
