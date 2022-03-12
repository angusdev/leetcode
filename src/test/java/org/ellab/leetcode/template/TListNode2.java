package org.ellab.leetcode.template;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.ellab.leetcode.Utils;
import org.junit.jupiter.api.Test;

/**
 * n - XXXXXXXX.
 * 
 * 
 * 
 * @see <a href="">leetcode.com</a>
 */
public class TListNode2 {
    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    class Solution {
        public ListNode solve(ListNode head) {
            return head;
        }
    }

    class BruteForce extends Solution {
        @Override
        public ListNode solve(ListNode head) {
            return head;
        }
    }

    private ListNode toListNode(int[] l) {
        ListNode node = null;
        ListNode prev = null;
        for (int i = l.length - 1; i >= 0; i--) {
            node = new ListNode(l[i], prev);
            prev = node;
        }

        return node;
    }

    private int[] toIntArray(ListNode node) {
        ArrayList<Integer> list = new ArrayList<>();

        while (node != null) {
            list.add(node.val);
            node = node.next;
        }

        Integer[] array = list.toArray(new Integer[0]);

        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }

        return result;
    }

    private void doTest(Solution solution, int[] head, int[] expected) {
        int[] actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            ListNode actualNode = solution.solve(toListNode(head));
            actual = toIntArray(actualNode);
            pass = Utils.equals(expected, actual);
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.print("Fail     : ");
            Utils.printlnArray(head);
            System.out.print("Expected : ");
            Utils.printlnArray(expected);
            System.out.print("Actual   : ");
            Utils.printlnArray(actual);
            if (unexpectedEx != null) {
                unexpectedEx.printStackTrace();
            }
            fail("Failed. See console for oupput");
        }
    }

    @Test
    public void testGivenCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] {}, new int[] {});
        doTest(solution, new int[] { 1, 2, 3 }, new int[] { 1, 2, 3 });
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] {}, new int[] {});
    }

    private void testPerformance(Solution solution, int tries, int[] nums) {
        ListNode head = toListNode(nums);

        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.solve(head);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int[] nums = new int[] { 1, 2, 3 };

        int tries = 10000;
        testPerformance(new Solution(), tries, nums);
        testPerformance(new BruteForce(), Math.max(1, tries / 100), nums);
    }
}
