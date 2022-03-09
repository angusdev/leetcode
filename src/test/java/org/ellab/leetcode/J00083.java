package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

/**
 * 83 - Remove Duplicates from Sorted List.
 * 
 * Given the head of a sorted linked list, delete all duplicates such that each element appears only once. Return the
 * linked list sorted as well.
 * 
 * <p>
 * <img src="https://assets.leetcode.com/uploads/2021/01/04/list1.jpg">
 * 
 * @see <a href="https://leetcode.com/problems/remove-duplicates-from-sorted-list/">leetcode.com</a>
 */
public class J00083 {
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
        public ListNode deleteDuplicates(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }

            ListNode result = head;
            ListNode resultHead = head;
            ListNode prev = head;
            head = head.next;
            while (head != null) {
                if (prev.val != head.val) {
                    result = result.next = head;
                }
                prev = head;
                head = head.next;
            }
            result.next = null;

            return resultHead;
        }
    }

    class BruteForce extends Solution {
        @Override
        public ListNode deleteDuplicates(ListNode head) {
            TreeSet<Integer> set = new TreeSet<>();
            while (head != null) {
                // value = true if duplicated
                set.add(head.val);
                head = head.next;
            }

            ListNode node = null;
            ListNode prev = null;
            for (int n : set) {
                node = new ListNode(n);
                if (prev != null) {
                    prev.next = node;
                }
                if (head == null) {
                    head = node;
                }
                prev = node;
            }

            return head;
        }

    }

    private ListNode toListNode(int[] l) {
        if (l == null) {
            return null;
        }

        ListNode node = null;
        ListNode prev = null;
        for (int i = l.length - 1; i >= 0; i--) {
            node = new ListNode(l[i], prev);
            prev = node;
        }

        return node;
    }

    private int[] toIntArray(ListNode node) {
        if (node == null) {
            return null;
        }

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
            ListNode actualNode = solution.deleteDuplicates(toListNode(head));
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

        doTest(solution, new int[] { 1, 1, 2 }, new int[] { 1, 2 });
        doTest(solution, new int[] { 1, 1, 2, 3, 3 }, new int[] { 1, 2, 3 });
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] {}, null);
        doTest(solution, new int[] { 1 }, new int[] { 1 });
        doTest(solution, new int[] { 1, 1 }, new int[] { 1 });
    }

    private void testPerformance(Solution solution, int tries, int[] nums) {
        ListNode head = toListNode(nums);

        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.deleteDuplicates(head);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int[] nums = new int[] { 1, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3,
                2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2,
                3, 2, 3, 2, 3, 2, 3 };

        int tries = 1000000;
        testPerformance(new Solution(), tries, nums);
        testPerformance(new BruteForce(), tries, nums);
    }
}
