package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 122 - Best Time to Buy and Sell Stock II.
 * 
 * You are given an integer array prices where prices[i] is the price of a given stock on the ith day.
 * 
 * On each day, you may decide to buy and/or sell the stock. You can only hold at most one share of the stock at any
 * time. However, you can buy it then immediately sell it on the same day.
 * 
 * Find and return the maximum profit you can achieve.
 * 
 * @see J00121
 * @see <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/">leetcode.com</a>
 */
public class J00122 {
    class Solution {
        public int maxProfit(int[] prices) {
            int profit = 0;

            for (int i = 1; i < prices.length; ++i) {
                if (prices[i] > prices[i - 1]) {
                    profit += prices[i] - prices[i - 1];
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

        doTest(solution, new int[] { 7, 1, 5, 3, 6, 4 }, 7);
        doTest(solution, new int[] { 1, 2, 3, 4, 5 }, 4);
        doTest(solution, new int[] { 7, 6, 4, 3, 1 }, 0);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 1, 7, 6, 6 }, 6);
        doTest(solution, new int[] { 3, 3, 3, 1, 9 }, 8);
        doTest(solution, new int[] { 1, 2, 3, 2, 3 }, 3);
    }
}
