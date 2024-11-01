package com.example.lib;

/**
 * @author wzh
 * @date 2023/7/3
 */
class Test3 {
    public static void main(String[] args) {

        Solution7 solution = new Solution7();


        int[] nums = {3, 1};
        solution.search(nums, 1);

    }


    /**
     * 打印二维数组
     *
     * @param intervals
     */
    public static void printTwoDimensionalArray(int[][] intervals) {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < intervals.length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append("[");
            sb.append(intervals[i][0]);
            sb.append(",");
            sb.append(intervals[i][1]);
            sb.append("]");
        }

        sb.append("]");

        System.out.println(sb);

    }


    private static ListNode arrayToList(int[] nums) {

        //创造虚拟头节点
        ListNode dummy = new ListNode();

        //当前节点
        ListNode current = dummy;
        for (int num : nums) {
            ListNode nextNode = new ListNode(num);
            current.next = nextNode;
            current = nextNode;
        }

        //返回真实的头节点
        return dummy.next;
    }


    /**
     * 300. 最长递增子序列
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {

        return 0;
    }


}
