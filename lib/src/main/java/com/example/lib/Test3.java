package com.example.lib;

/**
 * @author wzh
 * @date 2023/7/3
 */
class Test3 {
    public static void main(String[] args) {
        Solution3 solution = new Solution3();
//        int[][] intervals = {{1, 3}, {6, 9}};
//        int[] newInterval = {2, 5};
//
//        int[][] insert = solution.insert(intervals, newInterval);
//        printTwoDimensionalArray(insert);


//        int[][] test = {{7, 2, 1}, {6, 4, 2}, {6, 5, 3}, {3, 2, 1}};
//        solution.matrixSum(test);


//        int[][] test1 = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}, {61, 62, 63, 66},
//                {67, 68, 71, 75}, {76, 77, 80, 81}, {82, 83, 85, 86}, {87, 88, 90, 91}, {92, 93, 95, 98}};
//        solution.searchMatrix(test1, 65);

//        int[] test = {4, 5, 6, 7, 0, 1, 2};
//        int[] test = {1,3};
//        int[] test = {3, 1};

//        int search = solution.search(test, 1);
//
//        System.out.println("搜索到下标：" + search);

//        int i = solution.alternateDigitSum(4321);
//        System.out.println("结果：" + i);

//        int[] a = {1, 1, 2, 3, 3};
//
//        ListNode listNode = arrayToList(a);
//        ListNode.printListNode(listNode);
//        ListNode listNode1 = solution.deleteDuplicates1(listNode);
//
//        ListNode.printListNode(listNode1);

        String s1 = "aabcc";
        String s2 = "dbbca";
        String s3 = "aadbbcbcac";

        solution.isInterleave(s1, s2, s3);

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
}
