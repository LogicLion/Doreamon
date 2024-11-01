package com.example.lib;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author wzh
 * @date 2024/6/6
 */
class MinStack {

    Deque<Integer> xStack;
    Deque<Integer> minStack;

    public MinStack() {

        xStack = new LinkedList<>();
        minStack = new LinkedList<>();

        minStack.push(Integer.MAX_VALUE);
    }

    public void push(int val) {
        xStack.push(val);
        Integer lastMin = minStack.peek();
        minStack.push(Math.min(lastMin, val));
    }

    public void pop() {
        xStack.pop();
        minStack.pop();
    }

    public int top() {
        return xStack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
