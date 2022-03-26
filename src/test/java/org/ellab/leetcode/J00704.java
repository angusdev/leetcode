package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 704 - Binary Search.
 * 
 * Given an array of integers nums which is sorted in ascending order, and an integer target, write a function to search
 * target in nums. If target exists, then return its index. Otherwise, return -1.
 * 
 * You must write an algorithm with O(log n) runtime complexity.
 * 
 * @see <a href="https://leetcode.com/problems/binary-search/">leetcode.com</a>
 */
public class J00704 {
    class Solution {
        public int search(int[] nums, int target) {
            int start = 0;
            int end = nums.length - 1;
            while (start <= end) {
                int mid = (end + start) / 2;
                if (nums[mid] == target) {
                    return mid;
                }
                else if (nums[mid] < target) {
                    start = mid + 1;
                }
                else {
                    end = mid - 1;
                }
            }

            return -1;
        }
    }

    private void doTest(Solution solution, int[] nums, int target, int expected) {
        Integer actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.search(nums, target);
            pass = expected == actual;
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.print("Fail     : ");
            Utils.printlnArray(nums);
            System.out.println("           " + target);
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

        doTest(solution, new int[] { -1, 0, 3, 5, 9, 12 }, 9, 4);
        doTest(solution, new int[] { -1, 0, 3, 5, 9, 12 }, 2, -1);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 1 }, 1, 0);
        doTest(solution, new int[] { 0, 1 }, 1, 1);
        doTest(solution, new int[] { 1, 2 }, 1, 0);
    }

}
