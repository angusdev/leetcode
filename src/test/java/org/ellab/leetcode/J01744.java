package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * 1744 - Can You Eat Your Favorite Candy on Your Favorite Day.
 * 
 * You are given a (0-indexed) array of positive integers candiesCount where candiesCount[i] represents the number of
 * candies of the ith type you have. You are also given a 2D array queries where queries[i] = [favoriteTypei,
 * favoriteDayi, dailyCapi].
 * 
 * You play a game with the following rules:
 * 
 * 1. You start eating candies on day 0.
 * 
 * 2. You cannot eat any candy of type i unless you have eaten all candies of type i - 1.
 * 
 * 3. You must eat at least one candy per day until you have eaten all the candies.
 * 
 * Construct a boolean array answer such that answer.length == queries.length and answer[i] is true if you can eat a
 * candy of type favoriteTypei on day favoriteDayi without eating more than dailyCapi candies on any day, and false
 * otherwise. Note that you can eat different types of candy on the same day, provided that you follow rule 2.
 * 
 * Return the constructed array answer.
 * 
 * @see <a href="https://leetcode.com/problems/can-you-eat-your-favorite-candy-on-your-favorite-day/">leetcode.com</a>
 */
public class J01744 {
    class Solution {
        public boolean[] canEat(int[] candiesCount, int[][] queries) {
            boolean[] result = new boolean[queries.length];
            long[] candiesComm = new long[candiesCount.length + 1];
            for (int i = 1; i <= candiesCount.length; i++) {
                candiesComm[i] = candiesComm[i - 1] + candiesCount[i - 1];
            }
            for (int i = 0; i < queries.length; i++) {
                int type = queries[i][0];
                long day = queries[i][1];
                long cap = queries[i][2];
                result[i] = ((day + 1) * cap > candiesComm[type]) && day < candiesComm[type + 1];
            }

            return result;
        }
    }

    private void doTest(Solution solution, int[] candiesCount, int[][] queries, boolean[] expected) {
        boolean[] actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.canEat(candiesCount, queries);
            pass = Utils.equals(expected, actual);
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.print("Fail     : ");
            Utils.printlnArray(candiesCount);
            for (int i = 0; i < queries.length; i++) {
                System.out.print(i + " ");
                Utils.printlnArray(queries[i]);
            }
            System.out.print("Diff     : ");
            int diff = Utils.diff(expected, actual);
            System.out.println("[" + diff + "] expected=" + expected[diff] + ", actual=" + actual[diff]);
            System.out.println("Expected :");
            Utils.printlnArrayWithIndex(expected);
            System.out.println("Actual   :");
            Utils.printlnArrayWithIndex(actual);
            if (unexpectedEx != null) {
                unexpectedEx.printStackTrace();
            }
            fail("Failed. See console for oupput");
        }
    }

    @Test
    public void testGivenCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 7, 4, 5, 3, 8 }, new int[][] { { 0, 2, 2 }, { 4, 2, 4 }, { 2, 13, 1000000000 } },
                new boolean[] { true, false, true });
        doTest(solution, new int[] { 5, 2, 6, 4, 1 },
                new int[][] { { 3, 1, 2 }, { 4, 10, 3 }, { 3, 10, 100 }, { 4, 100, 30 }, { 1, 3, 1 } },
                new boolean[] { false, true, true, false, false });
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 10, 1, 5 }, new int[][] { { 1, 10, 1 }, { 1, 11, 1 }, { 1, 10, 2 }, { 2, 15, 1 },
                { 2, 16, 1 }, { 2, 0, 11 }, { 2, 0, 12 } },
                new boolean[] { true, false, true, true, false, false, true });
    }
}
