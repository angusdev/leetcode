package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 11 - Container With Most Water.
 * 
 * You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of
 * the ith line are (i, 0) and (i, height[i]).
 * 
 * Find two lines that together with the x-axis form a container, such that the container contains the most water.
 * 
 * Return the maximum amount of water a container can store.
 * 
 * Notice that you may not slant the container
 * 
 * @see <a href="https://leetcode.com/problems/container-with-most-water/">leetcode.com</a>
 */
public class J00011 {
    /**
     * Consider the case below.
     * 
     * <pre>
     *             |
     *             |       |
     *     |       |       |     
     *     |       |       |   |
     *     |       |       | | |
     * |   | |     |       | | |
     * | | | |     |       | | |
     * -------------------------
     * a b c d e f g  ...  x y z
     * 
     * 1. First we choose the left most and right most bar, a and z, and calculate the area az = 2x25 = 50. maxArea = 50.
     * 
     * 2. Since a is shorter than z (a < z), the area depends on the height of a, then we move to next bar that is
     * taller than a (the area of b-z is smaller than a-z since the height and width are both smaller).
     * 
     * 3. We found c and calculate the area of cz = 4x23 = 92, maxAare = 92 .
     * 
     * 4. Now since z < c, the area depends the height of the right bar. We move z to x (x > z) and calculate the area
     * cx = 5x21 = 105. maxArea = 105
     * 
     * 5. Now c < x, then we move the left bar until it reach g, area gx = 6x17 = 102, which < 105. maxArea = 105.
     * 
     * 6. Now move the right bar until it reach g, and end the loop.
     * </pre>
     */
    class Solution {
        public int maxArea(int[] height) {
            int maxArea = 0;
            int left = 0;
            int maxLeft = 0;
            int right = height.length - 1;
            int maxRight = 0;
            while (left < right) {
                if (height[left] <= height[right]) {
                    while (left < right && height[left] <= maxLeft) {
                        ++left;
                    }
                    maxLeft = height[left];
                }
                else {
                    while (left < right && height[right] <= maxRight) {
                        --right;
                    }
                    maxRight = height[right];
                }
                if (left < right) {
                    maxArea = Math.max(maxArea, Math.min(height[left], height[right]) * (right - left));
                }
            }

            return maxArea;
        }
    }

    /**
     * Similar to Solution 2, make some checking to stop earlier.
     */
    class Solution1 extends Solution {
        @Override
        public int maxArea(int[] height) {
            int maxArea = 0;

            int maxHeight = 0;
            for (int h : height) {
                maxHeight = h > maxHeight ? h : maxHeight;
            }

            int leftMax = 0;
            for (int i = 0; i < height.length - 1; i++) {
                if (height[i] > leftMax) {
                    leftMax = height[i];

                    int rightMax = 0;
                    for (int j = height.length - 1; j > i; j--) {
                        if (height[j] > rightMax) {
                            rightMax = height[j];

                            int area = Math.min(height[i], height[j]) * (j - i);
                            if (area > maxArea) {
                                maxArea = area;

                                // the right line is higher than left line, further test backward is not required as the
                                // height is bound by left
                                if (rightMax >= leftMax) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            return maxArea;
        }
    }

    /**
     * Consider the case
     * 
     * <pre>
     *     |
     * |   |       | 
     * | | |       |  
     * | | |       | |
     * ---------------
     * a b c  ...  y z
     * </pre>
     * 
     * When we try from a -> b -> c, since b is lower than a, it's area must be smaller than a and we can skip it. It
     * also apply the logic to the right hand side. So we test backward.
     */
    class Solution2 extends Solution {
        @Override
        public int maxArea(int[] height) {
            int maxArea = 0;

            int leftMax = 0;
            for (int i = 0; i < height.length - 1; i++) {
                if (height[i] > leftMax) {
                    leftMax = height[i];
                    int rightMax = 0;
                    for (int j = height.length - 1; j > i; j--) {
                        if (height[j] > rightMax) {
                            rightMax = height[j];
                            maxArea = Math.max(maxArea, Math.min(height[i], height[j]) * (j - i));
                        }
                    }
                }
            }

            return maxArea;
        }
    }

    class BruteForce extends Solution {
        @Override
        public int maxArea(int[] height) {
            int max = 0;

            for (int i = 0; i < height.length - 1; i++) {
                for (int j = i + 1; j < height.length; j++) {
                    max = Math.max(max, Math.min(height[i], height[j]) * (j - i));
                }
            }

            return max;
        }

    }

    private void doTest(Solution solution, int[] height, int expected) {
        Integer actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.maxArea(height);
            pass = actual == expected;
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.print("Fail     : ");
            ArrayUtils.printlnArray(height);
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

        doTest(solution, new int[] { 1, 8, 6, 2, 5, 4, 8, 3, 7 }, 49);
        doTest(solution, new int[] { 1, 1 }, 1);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 0, 1 }, 0);
        doTest(solution, new int[] { 0, 0 }, 0);
        doTest(solution, new int[] { 1, 5, 1 }, 2);
        doTest(new Solution(),
                new int[] { 2, 1, 5, 2, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 3, 4 }, 105);
    }

    private void testRandomCases(Solution solution, int tries, int maxArraySize, int maxNum) {
        for (int i = 0; i < tries; i++) {
            int[] height = ArrayUtils.randomIntArray(2, maxArraySize, 0, maxNum);

            doTest(solution, height, new BruteForce().maxArea(height));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 10000, 10, 10);
    }

    private void testPerformance(Solution solution, int tries, int[] height) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.maxArea(height);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int tries = 1000;
        int arraySize = 100000;
        int maxNum = 10000;
        int[] height = ArrayUtils.randomIntArray(arraySize, arraySize, 0, maxNum);
        testPerformance(new Solution(), tries, height);
        testPerformance(new Solution1(), tries, height);
        testPerformance(new Solution2(), tries, height);
        testPerformance(new BruteForce(), 1, height);
    }
}
