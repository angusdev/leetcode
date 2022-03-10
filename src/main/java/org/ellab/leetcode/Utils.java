package org.ellab.leetcode;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Utils {
    public static final String STR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    public static final String STR_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String STR_LETER = STR_LOWER + STR_UPPER;
    public static final String STR_NUMBER = "0123456789";
    public static final String STR_BINARY = "01";

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

    public static void printlnArray(boolean[] array) {
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

    public static void printlnArrayWithIndex(Object[] array) {
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

    public static void printlnArrayWithIndex(int[] array) {
        printlnArrayWithIndex(intArrayToIntegerArray(array));
    }

    public static void printlnArrayWithIndex(boolean[] array) {
        printlnArrayWithIndex(boolArrayToBooleanArray(array));
    }

    public static int[] integerArrayToIntArray(Integer[] src) {
        if (src == null) {
            return null;
        }

        int[] desc = new int[src.length];
        for (int i = 0; i < src.length; i++) {
            desc[i] = src[i];
        }

        return desc;
    }

    public static Integer[] intArrayToIntegerArray(int[] src) {
        if (src == null) {
            return null;
        }

        Integer[] desc = new Integer[src.length];
        for (int i = 0; i < src.length; i++) {
            desc[i] = src[i];
        }

        return desc;
    }

    public static Boolean[] boolArrayToBooleanArray(boolean[] src) {
        if (src == null) {
            return null;
        }

        Boolean[] desc = new Boolean[src.length];
        for (int i = 0; i < src.length; i++) {
            desc[i] = src[i];
        }

        return desc;
    }

    public static int[][] clone(int[][] src) {
        int[][] dest = new int[src.length][];

        for (int i = 0; i < src.length; i++) {
            dest[i] = new int[src[i].length];
            for (int j = 0; j < src[i].length; j++) {
                dest[i][j] = src[i][j];
            }
        }

        return dest;
    }

    public static char[][] clone(char[][] src) {
        char[][] dest = new char[src.length][];

        for (int i = 0; i < src.length; i++) {
            dest[i] = new char[src[i].length];
            for (int j = 0; j < src[i].length; j++) {
                dest[i][j] = src[i][j];
            }
        }

        return dest;
    }

    public static boolean equals(int[] a1, int[] a2) {
        if (a1 == null && a2 == null) {
            return true;
        }

        if (a1 == null || a2 == null) {
            return false;
        }

        if (a1.length != a2.length) {
            return false;
        }

        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                return false;
            }
        }

        return true;
    }

    public static boolean equals(boolean[] a1, boolean[] a2) {
        if (a1 == null && a2 == null) {
            return true;
        }

        if (a1 == null || a2 == null) {
            return false;
        }

        if (a1.length != a2.length) {
            return false;
        }

        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                return false;
            }
        }

        return true;
    }

    public static int diff(boolean[] a1, boolean[] a2) {
        if (a1 == null && a2 == null) {
            return -1;
        }

        if (a1 == null || a2 == null) {
            return 0;
        }

        if (a1.length != a2.length) {
            return 0;
        }

        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                return i;
            }
        }

        return -1;
    }

    public static boolean equals(int[][] a1, int[][] a2) {
        if (a1 == null && a2 == null) {
            return true;
        }

        if (a1 == null || a2 == null) {
            return false;
        }

        if (a1.length != a2.length) {
            return false;
        }

        for (int i = 0; i < a1.length; i++) {
            if (!equals(a1[i], a2[i])) {
                return false;
            }
        }

        return true;
    }

    public static boolean equals(char[][] a1, char[][] a2) {
        if (a1 == null && a2 == null) {
            return true;
        }

        if (a1 == null || a2 == null) {
            return false;
        }

        if (a1.length != a2.length) {
            return false;
        }

        for (int i = 0; i < a1.length; i++) {
            if (a1[i].length != a2[i].length) {
                return false;
            }
            for (int j = 0; j < a1[i].length; j++) {
                if (a1[i][j] != a2[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean equalsUnordered(int[] a1, int[] a2) {
        List<Integer> list1 = Arrays.asList(intArrayToIntegerArray(a1));
        List<Integer> list2 = Arrays.asList(intArrayToIntegerArray(a2));
        Collections.sort(list1);
        Collections.sort(list2);

        return list1.equals(list2);
    }

    public static int[] randomIntArray(int minArraySize, int maxArraySize, int minNum, int maxNum) {
        final int arraySize = minArraySize == maxArraySize ? minArraySize : Utils.random(minArraySize, maxArraySize);
        int[] array = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = minNum == maxNum ? minNum : Utils.random(minNum, maxNum);
        }

        return array;
    }

    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static int random(int max) {
        return random(0, max);
    }

    public static int randomSigned(int max) {
        return random(max) * (Math.random() >= 0.5 ? 1 : -1);
    }

    public static long random(long min, long max) {
        return (long) (Math.random() * (max - min + 1)) + min;
    }

    public static String randomString(int len, String string) {
        return randomString(len, len, string, true);
    }

    public static String randomString(int minLen, int maxLen, String string, boolean allowDuplicate) {
        if (!allowDuplicate && maxLen > string.length()) {
            throw new RuntimeException("!allowDuplicate && maxLen > string.length()");
        }

        int len = Utils.random(minLen, maxLen);
        char[] ch = new char[len];

        for (int i = 0; i < len; i++) {
            if (allowDuplicate) {
                ch[i] = string.charAt(Utils.random(0, string.length() - 1));
            }
            else {
                int pos = Utils.random(0, string.length() - 1);
                ch[i] = string.charAt(pos);
                string = string.substring(0, pos) + string.substring(pos + 1);
            }
        }

        return String.valueOf(ch);
    }

    public static String redirectSystemOutToString(Runnable runnable) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        runnable.run();
        System.out.flush();
        System.setOut(old);

        return baos.toString();
    }

    public static void sleep(long mills) {
        try {
            Thread.sleep(mills);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void sleepRandom(long min, long max) {
        try {
            Thread.sleep(random(min, max));
        }
        catch (InterruptedException ex) {
            ;
        }
    }

}
