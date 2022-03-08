package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Iterator;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

/**
 * 414 - Third Maximum Number.
 * 
 * Given an integer array nums, return the third distinct maximum number in this array. If the third maximum does not
 * exist, return the maximum number.
 * 
 * @see <a href="https://leetcode.com/problems/third-maximum-number/">leetcode.com</a>
 */
public class J00414 {
    class Solution {
        public int thirdMax(int[] nums) {
            boolean max3Assigned = false;
            boolean max2Assigned = false;
            int max1 = nums[0];
            int max2 = 0;
            int max3 = 0;
            for (int n : nums) {
                if (n > max1) {
                    max3 = max2;
                    max2 = max1;
                    max1 = n;
                    max3Assigned = max2Assigned;
                    max2Assigned = true;
                }
                else if (n != max1) {
                    if (!max2Assigned || n > max2) {
                        max3 = max2;
                        max2 = n;
                        max3Assigned = max2Assigned;
                        max2Assigned = true;
                    }
                    else if (n != max2) {
                        if (!max3Assigned || n > max3) {
                            max3 = n;
                            max3Assigned = true;
                        }
                    }
                }
            }

            return max3Assigned ? max3 : max1;
        }
    }

    class SolutionUseCounter extends Solution {
        @Override
        public int thirdMax(int[] nums) {
            int assigned = 1;
            int max1 = nums[0];
            int max2 = 0;
            int max3 = 0;
            for (int n : nums) {
                if (n > max1) {
                    max3 = max2;
                    max2 = max1;
                    max1 = n;
                    assigned++;
                }
                else if (n != max1) {
                    if (assigned < 2 || n > max2) {
                        max3 = max2;
                        max2 = n;
                        assigned++;
                    }
                    else if (n != max2) {
                        if (assigned < 3 || n > max3) {
                            max3 = n;
                            assigned++;
                        }
                    }
                }
            }

            return assigned >= 3 ? max3 : max1;
        }
    }

    class SolutionUseLong extends Solution {
        @Override
        public int thirdMax(int[] nums) {
            long max1 = (long) Integer.MIN_VALUE - 1;
            long max2 = (long) Integer.MIN_VALUE - 1;
            long max3 = (long) Integer.MIN_VALUE - 1;
            for (long n : nums) {
                if (n > max1) {
                    max3 = max2;
                    max2 = max1;
                    max1 = n;
                }
                else if (n != max1) {
                    if (n > max2) {
                        max3 = max2;
                        max2 = n;
                    }
                    else if (n != max2) {
                        if (n > max3) {
                            max3 = n;
                        }
                    }
                }
            }

            return max3 >= Integer.MIN_VALUE ? (int) max3 : (int) max1;
        }
    }

    class BruteForce extends Solution {
        @Override
        public int thirdMax(int[] nums) {
            TreeSet<Integer> set = new TreeSet<>();
            for (int n : nums) {
                set.add(n);
            }

            if (set.size() >= 3) {
                Iterator<Integer> i = set.descendingIterator();
                i.next();
                i.next();
                return i.next();
            }
            else {
                return set.last();
            }
        }
    }

    private void doTest(Solution solution, int[] nums, int expected) {
        Integer actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.thirdMax(nums);
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

    // @Test
    public void testGivenCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 3, 2, 1 }, 1);
        doTest(solution, new int[] { 1, 2 }, 2);
        doTest(solution, new int[] { 2, 2, 3, 1 }, 1);
    }

    @Test
    public void testFailCases() {
        doTest(new Solution(), new int[] { -5, -4, -3 }, -5);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 4, 3, 2, 1 }, 2);
        doTest(solution, new int[] { -5, -4, -3 }, -5);
        doTest(solution, new int[] { 1, 2, -1 }, -1);
        doTest(solution, new int[] { 1, -1, 2 }, -1);
        doTest(solution, new int[] { -1, 1, 2 }, -1);
    }

    private void testRandomCases(Solution solution, int tries, int maxArraySize, int maxNum) {
        for (int i = 0; i < tries; i++) {
            int[] nums = Utils.randomIntArray(1, maxArraySize, -maxNum, maxNum);
            doTest(solution, nums, new BruteForce().thirdMax(nums));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 1000, 10000, Integer.MAX_VALUE);
    }

    private void testPerformance(Solution solution, int tries, int[] nums) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.thirdMax(nums);
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
        testPerformance(new SolutionUseCounter(), tries, nums);
        testPerformance(new SolutionUseLong(), tries, nums);
        testPerformance(new BruteForce(), tries / 100, nums);
    }
}
