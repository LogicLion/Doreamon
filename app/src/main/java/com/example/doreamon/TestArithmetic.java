package com.example.doreamon;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wzh
 * @date 2023/5/6
 */
class TestArithmetic {


    public static void main(String[] args) {
        int[] sums = {2, 7, 11, 15};
        Solution solution = new Solution();
        int[] ints = solution.twoSum(sums, 9);
        System.out.println(ints);
    }


}

class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> hashtable = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; ++i) {
            if (hashtable.containsKey(target - nums[i])) {
                return new int[]{hashtable.get(target - nums[i]), i};
            }
            hashtable.put(nums[i], i);
        }
        return new int[0];
    }
}
