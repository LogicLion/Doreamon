package com.example.lib;

public class TestClass {
    public static void main(String[] args) {
//        Solution solution = new Solution();

//        int[] sums = {2, 7, 11, 15};
//        int[] ints = solution.twoSum(sums, 22);
//        System.out.println("找到符合条件的下标：" + ints[0] + "," + ints[1]);

//        String s = "abcabcbb";
//        int result = solution.lengthOfLongestSubstring(s);
//        System.out.println("无重复字符的最长子串:" + result);

//        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};
////        int[] heights = {1, 1};
//        int masAreas = solution.masAreas(heights);
//        System.out.println("容器可以储存的最大水量:" + masAreas);

//        String[] strs = {"flower", "flow", "flight"};
//        String[] strs = {"dog","racecar","car"};
//        String commonPrefix = solution.longestCommonPrefix(strs);
//        System.out.println("最长公共前缀:" + commonPrefix);

//        int[] nums = {-1,0,1,2,-1,-1,2,-4};
//        List<List<Integer>> threeSum = solution.threeSum(nums);
//        System.out.println(threeSum);

//        boolean palindrome = solution.isPalindrome(11);
//        System.out.println("是否回文数：" + palindrome);

//        boolean valid = solution.isValid("{}");
//        System.out.println("是否有效括号:" + valid);

//        int[] num1 = {1, 4, 5, 18};
//        int[] num2 = {1, 2, 3, 6, 8};
//        int[] num3 = {1, 5, 10, 12, 15};
//
//        ListNode list1 = arrayToList(num1);
//        ListNode list2 = arrayToList(num2);
//        ListNode list3 = arrayToList(num3);
//        printListNode(list1);
//        printListNode(list2);
//        printListNode(list3);
////        ListNode listNode = solution.mergeTwoLists1(list1, list2);
//
//        ListNode[] listNodes = {list1, list2, list3};
//        ListNode listNode = solution.mergeKLists(listNodes);
//        printListNode(listNode);

        Solution1 solution = new Solution1();

//        int[] num1 = {1, 4, 5, 18};
//        ListNode list1 = arrayToList(num1);
//        printListNode(list1);
//
//        ListNode swapPairs = solution.swapPairs(list1);
//        printListNode(swapPairs);

//        String pp = "bbsasdb";
//        String nn = "sas";
//        System.out.println("找出字符串中第一个匹配项的下标:"+solution.strStr(pp, nn));


//        int[] nums = {5, 7, 7, 8, 8, 8, 9, 9};
//        int[] range = solution.searchRange(nums, 9);
//        System.out.println("找到目标值在数组中的开始位置：" + range[0] + "," + range[1]);


//        int[] nums = {1, 3, 5, 6, 8};
//        int insert = solution.searchInsert(nums, 5);
//        System.out.println("找到目标值在数组中的开始位置：" + insert);


//        int[] nums = {13, 5, 1, 8, 21, 2};
//        int k = 3;
//        int tastiness = solution.maximumTastiness(nums, 3);
//        System.out.println("礼盒的最大甜蜜度:" + tastiness);




    }

    /**
     * 数组转链表，返回链表头节点
     *
     * @param nums
     * @return
     */
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


