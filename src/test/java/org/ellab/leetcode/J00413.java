package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 413 - Arithmetic Slices.
 * 
 * An integer array is called arithmetic if it consists of at least three elements and if the difference between any two
 * consecutive elements is the same.
 * 
 * For example, [1,3,5,7,9], [7,7,7,7], and [3,-1,-5,-9] are arithmetic sequences. Given an integer array nums, return
 * the number of arithmetic subarrays of nums.
 * 
 * A subarray is a contiguous subsequence of the array.
 * 
 * @see <a href="https://leetcode.com/problems/arithmetic-slices/">leetcode.com</a>
 */
public class J00413 {
    class Solution {
        public int numberOfArithmeticSlices(int[] nums) {
            int result = 0;
            for (int i = 1; i < nums.length; i++) {
                int diff = nums[i] - nums[i - 1];
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[j] - nums[j - 1] == diff) {
                        ++result;
                    }
                    else {
                        break;
                    }
                }
            }
            return result;
        }
    }

    private void doTest(Solution solution, int[] nums, int expected) {

        int actual = 0;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.numberOfArithmeticSlices(nums);
            pass = actual == expected;
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.print("Fail     : ");
            Utils.printlnArray(nums);
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

        doTest(solution, new int[] { 1, 2, 3, 4 }, 3);
        doTest(solution, new int[] { 1 }, 0);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 1, 2, 3, 4, 5 }, 6);
        doTest(solution, new int[] { 1, 2, 3, 5, 7, 1, 2, 3 }, 3);
    }
}
