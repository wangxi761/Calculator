package com.example.calculator;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Expression {
    public static Map<Character, Integer> LEVEL_MAP = new HashMap<>();

    static {
        LEVEL_MAP.put('+', 1);
        LEVEL_MAP.put('-', 1);
        LEVEL_MAP.put('*', 2);
        LEVEL_MAP.put('/', 2);

    }


    public static String calculate(String expression) {
        Log.i("calculator: ", expression);
        Stack<String> num = new Stack<>();
        Stack<Character> ops = new Stack<>();
        for (int i = 0; i < expression.length();i++) {
            int start = i;
            while (start < expression.length() && Character.isDigit(expression.charAt(start))) {
                start++;
            }
            if (start == i) {
                pushOperator(num, ops, expression.charAt(i));
            } else {
                num.push(expression.substring(i,start));
                i=start-1;
            }
        }

        while (!ops.isEmpty()) {
            String second = num.pop();
            String first = num.pop();
            num.push(calculate(first, second, ops.pop()));
        }
        return num.pop();
    }

    private static void pushOperator(Stack<String> num, Stack<Character> ops, Character op) {
        if (ops.isEmpty() || LEVEL_MAP.get(op) > LEVEL_MAP.get(ops.peek())) {
            ops.push(op);
        } else {
            String second = num.pop();
            String first = num.pop();
            num.push(calculate(first, second, ops.pop()));
            pushOperator(num, ops, op);
        }
    }

    public static String calculate(String a, String b, char op) {
        switch (op) {
            case '+':
                return String.valueOf(Long.parseLong(a) + Long.parseLong(b));
            case '-':
                return String.valueOf(Long.parseLong(a) - Long.parseLong(b));
            case '*':
                return String.valueOf(Long.parseLong(a) * Long.parseLong(b));
            case '/':
                return String.valueOf(Double.parseDouble(a) / Double.parseDouble(b));

        }
        return null;
    }

}
