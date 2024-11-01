package com.example.lib;

import java.util.Stack;

/**
 * @author wzh
 * @date 2024/3/4
 */
class MyQueue {

    //输入栈
    Stack<Integer> stack1;

    //输出栈
    Stack<Integer> stack2;



    public MyQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void push(int x) {
        stack1.push(x);
    }

    public int pop() {

        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    public int peek() {

        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }

        return stack2.peek();
    }

    public boolean empty() {

        return stack2.isEmpty()&&stack1.isEmpty();
    }

}
