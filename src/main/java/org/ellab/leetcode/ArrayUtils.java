package org.ellab.leetcode;

public class ArrayUtils {
    public static void printlnArray(int[] array) {
        if (array == null) {
            System.out.println(array);
            return;
        }

        System.out.print("{");
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(array[i]);
        }
        System.out.println("}");
    }

    public static void printlnArrayWithIndex(int[] array) {
        if (array == null) {
            System.out.println(array);
            return;
        }

        StringBuilder header = new StringBuilder(" ");
        StringBuilder body = new StringBuilder("{");

        for (int i = 0; i < array.length; i++) {
            String h = String.valueOf(i);
            String b = String.valueOf(array[i]);
            if (i > 0) {
                header.append("  ");
                body.append(", ");
            }
            for (int j = h.length(); j < Math.max(h.length(), b.length()); j++) {
                header.append(" ");
            }
            header.append(h);
            for (int j = b.length(); j < Math.max(h.length(), b.length()); j++) {
                body.append(" ");
            }
            body.append(b);
        }
        body.append("}");

        System.out.println(header);
        System.out.println(body);
    }

    public static int[] randomIntArray(int minArraySize, int maxArraySize, int minNum, int maxNum) {
        final int arraySize = minArraySize == maxArraySize ? minArraySize
                : NumberUtils.random(minArraySize, maxArraySize);
        int[] array = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = minNum == maxNum ? minNum : NumberUtils.random(minNum, maxNum);
        }

        return array;
    }
}
