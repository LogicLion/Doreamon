package com.example.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wzh
 * @date 2023/5/19
 */
public class Solution {
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
     * 最长回文子串
     * 给你一个字符串 s，找到 s 中最长的回文子串。
     * <p>
     * 如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        int strLen = s.length();
        boolean[][] dp = new boolean[strLen][strLen];
        return s;
    }


    /**
     * 11.盛最多水的容器
     *
     * @param height
     * @return
     */
    public int masAreas(int[] height) {
        int l = 0;
        int r = height.length - 1;
        int ans = 0;

        while (l < r) {
            ans = Math.max(ans, Math.min(height[l], height[r]) * (r - l));
            if (height[l] <= height[r]) {
                l++;
            } else {
                r--;
            }
        }

        return ans;
    }


    /**
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     * 如果不存在公共前缀，返回空字符串 ""。
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {

        if (strs == null || strs.length == 0) {
            return "";
        }

        String prefix = strs[0];

        for (int i = 1; i < strs.length; i++) {
            prefix = longestCommonPrefix(prefix, strs[i]);

            if (prefix.length() == 0) {
                break;
            }
        }

        return prefix;
    }

    public String longestCommonPrefix(String strs1, String strs2) {

        int minLength = Math.min(strs1.length(), strs2.length());
        int ans = 0;
        for (int i = 0; i < minLength; i++) {
            if (strs1.charAt(i) == strs2.charAt(i)) {
                ans++;
            } else {
                break;
            }
        }
        return strs1.substring(0, ans);
    }


    /**
     * 15.三数之和
     * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，
     * 同时还满足 nums[i] + nums[j] + nums[k] == 0 。
     * 请你返回所有和为 0 且下标不重复的三元组。
     * 解法：排序+双指针
     */
    public List<List<Integer>> threeSum(int[] nums) {

        //排序
        Arrays.sort(nums);

        List<List<Integer>> ans = new ArrayList<>();
        int numsLength = nums.length;

        //枚举a
        for (int first = 0; first < numsLength; first++) {

            // 需要和上一次枚举的数不相同
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }

            int third = numsLength - 1;

            //以当前的a作为参考值进行比较
            int target = -nums[first];

            //枚举b
            for (int second = first + 1; second < numsLength; second++) {
                //b需要和上一次枚举的数不相同
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }

                //需保证b在c的左边，逐步移动third
                while (second < third && nums[second] + nums[third] > target) {
                    --third;
                }


                //当b、c指针重合，已全部遍历一遍，后续也不再会有符合条件的了，可以退出循环
                if (second == third) {
                    break;
                }

                if (nums[second] + nums[third] == target) {
                    ArrayList<Integer> integers = new ArrayList<>();
                    integers.add(nums[first]);
                    integers.add(nums[second]);
                    integers.add(nums[third]);
                    ans.add(integers);
                }
            }
        }

        return ans;
    }


    /**
     * 9. 回文数
     * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
     * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     * 反转一半，这样int不用考虑溢出的情况（但其实溢出的也不会是回文数）
     *
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        // 特殊情况：
        // 如上所述，当 x < 0 时，x 不是回文数。
        // 同样地，如果数字的最后一位是 0，为了使该数字为回文，
        // 则其第一位数字也应该是 0
        // 只有 0 满足这一属性
        if (x < 0 || x % 10 == 0 && x != 0) {
            return false;
        }

        int reversedNumber = 0;
        while (x > reversedNumber) {
            reversedNumber = reversedNumber * 10 + x % 10;
            x = x / 10;
        }

        // 当数字长度为奇数时，我们可以通过 revertedNumber/10 去除处于中位的数字。
        // 例如，当输入为 12321 时，在 while 循环的末尾我们可以得到 x = 12，revertedNumber = 123，
        // 由于处于中位的数字不影响回文（它总是与自己相等），所以我们可以简单地将其去除。
        return x == reversedNumber || x == reversedNumber / 10;
    }


    /**
     * 20. 有效的括号
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']'的字符串 s ，判断字符串是否有效。
     * <p>
     * 有效字符串需满足：
     * <p>
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 每个右括号都有一个对应的相同类型的左括号。
     */
    public boolean isValid(String s) {

        if (s.length() % 2 == 1) {
            return false;
        }

        Deque<Character> stack = new LinkedList<Character>();
        Map<Character, Character> pairs = new HashMap<Character, Character>() {{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (pairs.containsKey(c)) {
                //结束括号，进行匹配
                if (stack.isEmpty() || stack.peek() != pairs.get(c)) {
                    //匹配不上
                    return false;
                }
                //匹配上了
                stack.pop();
            } else {
                //起始括号，入栈
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }


    /**
     * 21. 合并两个有序链表
     * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
     * 1.递归法
     *
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        } else if (list2 == null) {
            return list1;
        } else if (list1.val <= list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }
    }

    /**
     * 迭代法
     *
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists1(ListNode list1, ListNode list2) {

        //虚拟头节点
        ListNode preHead = new ListNode();

        //辅助节点，遍历和牵线，在每次比较中谁小它指向谁
        ListNode prev = preHead;

        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                //指向小的那边，表示这个节点已经比较过了
                prev.next = list1;
                //list1移除一个节点
                list1 = list1.next;
            } else {
                prev.next = list2;
                list2 = list2.next;
            }

            //步进一个节点，将在下一个循环中决定它的指向
            prev = prev.next;
        }

        //比较完了之后将剩下的不为空的链表接到prev来
        prev.next = list1 == null ? list2 : list1;

        return preHead.next;
    }


    public ListNode mergeKLists(ListNode[] lists) {
        ListNode merge = null;
        for (ListNode list : lists) {
            merge = mergeTwoLists1(merge, list);
        }

        return merge;
    }


    /**
     * 16. 最接近的三数之和
     * 给你一个长度为 n 的整数数组nums和 一个目标值target。请你从 nums 中选出三个整数，使它们的和与target最接近。
     * <p>
     * 返回这三个数的和。
     * <p>
     * 假定每组输入只存在恰好一个解。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/3sum-closest
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param nums
     * @param target
     * @return
     */
    public int threeSumClosest(int[] nums, int target) {
        int best = 0;
        Arrays.sort(nums);

        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int j = i + 1;
            int k = n - 1;

            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum == target) {
                    return sum;
                }
                if (Math.abs(sum - target) < Math.abs(best - target)) {
                    best = sum;
                }

                if (sum > target) {
                    k = k - 1;
                } else {
                    j = j + 1;
                }
            }

        }

        return best;
    }


    /**
     * 73. 矩阵置零
     *
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[] row = new boolean[m];
        boolean[] col = new boolean[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = true;
                    col[j] = true;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (row[i] || col[j]) {
                    matrix[i][j] = 0;
                }
            }
        }

    }





}
