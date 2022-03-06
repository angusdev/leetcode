package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 123 - Best Time to Buy and Sell Stock III.
 * 
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * 
 * Find the maximum profit you can achieve. You may complete at most two transactions.
 * 
 * Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy
 * again).
 * 
 * @see J00122
 * @see <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/">leetcode.com</a>
 */
public class J00123 {
    class Solution {
        public int maxProfit(int[] prices) {
            int[] leftToRightMaxProfit = new int[prices.length];

            int maxProfit = 0;

            // left to right
            int profit1 = 0;
            int min1 = prices[0];
            for (int i = 1; i < prices.length; ++i) {
                final int price = prices[i];
                if (price < min1) {
                    min1 = price;
                }
                else {
                    final int diff = price - min1;
                    profit1 = diff > profit1 ? diff : profit1;
                }
                leftToRightMaxProfit[i] = profit1;
            }

            // right to left
            int profit2 = 0;
            int max2 = prices[prices.length - 1];
            for (int i = prices.length - 2; i >= 0; --i) {
                final int price = prices[i];
                if (price > max2) {
                    max2 = price;
                }
                else {
                    final int diff = max2 - price;
                    profit2 = diff > profit2 ? diff : profit2;
                }
                int profit = leftToRightMaxProfit[i] + profit2;
                maxProfit = profit > maxProfit ? profit : maxProfit;
            }

            return maxProfit;
        }
    }

    class SimpleSolution extends Solution {
        @Override
        public int maxProfit(int[] prices) {
            int[] leftToRightMaxProfit = new int[prices.length];
            int[] rightToLeftMaxProfit = new int[prices.length];

            // left to right, max profit if sell at day i
            int profit1 = 0;
            int min1 = prices[0];
            for (int i = 1; i < prices.length; ++i) {
                final int price = prices[i];
                if (price < min1) {
                    min1 = price;
                }
                else {
                    final int diff = price - min1;
                    profit1 = diff > profit1 ? diff : profit1;
                }
                leftToRightMaxProfit[i] = profit1;
            }

            // right to left, max profit if buy at day i
            int profit2 = 0;
            int max2 = prices[prices.length - 1];
            for (int i = prices.length - 2; i >= 0; --i) {
                final int price = prices[i];
                if (price > max2) {
                    max2 = price;
                }
                else {
                    final int diff = max2 - price;
                    profit2 = diff > profit2 ? diff : profit2;
                }
                rightToLeftMaxProfit[i] = profit2;
            }

            int maxProfit = 0;

            for (int i = 1; i < prices.length; i++) {
                int profit = leftToRightMaxProfit[i] + rightToLeftMaxProfit[i];
                maxProfit = profit > maxProfit ? profit : maxProfit;
            }

            return maxProfit;
        }
    }

    class BruteForce extends Solution {
        @Override
        public int maxProfit(int[] prices) {
            int maxProfit = 0;

            for (int i = 1; i < prices.length; ++i) {
                if (prices[i] > prices[i - 1]) {
                    // profit of sell at day i and buy at day i + 1
                    final int profit = maxProfit(prices, 0, i) + maxProfit(prices, i + 1, prices.length - 1);
                    maxProfit = profit > maxProfit ? profit : maxProfit;
                }
            }

            return maxProfit;
        }

        private int maxProfit(int[] prices, int start, int end) {
            if (start > prices.length - 1 || end > prices.length - 1) {
                return 0;
            }

            int profit = 0;
            int min = prices[start];
            for (int i = start + 1; i <= end; i++) {
                final int price = prices[i];
                if (price < min) {
                    min = price;
                }
                else {
                    final int diff = price - min;
                    profit = diff > profit ? diff : profit;
                }
            }

            return profit;
        }

    }

    private void doTest(Solution solution, int[] prices, int expected) {
        Exception unexpectedEx = null;
        boolean pass = false;
        int actual = 0;

        try {
            actual = solution.maxProfit(prices);
            pass = actual == expected;
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.print("Fail     : ");
            ArrayUtils.printlnArray(prices);
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

        doTest(solution, new int[] { 3, 3, 5, 0, 0, 3, 1, 4 }, 6);
        doTest(solution, new int[] { 1, 2, 3, 4, 5 }, 4);
        doTest(solution, new int[] { 7, 6, 4, 3, 1 }, 0);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 1, 1, 0, 6 }, 6);
        doTest(solution, new int[] { 1, 7, 6, 6 }, 6);
        doTest(solution, new int[] { 3, 3, 3, 1, 9 }, 8);
        doTest(solution, new int[] { 8, 1, 7, 1 }, 6);
        doTest(solution, new int[] { 2, 4 }, 2);
        doTest(solution, new int[] { 0, 0 }, 0);
        doTest(solution, new int[] { 1, 0 }, 0);
    }

    private void testRandomCases(Solution solution, int tries, int maxArraySize, int maxNum) {
        for (int i = 0; i < tries; i++) {
            int[] prices = ArrayUtils.randomIntArray(2, maxArraySize, 0, maxNum);
            doTest(solution, prices, new BruteForce().maxProfit(prices));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 100000, 100, 100);
    }

    private void testPerformance(Solution solution, int tries, int[] prices) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.maxProfit(prices);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int tries = 10000;
        int arraySize = 10000;
        int maxNum = 100000;
        int[] prices = ArrayUtils.randomIntArray(arraySize, arraySize, 0, maxNum);
        testPerformance(new Solution(), tries, prices);
        testPerformance(new SimpleSolution(), tries, prices);
        testPerformance(new BruteForce(), tries / 100, prices);
    }
}
