package com.example.lib;

import java.util.ArrayDeque;

/**
 * @author wzh
 * @date 2024/9/11
 */
class Solution8 {

    /**
     * 224. 基本计算器
     *
     * @param s
     * @return
     */
    public int calculate(String s) {
        /**
         * 当前已计算的结果
         */
        int res = 0;
        /**
         *数字可能有几位数字,用num暂存，每当遇到"）"和"+"和"-"，结算一次num和sign
         */
        int num = 0;
        /**
         *
         */
        int sign = 1;

        ArrayDeque<Integer> stack = new ArrayDeque();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                //数字
                num = num * 10 + c - '0';
            } else if (c == '+' || c == '-') {

                res += num * sign;
                num = 0;
                sign = c == '+' ? 1 : -1;
            } else if (c == '(') {
                //将res 和 sign 入栈
                stack.push(res);
                stack.push(sign);

                //res和sign重置为初始状态
                res = 0;
                sign = 1;
            } else if (c == ')') {
                res += num * sign;
                num = 0;

                //出栈“（”的符号
                res *= stack.pop();

                //合计“（”的结果
                res += stack.pop();
            }
        }
        //如果最后一个是数字，则未结算进去，结算进去
        res += num * sign;

        return res;
    }
}
