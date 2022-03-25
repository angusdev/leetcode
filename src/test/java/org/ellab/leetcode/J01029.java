package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * 1029 - Two City Scheduling.
 * 
 * A company is planning to interview 2n people. Given the array costs where costs[i] = [aCosti, bCosti], the cost of
 * flying the ith person to city a is aCosti, and the cost of flying the ith person to city b is bCosti.
 * 
 * Return the minimum cost to fly every person to a city such that exactly n people arrive in each city.
 * 
 * @see <a href="https://leetcode.com/problems/two-city-scheduling/">leetcode.com</a>
 */
public class J01029 {
    class Solution {
        public int twoCitySchedCost(int[][] costs) {
            Arrays.sort(costs, (a, b) -> {
                return a[0] - a[1] - (b[0] - b[1]);
            });

            int result = 0;
            int half = costs.length / 2;
            for (int i = 0; i < half; i++) {
                result += costs[i][0];
            }
            for (int i = half; i < costs.length; i++) {
                result += costs[i][1];
            }

            return result;
        }
    }

    private void doTest(Solution solution, int[][] costs, int expected) {
        Integer actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.twoCitySchedCost(costs);
            pass = expected == actual;
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.println("Fail     :");
            for (int[] row : costs) {
                Utils.printlnArray(row);
            }
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

        doTest(solution, new int[][] { { 10, 20 }, { 30, 200 }, { 400, 50 }, { 30, 20 } }, 110);
        doTest(solution,
                new int[][] { { 259, 770 }, { 448, 54 }, { 926, 667 }, { 184, 139 }, { 840, 118 }, { 577, 469 } },
                1859);
        doTest(solution, new int[][] { { 515, 563 }, { 451, 713 }, { 537, 709 }, { 343, 819 }, { 855, 779 },
                { 457, 60 }, { 650, 359 }, { 631, 42 } }, 3086);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[][] { { 10, 20 }, { 5, 6 } }, 16);
        doTest(solution, new int[][] { { 10, 20 }, { 20, 10 } }, 20);
        doTest(solution, new int[][] { { 10, 20 }, { 5, 6 }, { 1, 3 }, { 2, 2 } }, 19);
        doTest(solution, new int[][] { { 10, 10 }, { 10, 10 }, { 10, 10 }, { 10, 10 } }, 40);
    }
}
