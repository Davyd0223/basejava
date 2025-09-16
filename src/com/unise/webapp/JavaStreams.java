package com.unise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaStreams {
    static int[] array = {1, 2, 3, 3, 2, 3};
    //int result = minValue(array);

    private static int minValue(int[] values) {
        return (int) Arrays.stream(values).asLongStream().distinct().sorted()
                .reduce(0, (result, i) -> 10 * result + i);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(Integer::intValue).sum();
        return integers.stream().filter(i -> (sum % 2 == 0) == (i % 2 != 0)).toList();
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 14; i++) {
            list.add(i);
        }

        System.out.println(oddOrEven(list));

        System.out.println(minValue(array));
    }
}
