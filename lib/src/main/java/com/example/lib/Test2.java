package com.example.lib;

/**
 * @author wzh
 * @date 2023/6/2
 */
class Test2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();

        //["aba","bcb","ece","aa","e"]
        //[[0,2],[1,4],[1,1]]

//        String[] vowStr = {"aba", "bcb", "ece", "aa", "e"};
//        int[][] check = {{0, 2}, {1, 4}, {1, 1}};
//        int[] ints = solution.vowelStrings(vowStr, check);
//
//        System.out.println("统计范围内的元音字符串数:" + arrayToString(ints));


//        int[] nums = {1, 2, 3};
//        List<List<Integer>> lists = solution.permute(nums);
//        System.out.println(lists);


//        int[] nums = {1, 2, 0, 2, 1, 0, 0, 1, 0};
//        int[] operations = solution.applyOperations(nums);
//        System.out.println(arrayToString(operations));


        int[] head = {1, 2, -3, 3, 1};
        ListNode listNode = arrayToList(head);

        printListNode(listNode);
        ListNode listNode1 = solution.removeZeroSumSublists(listNode);
        printListNode(listNode1);


    }

    public static String arrayToString(int[] array) {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                sb.append(array[i]);
            } else {
                sb.append(",");
                sb.append(array[i]);
            }
        }
        sb.append("]");
        return sb.toString();
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
     * 打印链表
     *
     * @param head
     * @return
     */
    public static void printListNode(ListNode head) {
        StringBuffer sb = new StringBuffer();

        if (head == null) {
            return;
        } else {
            sb.append("{");
            sb.append(head.val);
        }

        ListNode current = head;
        while (current.next != null) {
            sb.append(",");
            current = current.next;
            sb.append(current.val);
        }

        sb.append("}");
        System.out.println(sb);
    }
}
