package org.ellab.leetcode;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

/**
 * 881 - Boats to Save People.
 * 
 * You are given an array people where people[i] is the weight of the ith person, and an infinite number of boats where
 * each boat can carry a maximum weight of limit. Each boat carries at most two people at the same time, provided the
 * sum of the weight of those people is at most limit.
 * 
 * Return the minimum number of boats to carry every given person.
 * 
 * @see <a href="https://leetcode.com/problems/boats-to-save-people/">leetcode.com</a>
 */
public class J00881 {
    class Solution {
        public int numRescueBoats(int[] people, int limit) {
            Arrays.sort(people);
            int left = 0;
            int right = people.length - 1;

            int result = 0;
            while (left <= right) {
                if (people[left] + people[right] <= limit) {
                    left++;
                }
                result++;
                right--;
            }

            return result;
        }
    }

    class Solution2 extends Solution {
        @Override
        public int numRescueBoats(int[] people, int limit) {
            TreeMap<Integer, Integer> greaterHalf = new TreeMap<>();
            TreeMap<Integer, Integer> smallerHalf = new TreeMap<>();
            int greaterHalfCount = 0, smallerHalfCount = 0, halfCount = 0;
            float halfLimit = limit / 2.0f;

            for (int p : people) {
                if (p > halfLimit) {
                    greaterHalfCount++;
                    addToMap(greaterHalf, p);
                }
                else if (p < halfLimit) {
                    smallerHalfCount++;
                    addToMap(smallerHalf, p);
                }
                else {
                    halfCount++;
                }
            }

            int result = 0;
            while (!greaterHalf.isEmpty()) {
                if (smallerHalf.isEmpty()) {
                    return result + greaterHalfCount + (int) Math.ceil(halfCount / 2.0f);
                }

                int g = greaterHalf.lastKey();
                removeOneFromMap(greaterHalf, g);
                greaterHalfCount--;
                result++;

                int s = smallerHalf.firstKey();
                if (s + g <= limit) {
                    removeOneFromMap(smallerHalf, s);
                    smallerHalfCount--;
                }
            }

            return result + (int) Math.ceil((smallerHalfCount + halfCount) / 2.0f);
        }

        private void addToMap(TreeMap<Integer, Integer> map, int n) {
            if (map.containsKey(n)) {
                map.put(n, map.get(n) + 1);
            }
            else {
                map.put(n, 1);
            }
        }

        private void removeOneFromMap(TreeMap<Integer, Integer> map, int n) {
            int value = map.get(n);
            if (value <= 1) {
                map.remove(n);
            }
            else {
                map.put(n, value - 1);
            }
        }
    }

    class BruteForce extends Solution {
        @Override
        public int numRescueBoats(int[] people, int limit) {
            TreeMap<Integer, Integer> map = new TreeMap<>();
            for (int p : people) {
                if (map.containsKey(p)) {
                    map.put(p, map.get(p) + 1);
                }
                else {
                    map.put(p, 1);
                }
            }

            int result = 0;
            while (!map.isEmpty()) {
                int k = map.lastKey();
                removeOneFromMap(map, k);

                result++;

                for (int i = limit - k; i >= 0; i--) {
                    if (map.containsKey(i)) {
                        removeOneFromMap(map, i);
                        break;
                    }
                }
            }

            return result;
        }

        private void removeOneFromMap(TreeMap<Integer, Integer> map, int n) {
            int value = map.get(n);
            if (value <= 1) {
                map.remove(n);
            }
            else {
                map.put(n, value - 1);
            }
        }
    }

    private void doTest(Solution solution, int[] people, int limit, int expected) {
        Integer actual = null;
        boolean pass = false;
        Exception unexpectedEx = null;
        try {
            actual = solution.numRescueBoats(people, limit);
            pass = expected == actual;
        }
        catch (Exception ex) {
            unexpectedEx = ex;
        }

        if (!pass) {
            System.out.println(solution.getClass().getName());
            System.out.print("Fail     : ");
            Utils.printlnArray(people);
            System.out.println("           " + limit);
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

        doTest(solution, new int[] { 1, 2 }, 3, 1);
        doTest(solution, new int[] { 3, 2, 2, 1 }, 3, 3);
    }

    @Test
    public void testMyCases() {
        final Solution solution = new Solution();

        doTest(solution, new int[] { 4, 4, 4, 4 }, 4, 4);
        doTest(solution, new int[] { 4, 4, 4, 4, 1 }, 4, 5);
        doTest(solution, new int[] { 3, 7, 2, 8, 4, 6, 9, 1, 6, 6 }, 10, 6);
    }

    private void testRandomCases(Solution solution, int tries, int maxArraySize, int maxNum, int maxNum2) {
        for (int i = 0; i < tries; i++) {
            int[] people = Utils.randomIntArray(1, maxArraySize, 0, maxNum);
            int limit = Utils.random(0, maxNum2);
            doTest(solution, people, limit, new BruteForce().numRescueBoats(people, limit));
        }
    }

    @Test
    public void testRandomCases() {
        testRandomCases(new Solution(), 10000, 100, 10000, 10000);
    }

    private void testPerformance(Solution solution, int tries, int[] people, int limit) {
        long start = System.nanoTime();
        for (int i = 0; i < tries; i++) {
            solution.numRescueBoats(people, limit);
        }
        long end = System.nanoTime();
        System.out.println(solution.getClass().getName() + " : " + (end - start) / 1000000000.0 + " sec");
    }

    @Test
    public void testPerformance() {
        int tries = 10000;
        int arraySize = 100;
        int maxNum = 10000;
        int[] people = Utils.randomIntArray(arraySize, arraySize, 0, maxNum);
        int limit = 10000;
        testPerformance(new Solution(), tries, people, limit);
        testPerformance(new Solution2(), tries, people, limit);
        testPerformance(new BruteForce(), Math.max(1, tries), people, limit);
    }
}
