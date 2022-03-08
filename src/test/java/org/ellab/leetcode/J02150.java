package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * 2150 - Find All Lonely Numbers in the Array.
 * 
 * You are given an integer array nums. A number x is lonely when it appears only once, and no adjacent numbers (i.e. x
 * + 1 and x - 1) appear in the array.
 * 
 * Return all lonely numbers in nums. You may return the answer in any order.
 * 
 * @see <a href="https://leetcode.com/problems/find-all-lonely-numbers-in-the-array/">leetcode.com</a>
 */
public class J02150 {
    class Solution {
        public List<Integer> findLonely(int[] nums) {
            List<Integer> result = new ArrayList<>();

            // THe main loop assumes at least 2 numbers
            if (nums.length == 0) {
                return result;
            }

            if (nums.length == 1) {
                result.add(nums[0]);
                return result;
            }

            // Sort the array so don't need to remember the number
            Arrays.sort(nums);

            int last = nums.length - 1;
            int i = 1;
            while (i < last) {
                if (nums[i] == nums[i + 1] || nums[i] + 1 == nums[i + 1]) {
                    // e.g. 1, 2, 4, when we are checking 1, we know that 2 is also not lonely, so we skip it
                    i += 2;
                }
                else {
                    if (nums[i] != nums[i - 1] && nums[i] != nums[i - 1] + 1) {
                        result.add(nums[i]);
                    }
                    i++;
                }
            }

            // check first and last item, it is excluded from the loop to avoid boundary check
            if (nums[0] != nums[1] && nums[0] + 1 != nums[1]) {
                result.add(nums[0]);
            }
            if (nums[last] != nums[last - 1] && nums[last] != nums[last - 1] + 1) {
                result.add(nums[last]);
            }

            return result;
        }
    }

    class SolutionMoreReadable extends Solution {
        @Override
        public List<Integer> findLonely(int[] nums) {
            List<Integer> result = new ArrayList<>();

            // Sort the array so don't need to remember the number
            Arrays.sort(nums);

            for (int i = 0; i < nums.length; i++) {
                if (i == 0 || nums[i] - nums[i - 1] > 1) {
                    if (i == nums.length - 1 || nums[i + 1] - nums[i] > 1) {
                        result.add(nums[i]);
                    }
                }
            }

            return result;
        }
    }

    class SolutionUseMap extends Solution {
        @Override
        public List<Integer> findLonely(int[] nums) {
            List<Integer> result = new ArrayList<>();
            Map<Integer, Integer> map = new HashMap<>();

            for (int n : nums) {
                if (map.containsKey(n)) {
                    map.put(n, map.get(n) + 1);
                }
                else {
                    map.put(n, 1);
                }
            }

            for (Map.Entry<Integer, Integer> e : map.entrySet()) {
                if (e.getValue() == 1) {
                    if (!map.containsKey(e.getKey() - 1) && !map.containsKey(e.getKey() + 1)) {
                        result.add(e.getKey());
                    }
                }
            }

            return result;
        }
    }

    class BruteForce extends Solution {
        @Override
        public List<Integer> findLonely(int[] nums) {
            int maxNum = 1000000;
            List<Integer> result = new ArrayList<>();
            int[] cache = new int[maxNum + 1];

            for (int n : nums) {
                cache[n]++;
            }

            for (int i = 0; i <= maxNum; i++) {
                if (cache[i] == 1) {
                    if ((i == 0 || cache[i - 1] == 0) && (i == maxNum || cache[i + 1] == 0)) {
                        result.add(i);
                    }
                }
            }

            return result;
        }

    }

    private void doTest(Solution solution, int[] nums, int[] expected) {
        List<Integer> actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.findLonely(nums);
            pass = Utils.equalsUnordered(expected, Utils.integerArrayToIntArray(actual.toArray(new Integer[0])));
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.print("Fail     : ");
            Utils.printlnArray(nums);
            System.out.println("Expected : ");
            System.out.print("           ");
            Utils.printlnArray(expected);
            System.out.println("Actual   : ");
            if (actual != null) {
                System.out.print("           ");
                Utils.printlnArray(Utils.integerArrayToIntArray(actual.toArray(new Integer[0])));
            }
            else {
                System.out.println(actual);
            }

            if (unexpectedEx != null) {
                unexpectedEx.printStackTrace();
            }
            fail("Failed. See console for oupput");
        }
    }

    @Test
    public void testGivenCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 10, 6, 5, 8 }, new int[] { 10, 8 });
        doTest(solution, new int[] { 1, 3, 5, 3 }, new int[] { 1, 5 });
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] {}, new int[] {});
        doTest(solution, new int[] { 0 }, new int[] { 0 });
        doTest(solution, new int[] { 0, 1 }, new int[] {});
        doTest(solution, new int[] { 1000000 }, new int[] { 1000000 });
        doTest(solution, new int[] { 1000000, 999999, 999997 }, new int[] { 999997 });
    }

    private void testRandomCases(Solution solution, int tries, int maxArraySize, int maxNum) {
        for (int i = 0; i < tries; i++) {
            int[] nums = Utils.randomIntArray(1, maxArraySize, 0, maxNum);
            doTest(solution, nums,
                    Utils.integerArrayToIntArray(new BruteForce().findLonely(nums).toArray(new Integer[0])));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 100, 100, 100);
        testRandomCases(new Solution(), 100, 10000, 1000000);
    }

    private void testPerformance(Solution solution, int tries, int[] nums) {
        for (int i = 0; i < Math.min(tries / 10, 100); i++) {
            solution.findLonely(nums);
        }
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.findLonely(nums);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int tries = 1000;
        int arraySize = 10000;
        int maxNum = 10000;
        int[] nums = Utils.randomIntArray(arraySize, arraySize, 0, maxNum);
        testPerformance(new Solution(), tries, nums);
        testPerformance(new SolutionMoreReadable(), tries, nums);
        testPerformance(new SolutionUseMap(), tries, nums);
        testPerformance(new BruteForce(), tries, nums);

        // array size is bigger, map is slower
        System.out.println();
        arraySize = 10000;
        nums = Utils.randomIntArray(arraySize, arraySize, 0, maxNum);
        testPerformance(new Solution(), tries, nums);
        testPerformance(new SolutionMoreReadable(), tries, nums);
        testPerformance(new SolutionUseMap(), tries, nums);
        testPerformance(new BruteForce(), tries, nums);
    }
}
