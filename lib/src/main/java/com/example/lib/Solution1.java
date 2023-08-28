package com.example.lib;

import java.util.Arrays;

/**
 * @author wzh
 * @date 2023/5/26
 */
public class Solution1 {

    /**
     * 24. 两两交换链表中的节点
     * 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
     *
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;
        ListNode tempNode = dummyHead;
        while (tempNode.next != null && tempNode.next.next != null) {
            ListNode node1 = tempNode.next;
            ListNode node2 = tempNode.next.next;

            tempNode.next = node2;
            node1.next = node2.next;
            node2.next = node1;

            tempNode = tempNode.next.next;
        }


        return dummyHead.next;
    }


    /**
     * 26. 删除有序数组中的重复项
     *
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {

        int leftIndex = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[leftIndex] != nums[i]) {
                leftIndex++;
                nums[leftIndex] = nums[i];
            }
        }

        return leftIndex + 1;
    }

    /**
     * 27. 移除元素
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
     *
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] != val) {
                nums[left] = nums[right];
                left++;
            }
        }

        return left;
    }


    /**
     * 28. 找出字符串中第一个匹配项的下标
     * <p>
     * 给你两个字符串haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）。
     * 如果needle 不是 haystack 的一部分，则返回 -1 。
     *
     * @param haystack
     * @param needle
     * @return
     */
    public int strStr(String haystack, String needle) {

        int n = haystack.length();
        int m = needle.length();
        char[] hh = haystack.toCharArray();
        char[] pp = needle.toCharArray();

        for (int i = 0; i <= n - m; i++) {

            int a = i;
            int b = 0;
            for (int j = 0; j < m; j++) {
                if (hh[a] == pp[b]) {
                    a++;
                    b++;
                }
            }

            if (b == m) {
                return i;
            }
        }

        return -1;
    }


    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     * <p>
     * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
     * <p>
     * 如果数组中不存在目标值 target，返回[-1, -1]。
     * <p>
     * 你必须设计并实现时间复杂度为O(log n)的算法解决此问题。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {

        //先找>=target的第一个
        int leftIdx = binarySearch(nums, target);
        //再找>target的第一个,因为用target + 1的值可能已经超出了右边界
        int rightIdx = binarySearch(nums, target + 1);
        //考虑右边界情况
        rightIdx = (rightIdx == nums.length - 1 && nums[rightIdx] == target) ? rightIdx : rightIdx - 1;

        //判断找到的值是否符合要求
        if (leftIdx == nums.length || nums[leftIdx] != target) {
            return new int[]{-1, -1};
        }


        return new int[]{leftIdx, rightIdx};
    }


    public int binarySearch(int[] nums, int target) {

        int left = 0, right = nums.length - 1;
        while (left < right) {

            int mid = (left + right) / 2;

            if (nums[mid] >= target) {
                //大于等于的情形下都是移动右边
                right = mid;
            } else {
                //小于的情形下移动左边,因为一定是小于所以+1
                left = mid + 1;
            }
        }
        return left;
    }


    /**
     * 35. 搜索插入位置
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * <p>
     * 请必须使用时间复杂度为 O(log n) 的算法。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/search-insert-position
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public int searchInsert(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        int mid = nums.length - 1;
        while (l <= r) {

            if (nums[mid] < target) {
                l = mid + 1;
            } else if (nums[mid] > target) {
                r = mid - 1;
            } else {
                //找到了，返回
                return mid;
            }
            mid = (l + r) / 2;
        }

        //没找到,看最终mid所在的值与target比较一下，确定要插入的位置
        if (nums[mid] > target) {
            return mid;
        } else {
            return mid + 1;
        }
    }


    /**
     * 48. 旋转图像
     * 给定一个 n×n 的二维矩阵matrix 表示一个图像。请你将图像顺时针旋转 90 度。
     * <p>
     * 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/rotate-image
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < (n + 1) / 2; j++) {

                //以5*5其中一个点为例：
                //{0,1} ->{3,0}->{4,3}->{1,4}
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - j - 1][i];
                matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
                matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
                matrix[j][n - i - 1] = temp;
            }
        }

    }


    /**
     * 2517. 礼盒的最大甜蜜度
     * 给你一个正整数数组 price ，其中 price[i] 表示第 i 类糖果的价格，另给你一个正整数 k 。
     * <p>
     * 商店组合 k 类 不同 糖果打包成礼盒出售。礼盒的 甜蜜度 是礼盒中任意两种糖果 价格 绝对差的最小值。
     * <p>
     * 返回礼盒的 最大 甜蜜度。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/maximum-tastiness-of-candy-basket
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     *
     * 思路
     * 题意 -> 给定一个数组，选择任意 K 个数组成一个新集合，一个集合的有效值为 集合 所有任意两个数之差的绝对值 中取最小值，返回最大有效值集合的值。
     *
     * 「任意两种糖果价格绝对差的最小值」等价于「排序后，任意两种相邻糖果价格绝对差的最小值」。
     * 如果题目中有「最大化最小值」或者「最小化最大值」，一般都是二分答案，请记住这个套路。
     * 由于数组中任意元素都是大于0的，因此最小的有效值肯定是 0， 而最大的有效值肯定是排序之后的数组中 最后一个元素 - 第一个元素 之差，假设为 x。 因此答案肯定在 0-x 之间。
     *
     * 题意 -> 在 0-x 中找一个最大值 -> 很显然二分查找最合适不过了，因为 0-x 是有序的，完全满足二分查找条件。
     * 还有一个问题，如果判断 0-x 中的 mid 是否满足题意有效：
     *
     * mid的含义为 数组中集合有效值为mid的个数 >= K，因此只需要判断数组中是否存在 K 个以上集合有效值大于等于 mid。
     * 不需要枚举所有集合的有效元素，由于数组已经升序处理，只需要判断具体有多少集合是满足要求即可，具体判断方法请看代码。
     *
     * 作者：xun-ge-v
     * 链接：https://leetcode.cn/problems/maximum-tastiness-of-candy-basket/solution/er-fen-cha-zhao-zhu-shi-chao-ji-xiang-xi-pxty/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     * @param price
     * @param k
     * @return
     */
    public int maximumTastiness(int[] price, int k) {

        Arrays.sort(price);
        int left = 0;
        int right = price[price.length - 1] - price[0];
        while (left < right) {
            int mid = (left + right + 1) / 2;

            if (check(price, k, mid)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    public boolean check(int[] price, int k, int tastiness) {
        int cnt = 0;
        int prev = Integer.MIN_VALUE / 2;
        for (int p : price) {
            if (p - prev >= tastiness) {
                cnt++;
                prev = p;
            }
        }
        return cnt >= k;
    }


}
