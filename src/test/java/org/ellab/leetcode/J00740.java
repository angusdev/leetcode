package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * 740 - Delete and Earn.
 * 
 * You are given an integer array nums. You want to maximize the number of points you get by performing the following
 * operation any number of times:
 * 
 * Pick any nums[i] and delete it to earn nums[i] points. Afterwards, you must delete every element equal to nums[i] - 1
 * and every element equal to nums[i] + 1.
 * 
 * Return the maximum number of points you can earn by applying the above operation some number of times.
 * 
 * @see <a href="https://leetcode.com/problems/delete-and-earn/">leetcode.com</a>
 */
public class J00740 {
    /**
     * Modify from SimpleSolution, to reduce the number of calculation.
     * 
     * Consider this case
     * 
     * <pre>
     * 0 1 2 3 4 5 6 7 8  9 10 11 12
     * 0 2 0 3 8 5 6 7 8 18 10  0 12
     * </pre>
     * 
     * We can see that we can earn the 1 and 12 since they have no adjacent, we only need to consider 3-10. Then we use
     * recursive to get the possible earn for 3-10. Firstly we should either earn 3 or 4, there is no point to skip both
     * 3 and 4. No we consider two cases:
     * 
     * <pre>
     * 3 + {5, 6, 7, 8, 9, 10}
     * 4 + {6, 7, 8, 9, 10}
     * </pre>
     *
     * This break down the number series to smaller series. However the worse case scenario is same as SimpleSolution.
     * I.e. all number exists in the array.
     */
    class Solution {
        public int deleteAndEarn(int[] nums) {
            // Pre build the number earn map
            int max = 0;
            for (int num : nums) {
                if (num > max) {
                    max = num;
                }
            }
            int[] earnMap = new int[max + 1];
            // Or just hardcode the array size
            // int[] earnMap = new int[10001];
            for (int num : nums) {
                earnMap[num] += num;
            }

            int totalEarn = 0;
            int start = -1;
            boolean oneItemOnly = true;
            Map<Integer, Integer> cachedResult = new HashMap<>();
            for (int i = 0; i < earnMap.length; i++) {
                if (earnMap[i] == 0) {
                    if (start >= 0) {
                        // previous have one or more are non zero
                        if (oneItemOnly) {
                            // only one item, always earn the previous one
                            totalEarn += earnMap[i - 1];
                        }
                        else {
                            // calculate the best earn of this segment
                            totalEarn += deleteAndEarn(earnMap, start, i - 1, cachedResult);
                        }
                        // reset the count, start a new segment
                        start = -1;
                        oneItemOnly = true;
                        continue;
                    }
                    else {
                        // previous all 0, skip
                        continue;
                    }
                }
                else if (start >= 0) {
                    oneItemOnly = false;
                    continue;
                }
                else {
                    start = i;
                }
            }

            if (start >= 0) {
                totalEarn += deleteAndEarn(earnMap, start, earnMap.length - 1, cachedResult);
            }
            return totalEarn;
        }

        // recursive
        private int deleteAndEarn(int[] earnMap, int start, int end, Map<Integer, Integer> cachedResult) {
            if (start == end) {
                return earnMap[start];
            }
            if (start > end) {
                return 0;
            }
            if (cachedResult.containsKey(start)) {
                return cachedResult.get(start);
            }

            int earnThis = earnMap[start] + deleteAndEarn(earnMap, start + 2, end, cachedResult);
            int earnNext = earnMap[start + 1] + deleteAndEarn(earnMap, start + 3, end, cachedResult);

            int earn = Math.max(earnThis, earnNext);
            cachedResult.put(start, earn);

            return earn;
        }
    }

    /**
     * Let's consider this case
     * 
     * <pre>
     * { 1, 10, 4, 1, 3, 4, 5, 7, 12, 4, 9, 8, 9 }
     * </pre>
     * 
     * First sort it
     * 
     * <pre>
     * { 1, 1, 3, 4, 4, 5, 6, 7, 8, 9, 9, 10, 12 }
     * </pre>
     * 
     * Then restructure the array and put the total can-earn for each number
     * 
     * <pre>
     * 0 1 2 3 4 5 6 7 8  9 10 11 12
     * 0 2 0 3 8 5 6 7 8 18 10  0 12
     * </pre>
     * 
     * Then compare the earn the first element or earn the next element recursively. Use an Map to cache the calculated
     * result.
     */
    class SimpleSolution extends Solution {
        @Override
        public int deleteAndEarn(int[] nums) {
            // Pre build the number earn map
            int[] earnMap = new int[10001];
            for (int num : nums) {
                earnMap[num] += num;
            }

            int totalEarn = 0;
            Map<Integer, Integer> cachedResult = new HashMap<>();
            totalEarn = deleteAndEarn(earnMap, 0, cachedResult);
            return totalEarn;
        }

