package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import org.junit.jupiter.api.Test;

/**
 * 1046 - Last Stone Weight.
 * 
 * You are given an array of integers stones where stones[i] is the weight of the ith stone.
 * 
 * We are playing a game with the stones. On each turn, we choose the heaviest two stones and smash them together.
 * Suppose the heaviest two stones have weights x and y with x <= y. The result of this smash is:
 * 
 * <ul>
 * <li>If x == y, both stones are destroyed, and</li>
 * <li>If x != y, the stone of weight x is destroyed, and the stone of weight y has new weight y - x.</li>
 * </ul>
 * 
 * At the end of the game, there is at most one stone left.
 * 
 * Return the smallest possible weight of the left stone. If there are no stones left, return 0.
 * 
 * @see <a href="">leetcode.com</a>
 */
public class J01046 {
    class Solution {
        public int lastStoneWeight(int[] stones) {
            Queue<Integer> q = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            });
            for (int s : stones) {
                q.offer(s);
            }

            while (q.size() > 1) {
                int a = q.poll();
                int b = q.poll();
                if (a != b) {
                    q.offer(a - b);
                }
            }

            if (q.size() == 1) {
                return q.poll();
            }

            return 0;
        }
    }

    private void doTest(Solution solution, int[] stones, int expected) {
        Integer actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.lastStoneWeight(stones);
            pass = expected == actual;
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.print("Fail     : ");
            Utils.printlnArray(stones);
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

        doTest(solution, new int[] { 2, 7, 4, 1, 8, 1 }, 1);
        doTest(solution, new int[] { 1 }, 1);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 1, 1, 1, 1 }, 0);
        doTest(solution, new int[] { 1, 2 }, 1);
        doTest(solution, new int[] { 1, 2, 2 }, 1);
        doTest(solution, new int[] { 1, 2, 2, 2 }, 1);
    }
}
