package org.ellab.leetcode.template;

import static org.junit.jupiter.api.Assertions.fail;

import org.ellab.leetcode.Utils;
import org.junit.jupiter.api.Test;

/**
 * n - XXXXXXXX.
 * 
 * 
 * 
 * @see <a href="">leetcode.com</a>
 */
public class TIntIntArray {
    class Solution {
        public int solve(int[] nums) {
            return 0;
        }
    }

    class BruteForce extends Solution {
        @Override
        public int solve(int[] nums) {
            return 0;
        }
    }

    private void doTest(Solution solution, int[] nums, int expected) {
        Integer actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.solve(nums);
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

        doTest(solution, new int[] {}, 0);
        doTest(solution, new int[] {}, 0);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] {}, 0);
    }

    private void testRandomCases(Solution solution, int tries, int maxArraySize, int maxNum) {
        for (int i = 0; i < tries; i++) {
            int[] nums = Utils.randomIntArray(1, maxArraySize, 0, maxNum);
            doTest(solution, nums, new BruteForce().solve(nums));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 10000, 100, 10000);
    }

    private void testPerformance(Solution solution, int tries, int[] nums) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.solve(nums);
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
        testPerformance(new BruteForce(), Math.max(1, tries / 100), nums);
    }
}
