package org.ellab.leetcode;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Utils {
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
