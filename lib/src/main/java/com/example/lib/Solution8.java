package com.example.lib;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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


    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[0];
    }


    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String s1 = new String(chars);
            List<String> list = map.getOrDefault(s1, new ArrayList<>());
            list.add(s);
            map.put(s1, list);
        }

        return new ArrayList<List<String>>(map.values());
    }

    public int longestConsecutive(int[] nums) {

        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int currCount = 0;
        int longestCount = 0;

        for (int num : set) {
            if (!set.contains(num - 1)) {
                currCount = 1;
                while (set.contains(num + currCount)) {
                    currCount++;
                }

                if (currCount > longestCount) {
                    longestCount = currCount;
                }
            }
        }

        return longestCount;
    }


    public void moveZeroes(int[] nums) {
        int n = nums.length;
        int fast = 0;
        int slow = 0;
        while (fast < n) {
            if (nums[fast] != 0) {
                int temp = nums[fast];
                nums[fast] = nums[slow];
                nums[slow] = temp;
                slow++;
            }

            fast++;
        }
    }

    public int maxArea(int[] height) {
        int n = height.length;
        int l = 0;
        int r = n - 1;

        int maxArea = 0;
        while (l < r) {
            if (height[l] > height[r]) {
                maxArea = Math.max(height[r] * (r - l), maxArea);
                r--;
            } else {
                maxArea = Math.max(height[l] * (r - l), maxArea);
                l++;
            }
        }

        return maxArea;
    }

    public int lengthOfLongestSubstring(String s) {
        Set set = new HashSet();

        int start = 0, end = 0, maxLen = 0;
        while (end < s.length()) {
            if (set.contains(s.charAt(end))) {
                set.remove(s.charAt(start));
                start++;
            } else {
                set.add(s.charAt(end));
                end++;
                maxLen = Math.max(maxLen, end - start);
            }
        }

        return maxLen;
    }
}
