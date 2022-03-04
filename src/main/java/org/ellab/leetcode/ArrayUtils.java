package org.ellab.leetcode;

public class ArrayUtils {
    public static void printlnArray(int[] nums) {
        if (nums == null) {
            System.out.println(nums);
            return;
        }

        System.out.print("{");
        for (int i = 0; i < nums.length; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(nums[i]);
        }
        System.out.println("}");
    }
}