        // recursive
        private int deleteAndEarn(int[] earnMap, int start, Map<Integer, Integer> cachedResult) {
            if (start >= earnMap.length) {
                return 0;
            }
            if (start == earnMap.length - 1) {
                return earnMap[start];
            }
            if (cachedResult.containsKey(start)) {
                return cachedResult.get(start);
            }

            int earnThis = earnMap[start] + deleteAndEarn(earnMap, start + 2, cachedResult);
            int earnNext = earnMap[start + 1] + deleteAndEarn(earnMap, start + 3, cachedResult);

            int earn = Math.max(earnThis, earnNext);
            cachedResult.put(start, earn);

            return earn;
        }
    }

    class BruteForce extends Solution {
        @Override
        public int deleteAndEarn(int[] nums) {
            Set<Integer> scoredNums = new HashSet<>();
            Set<Integer> unscoredNums = new HashSet<>();
            return deleteAndEarn(nums, 0, scoredNums, unscoredNums);
        }

        private int deleteAndEarn(int[] nums, int start, Set<Integer> scoredNums, Set<Integer> unscoredNums) {
            int score = 0;
            for (int i = start; i < nums.length; i++) {
                int num = nums[i];
                if (scoredNums.contains(num)) {
                    score += num;
                }
                else if (scoredNums.contains(num - 1) || scoredNums.contains(num + 1)) {
                    continue;
                }
                else if (unscoredNums.contains(num)) {
                    continue;
                }
                else {
                    Set<Integer> set1 = new HashSet<>();
                    Set<Integer> set2 = new HashSet<>();
                    set1.addAll(scoredNums);
                    set2.addAll(unscoredNums);
                    set1.add(num);
                    int scoreIfEarnThis = score + num + deleteAndEarn(nums, i + 1, set1, set2);

                    set1 = new HashSet<>();
                    set2 = new HashSet<>();
                    set1.addAll(scoredNums);
                    set2.addAll(unscoredNums);
                    set2.add(num);
                    int scoreIfNotEarnThis = score + deleteAndEarn(nums, i + 1, set1, set2);

                    return Math.max(scoreIfEarnThis, scoreIfNotEarnThis);
                }
            }

            return score;
        }
    }

    private void doTest(Solution solution, int[] nums, int expected) {
        Integer actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.deleteAndEarn(nums);
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

        doTest(solution, new int[] { 3, 4, 2 }, 6);
        doTest(solution, new int[] { 2, 2, 3, 3, 3, 4 }, 9);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 2, 2, 3, 3, 3, 4, 4 }, 12);
        doTest(solution, new int[] { 1, 1, 1, 1, 1, 1, 2, 2, 3, 3, 3, 4, 4 }, 15);
        doTest(solution, new int[] { 1, 1, 5, 5, 8, 10, 12 }, 42);
        doTest(solution, new int[] { 1, 10 }, 11);
        doTest(solution, new int[] { 1, 10, 12, 13 }, 24);
        doTest(solution, new int[] { 1, 1, 3, 4, 4, 5, 6, 7, 8, 9, 9, 10, 12 }, 47);
    }

    private void testRandomCases(Solution solution, int tries, int maxArraySize, int maxNum) {
        for (int i = 0; i < tries; i++) {
            int[] nums = Utils.randomIntArray(1, maxArraySize, 0, maxNum);
            doTest(solution, nums, new BruteForce().deleteAndEarn(nums));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 100, 20, 1000);
    }

    private void testPerformance(Solution solution, int tries, int[] nums) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.deleteAndEarn(nums);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int tries = 10000;
        int arraySize = 10000;
        int maxNum = 10000;
        int[] nums = Utils.randomIntArray(arraySize, arraySize, 0, maxNum);
        testPerformance(new Solution(), tries, nums);
        testPerformance(new SimpleSolution(), tries, nums);
    }
}
