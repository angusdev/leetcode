package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * 1 - Two Sum.
 * 
 * Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to
 * target.
 * 
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 * 
 * You can return the answer in any order.
 * 
 * @see <a href="https://leetcode.com/problems/add-two-numbers/">leetcode.com</a>
 *
 */
public class J00001 {
    class Solution {
        public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(nums[0], 0);
            for (int i = 1; i < nums.length; i++) {
                int remaining = target - nums[i];
                if (map.containsKey(remaining)) {
                    return new int[] { map.get(remaining), i };
                }
                else {
                    map.put(nums[i], i);
                }
            }

            return null;
        }
    }

    class BruteForce extends Solution {
        @Override
        public int[] twoSum(int[] nums, int target) {
            for (int i = 0; i < nums.length; i++) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[i] + nums[j] == target) {
                        return new int[] { i, j };
                    }
                }
            }

            assert false;
            return null;
        }
    }

    private void doTest(Solution solution, int[] nums, int target, int[] expected) {
        int[] actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.twoSum(nums, target);
            if (nums[actual[0]] + nums[actual[1]] == target) {
                if (expected != null) {
                    if (actual[0] == expected[0] && actual[1] == expected[1] && actual.length == expected.length) {
                        pass = true;
                    }
                }
                else {
                    // for random generated case, it is not guarantee there is only one solution, so only test the
                    // target is match
                    pass = true;
                }
            }
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.print("Fail     : ");
            ArrayUtils.printlnArray(nums);
            System.out.println("           target=" + target);
            System.out.print("Expected : ");
            ArrayUtils.printlnArray(expected);
            System.out.print("Actual   : ");
            ArrayUtils.printlnArray(actual);
            if (unexpectedEx != null) {
                unexpectedEx.printStackTrace();
            }
            fail("Failed. See console for oupput");
        }
    }

    @Test
    public void testGivenCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 2, 7, 11, 15 }, 9, new int[] { 0, 1 });
        doTest(solution, new int[] { 3, 2, 4 }, 6, new int[] { 1, 2 });
        doTest(solution, new int[] { 3, 3 }, 6, new int[] { 0, 1 });
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 0, 1, 2 }, 1, new int[] { 0, 1 });
        doTest(solution, new int[] { 0, 1, 2 }, 2, new int[] { 0, 2 });
        doTest(solution, new int[] { -1, 1, 2 }, 0, new int[] { 0, 1 });
        doTest(solution, new int[] { 2, -1, 1 }, 0, new int[] { 1, 2 });
    }

    private void testRandomCases(Solution solution, int tries, int maxArraySize, int maxNum) {
        for (int i = 0; i < tries; i++) {
            final int arraySize = NumberUtils.random(2, maxArraySize);
            int[] nums = new int[arraySize];
            for (int j = 0; j < arraySize; j++) {
                nums[j] = NumberUtils.randomSigned(maxNum);
            }
            int expected1 = NumberUtils.random(arraySize - 1);
            int expected2 = NumberUtils.random(arraySize - 1);
            while (expected2 == expected1) {
                expected2 = NumberUtils.random(arraySize - 1);
            }
            int target = nums[expected1] + nums[expected2];

            doTest(solution, nums, target, null);
        }
    }

    @Test
    public void testRandomCases() {
        final Solution solution = new Solution();

        testRandomCases(solution, 10000, 10, 10);
        testRandomCases(solution, 10000, 10000, (int) Math.pow(10, 9));
    }
}
