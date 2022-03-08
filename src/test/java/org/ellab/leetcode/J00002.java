package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * 2 - Add Two Numbers.
 * 
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse
 * order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.
 * 
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * 
 * @see <a href="https://leetcode.com/problems/add-two-numbers/">leetcode.com</a>
 */
public class J00002 {
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
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode root = null;
            ListNode node = null;
            ListNode prev = null;
            int carry = 0;
            while (l1 != null || l2 != null || carry != 0) {
                int n = (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0) + carry;
                node = new ListNode(n % 10);
                if (prev != null) {
                    prev.next = node;
                }
                prev = node;
                if (root == null) {
                    root = node;
                }
                carry = n >= 10 ? 1 : 0;
                l1 = l1 != null ? l1.next : null;
                l2 = l2 != null ? l2.next : null;
            }

            return root;
        }
    }

    class SimpleSolution extends Solution {
        @Override
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            return addTwoNumbers(l1, l2, 0);
        }

        private ListNode addTwoNumbers(ListNode l1, ListNode l2, int carry) {
            if (l1 == null && l2 == null && carry == 0) {
                return null;
            }
            int n = (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0) + carry;
            return new ListNode(n % 10,
                    addTwoNumbers(l1 != null ? l1.next : null, l2 != null ? l2.next : null, n >= 10 ? 1 : 0));
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

    private void doTest(Solution solution, int[] l1, int[] l2, int[] expected) {
        int[] actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            ListNode actualNode = solution.addTwoNumbers(toListNode(l1), toListNode(l2));
            actual = toIntArray(actualNode);
            if (actual.length == expected.length) {
                pass = true;
                for (int i = 0; i < actual.length; i++) {
                    if (actual[i] != expected[i]) {
                        pass = false;
                        break;
                    }
                }
            }
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.print("Fail     : ");
            Utils.printlnArray(l1);
            System.out.print("           ");
            Utils.printlnArray(l2);
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

        doTest(solution, new int[] { 2, 4, 3 }, new int[] { 5, 6, 4 }, new int[] { 7, 0, 8 });
        doTest(solution, new int[] { 0 }, new int[] { 0 }, new int[] { 0 });
        doTest(solution, new int[] { 9, 9, 9, 9, 9, 9, 9 }, new int[] { 9, 9, 9, 9 },
                new int[] { 8, 9, 9, 9, 0, 0, 0, 1 });
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 9 }, new int[] { 1 }, new int[] { 0, 1 });

        doTest(solution, new int[] { 9 }, new int[] { 1, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
                new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
    }

    private void testPerformance(Solution solution, int tries, int[] l1, int[] l2) {
        ListNode n1 = toListNode(l1);
        ListNode n2 = toListNode(l2);

        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.addTwoNumbers(n1, n2);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int[] l1 = new int[] { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
                9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
                9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 };
        int[] l2 = new int[] { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
                9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
                9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 };

        int tries = 10000000;
        testPerformance(new Solution(), tries, l1, l2);
        testPerformance(new SimpleSolution(), tries, l1, l2);
    }
}
