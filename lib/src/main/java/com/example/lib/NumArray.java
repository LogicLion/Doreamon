package com.example.lib;

/**
 * @author wzh
 * @date 2024/3/18
 */
class NumArray {

    private final int[] nums;

    private int[] sums;

    public NumArray(int[] nums) {
        this.nums = nums;
        sums = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            sums[i] += nums[i];
            if (i > 0) {
                sums[i] += sums[i - 1];
            }
        }
    }

    public int sumRange(int left, int right) {
        return sums[right] - sums[left] + nums[left];
    }
}
