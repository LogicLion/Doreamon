package com.example.lib;

import java.util.HashMap;
import java.util.Map;

public class TestClass {
    public static void main(String[] args) {
        int[] sums = {2, 7, 11, 15};
        Solution solution = new Solution();
        int[] ints = solution.twoSum(sums, 22);
        System.out.println("找到符合条件的下标：" + ints[0] + "," + ints[1]);

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
}


class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}