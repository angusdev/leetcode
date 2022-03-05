package org.ellab.leetcode;

public class NumberUtils {
    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static int random(int max) {
        return random(0, max);
    }

    public static int randomSigned(int max) {
        return (int) (Math.random() * (max + 1)) * (Math.random() >= 0.5 ? 1 : -1);
    }
}