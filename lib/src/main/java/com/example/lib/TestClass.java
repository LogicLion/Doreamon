package com.example.lib;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestClass {
    public static void main(String[] args) {
        Solution solution = new Solution();

//        int[] sums = {2, 7, 11, 15};
//        int[] ints = solution.twoSum(sums, 22);
//        System.out.println("找到符合条件的下标：" + ints[0] + "," + ints[1]);

        String s = "abcabcbb";
        int result = solution.lengthOfLongestSubstring(s);
        System.out.println("无重复字符的最长子串:" + result);

    }
}


class Solution {
    /**
     * 两数之和
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            //遍历数组 nums，i 为当前下标，每个值都判断map中是否存在 target-nums[i] 的 key 值
            if (map.containsKey(target - nums[i])) {
                //如果存在则找到了两个值，
                return new int[]{map.get(target - nums[i]), i};
            }
            //如果不存在则将当前的 (nums[i],i) 存入 map 中，继续遍历直到找到为止
            map.put(nums[i], i);
        }

        throw new IllegalArgumentException("No two sum solution");

    }


    /**
     * 无重复字符的最长子串
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * 解法:滑动窗口
     *
     * @return 最长子串 的长度
     */
    public int lengthOfLongestSubstring(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<>();
        //右指针,初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1;

        //记录字符串长度
        int ans = 0;

        //i即是左指针
        for (int i = 0; i < s.length(); i++) {
            if (i != 0) {
                //左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1));
            }
            //不断地移动右指针
            while (rk + 1 < s.length() && !occ.contains(s.charAt(rk + 1))) {
                occ.add(s.charAt(rk + 1));
                ++rk;
            }

            //比较字符串长度
            ans = Math.max(ans, rk - i + 1);
        }

        return ans;
    }


    /**
     *  最长回文子串
     *  给你一个字符串 s，找到 s 中最长的回文子串。
     *
     * 如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        int strLen = s.length();
        boolean[][] dp = new boolean[strLen][strLen];
        return s;
    }
}

