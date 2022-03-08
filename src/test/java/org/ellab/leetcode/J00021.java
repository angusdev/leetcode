package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * 21 - Merge Two Sorted Lists.
 * 
 * You are given the heads of two sorted linked lists list1 and list2.
 * 
 * Merge the two lists in a one sorted list. The list should be made by splicing together the nodes of the first two
 * lists.
 * 
 * Return the head of the merged linked list.
 * 
 * @see <a href="https://leetcode.com/problems/merge-two-sorted-lists/">leetcode.com</a>
 */
public class J00021 {
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
        public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
            if (list1 == null) {
                return list2;
            }
            else if (list2 == null) {
                return list1;
            }

            // root is just place holder, the first element should be root.next
            ListNode root = new ListNode();
            ListNode node = root;
            while (list1 != null && list2 != null) {
                if (list1.val <= list2.val) {
                    node = node.next = list1;
                    list1 = list1.next;
                }
                else {
                    node = node.next = list2;
                    list2 = list2.next;
                }
            }
            node.next = list1 != null ? list1 : list2;

            return root.next;
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

    private void doTest(Solution solution, int[] list1, int[] list2, int[] expected) {
        int[] actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            ListNode actualNode = solution.mergeTwoLists(toListNode(list1), toListNode(list2));
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
            Utils.printlnArray(list1);
            System.out.print("           ");
            Utils.printlnArray(list2);
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

        doTest(solution, new int[] { 1, 2, 4 }, new int[] { 1, 3, 4 }, new int[] { 1, 1, 2, 3, 4, 4 });
        doTest(solution, new int[] {}, new int[] {}, new int[] {});
        doTest(solution, new int[] {}, new int[] { 0 }, new int[] { 0 });
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 1 }, new int[] { 1 }, new int[] { 1, 1 });
        doTest(solution, new int[] { 9 }, new int[] { 1 }, new int[] { 1, 9 });
    }
}
