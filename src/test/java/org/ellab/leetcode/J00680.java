package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 680 - Valid Palindrome II.
 * 
 * Given a string s, return true if the s can be palindrome after deleting at most one character from it.
 * 
 * @see <a href="https://leetcode.com/problems/valid-palindrome-ii/">leetcode.com</a>
 */
public class J00680 {
    class Solution {
        public boolean validPalindrome(String s) {
            int left = 0;
            int right = s.length() - 1;

            while (left < right) {
                if (s.charAt(left) != s.charAt(right)) {
                    int left2 = left + 1;
                    int right2 = right - 1;
                    boolean success = true;
                    while (left < right2) {
                        if (s.charAt(left) != s.charAt(right2)) {
                            success = false;
                            break;
                        }
                        left++;
                        right2--;
                    }

                    if (success) {
                        return true;
                    }

                    while (left2 < right) {
                        if (s.charAt(left2) != s.charAt(right)) {
                            return false;
                        }
                        left2++;
                        right--;
                    }
                    return true;
                }
                left++;
                right--;
            }

            return true;
        }
    }

    class SolutionUsingRecursive extends Solution {
        @Override
        public boolean validPalindrome(String s) {
            return check(s, 0, s.length() - 1, false);
        }

        private boolean check(String s, int left, int right, boolean deleted) {
            while (left < right) {
                if (s.charAt(left) != s.charAt(right)) {
                    if (deleted) {
                        return false;
                    }
                    else {
                        return check(s, left + 1, right, true) || check(s, left, right - 1, true);
                    }
                }
                left++;
                right--;
            }

            return true;
        }
    }

    private void doTest(Solution solution, String s, boolean expected) {
        Boolean actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.validPalindrome(s);
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

        doTest(solution, "aba", true);
        doTest(solution, "abca", true);
        doTest(solution, "abc", false);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, "abba", true);
        doTest(solution, "abcdba", true);
        doTest(solution, "abcdeba", false);
        doTest(solution, "deeee", true);
    }
}
