package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 121 - Best Time to Buy and Sell Stock.
 * 
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * 
 * You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future
 * to sell that stock.
 * 
 * Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
 * 
 * @see <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock/">leetcode.com</a>
 */
public class J00121 {
    class Solution {
        public int maxProfit(int[] prices) {
            int profit = 0;
            int min = prices[0];
            for (int i = 1; i < prices.length; i++) {
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

    class SimpleSolution extends Solution {
        @Override
        public int maxProfit(int[] prices) {
            int profit = 0;
            int min = prices[0];
            for (int i = 1; i < prices.length; i++) {
                min = Math.min(min, prices[i]);
                profit = Math.max(profit, prices[i] - min);
            }

            return profit;
        }
    }

    class BruteForce extends Solution {
        @Override
        public int maxProfit(int[] prices) {
            int profit = 0;

            for (int i = 0; i < prices.length - 1; i++) {
                for (int j = i + 1; j < prices.length; j++) {
                    profit = Math.max(profit, prices[j] - prices[i]);
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

        doTest(solution, new int[] { 7, 1, 5, 3, 6, 4 }, 5);
        doTest(solution, new int[] { 7, 6, 4, 3, 1 }, 0);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 1, 7, 6, 6 }, 6);
        doTest(solution, new int[] { 3, 3, 3, 1, 9 }, 8);
        doTest(solution, new int[] { 3, 3, 3, 8 }, 5);
    }

    private void testRandomCases(Solution solution, int tries, int maxArraySize, int maxNum) {
        for (int i = 0; i < tries; i++) {
            final int arraySize = NumberUtils.random(2, maxArraySize);
            int[] prices = new int[arraySize];
            for (int j = 0; j < arraySize; j++) {
                prices[j] = NumberUtils.random(maxNum);
            }

            doTest(solution, prices, new BruteForce().maxProfit(prices));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 10000, 10, 10);
        testRandomCases(new SimpleSolution(), 10000, 10, 100);
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
        int tries = 100000;
        int arraySize = 100000;
        int maxNum = 10000;
        int[] prices = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            prices[i] = NumberUtils.randomSigned(maxNum);
        }
        testPerformance(new Solution(), tries, prices);
        testPerformance(new SimpleSolution(), tries, prices);
    }
}
