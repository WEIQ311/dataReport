package com.report.util;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 自定义栈，min函数得到当前最小值
 */
public class TestMinStack {
    @Test
    public void test() {
        MinStack ms = new MinStack();
        ms.push(5);
        System.out.println(ms.min());
        ms.push(6);
        ms.push(2);
        ms.push(1);
        System.out.println(ms.min());
        ms.pop();
        System.out.println(ms.min());
        ms.pop();
        System.out.println(ms.min());
    }


    @Test
    public void sortTest() {
        Integer[] number = {1, 8, 9, 34, 90, 69, 60, 109, 22};
        List<Integer> numbers = new ArrayList<>();
        for (Integer s : number) {
            numbers.add(s);
        }
        numbers.sort((i,j)->j-i);
        System.out.println(numbers);
        numbers = numbers.stream().sorted(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        }).collect(Collectors.toList());
        System.out.println(numbers);
        System.out.println(Arrays.deepToString(number));

        Arrays.sort(number,Collections.reverseOrder());
        System.out.println(Arrays.deepToString(number));

        System.out.println("------------");
        Collections.sort(numbers, (Integer a, Integer b) -> {
            return b - a;
        });
        System.out.println(numbers);

        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, (String a, String b) -> {
            return b.compareTo(a);
        });
        System.out.println(names);
        Integer[] results = bubble(number);
        System.out.println(Arrays.deepToString(results));
    }

    /**
     * 排序算法
     *
     * @param a
     * @return Integer[]
     */
    public static Integer[] bubble(Integer[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = 0; j < a.length - i; j++) {
                if (a[j] <= a[j + 1]) {
                    int temp = a[j + 1];
                    a[j + 1] = a[j];
                    a[j] = temp;
                }
            }
        }
        return a;
    }
}

class MinStack {
    private Stack<Integer> minStack = new Stack<Integer>();
    private Stack<Integer> stack = new Stack<Integer>();

    public int pop() {
        minStack.pop();
        return stack.pop();
    }

    public void push(int num) {
        if (minStack.size() <= 0) {
            minStack.push(num);
            return;
        }
        Integer min = minStack.lastElement();
        if (num < min) {
            minStack.push(num);
        } else {
            minStack.push(min);
        }
        stack.push(num);
    }

    public int min() {
        if (minStack.size() <= 0) {
            return -1;
        }
        return minStack.lastElement();
    }

}