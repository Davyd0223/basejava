package com.unise.webapp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class JavaStreams {
    static int[] array = {9, 8};
    //int result = minValue(array);

    private static int minValue(int[] values) {
        HashSet<Integer> set = new HashSet<>();
        int n = 0;
        for (int i : values) {
            set.add(i);
        }
        for (int result : set) {
            n = (10 * n) + result;
        }
        return n;
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int number = integers.stream().mapToInt(Integer::intValue).sum();
        if (number % 2 == 0) {
            integers.removeIf(i -> i % 2 == 0);
        } else {
            integers.removeIf(i -> i % 2 == 1);
        }
        return integers;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 13; i++) {
            list.add(i);
        }

        System.out.println(oddOrEven(list));

        System.out.println(minValue(array));
    }
}
