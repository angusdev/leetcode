package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

/**
 * 287 - Find the Duplicate Number.
 * 
 * Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
 * 
 * There is only one repeated number in nums, return this repeated number.
 * 
 * You must solve the problem without modifying the array nums and uses only constant extra space.
 * 
 * @see <a href=https://leetcode.com/problems/find-the-duplicate-number/">leetcode.com</a>
 */
public class J00287 {
    class Solution {
        public int findDuplicate(int[] nums) {
            boolean[] used = new boolean[100001];

            for (int n : nums) {
                if (used[n]) {
                    return n;
                }
                used[n] = true;
            }

            return -1;
        }
    }

    class BruteForce extends Solution {
        @Override
        public int findDuplicate(int[] nums) {
            HashSet<Integer> set = new HashSet<>();

            for (int n : nums) {
                if (set.contains(n)) {
                    return n;
                }
                set.add(n);
            }

            return -1;
        }
    }

    private void doTest(Solution solution, int[] nums, int expected) {
        Integer actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.findDuplicate(nums);
            pass = expected == actual;
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

        doTest(solution, new int[] { 1, 3, 4, 2, 2 }, 2);
        doTest(solution, new int[] { 3, 1, 3, 4, 2 }, 3);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] {}, -1);
    }

    private void testRandomCases(Solution solution, int tries, int maxArraySize, int maxNum) {
        for (int i = 0; i < tries; i++) {
            int[] nums = Utils.randomIntArray(1, maxArraySize, 0, maxNum);
            doTest(solution, nums, new BruteForce().findDuplicate(nums));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 10000, 100, 10000);
    }

    private void testPerformance(Solution solution, int tries, int[] nums) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.findDuplicate(nums);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int tries = 100000;
        int arraySize = 10000;
        int maxNum = 100000;
        int[] nums = Utils.randomIntArray(arraySize, arraySize, 0, maxNum);
        testPerformance(new Solution(), tries, nums);
        testPerformance(new BruteForce(), Math.max(1, tries), nums);
    }
}
