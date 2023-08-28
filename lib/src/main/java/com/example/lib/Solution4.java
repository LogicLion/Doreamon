package com.example.lib;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author wzh
 * @date 2023/8/2
 */
class Solution4 {

    /**
     * 822. 翻转卡片游戏
     * 在桌子上有 N 张卡片，每张卡片的正面和背面都写着一个正数（正面与背面上的数有可能不一样）。
     * <p>
     * 我们可以先翻转任意张卡片，然后选择其中一张卡片。
     * <p>
     * 如果选中的那张卡片背面的数字 X 与任意一张卡片的正面的数字都不同，那么这个数字是我们想要的数字。
     * <p>
     * 哪个数是这些想要的数字中最小的数（找到这些数中的最小值）呢？如果没有一个数字符合要求的，输出 0。
     * <p>
     * 其中, fronts[i]和backs[i]分别代表第i张卡片的正面和背面的数字。
     * <p>
     * 如果我们通过翻转卡片来交换正面与背面上的数，那么当初在正面的数就变成背面的数，背面的数就变成正面的数。
     * <p>
     * 技巧题
     * <p>
     * 重点在于：
     * <p>
     * 如果某个位置正反面数字一样，那么相同的数字在其他位置是肯定行不通的，
     * <p>
     * 相反，如果某个数字在任何位置都不会存在正反面一样的情况，那么我们一定可以通过翻面来避开相同的情况
     * <p>
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/card-flipping-game
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param fronts
     * @param backs
     * @return
     */
    public int flipgame(int[] fronts, int[] backs) {

        int res = Integer.MAX_VALUE;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < fronts.length; i++) {

            if (fronts[i] == backs[i]) {
                set.add(fronts[i]);
            }
        }

        for (int front : fronts) {
            if (front < res && !set.contains(front)) {
                res = front;
            }
        }

        for (int back : backs) {
            if (back < res && !set.contains(back)) {
                res = back;
            }
        }


        return res == Integer.MAX_VALUE ? 0 : res;
    }


    /**
     * 75. 颜色分类
     * 给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
     * 我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
     * 必须在不使用库内置的 sort 函数的情况下解决这个问题。
     *
     * @param nums
     */
    public void sortColors(int[] nums) {
        int n = nums.length;
        int l = 0;
        int r = n - 1;
        int temp;

        for (int i = 0; i <= r; i++) {
            while (i < r && nums[i] == 2) {
                //一直换到nums[i] != 2为止
                temp = nums[r];
                nums[r] = nums[i];
                nums[i] = temp;
                r--;
            }

            if (nums[i] == 0) {
                temp = nums[l];
                nums[l] = nums[i];
                nums[i] = temp;
                l++;
            }
        }

    }


    /**
     * 91. 解码方法
     *
     * @param s
     * @return
     */
    public int numDecodings(String s) {
        int n = s.length();
        int a = 0;
        int b = 1;
        int c = 0;
        for (int i = 1; i <= n; i++) {
            c = 0;
            if (s.charAt(i - 1) != '0') {
                c += b;
            }

            if (i > 1 && s.charAt(i - 2) != '0' && (s.charAt(i - 2) - '0') * 10 + (s.charAt(i - 1) - '0') <= 26) {
                c += a;
            }

            a = b;
            b = c;
        }

        return c;
    }

    /**
     * 344. 反转字符串
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
     * <p>
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     *
     * @param s
     */
    public void reverseString(char[] s) {

        char temp;
        int n = s.length;
        int l = 0;
        int r = n - 1;
        while ((l < r)) {
            temp = s[l];
            s[l] = s[r];
            s[r] = temp;
            l++;
            r--;
        }

    }


    /**
     * 53. 最大子数组和
     * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * <p>
     * 子数组 是数组中的一个连续部分。
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {

        int dp = 0;
        int ans = nums[0];
        for (int i = 1; i < nums.length - 1; i++) {
            dp = Math.max(nums[i], dp + nums[i]);
            ans = Math.max(dp, ans);
        }

        return ans;
    }

    public int maxAbsoluteSum(int[] nums) {
        int minDp = 0;
        int minSum = nums[0];
        int maxDp = 0;
        int maxSum = nums[0];
        for (int num : nums) {
            minDp = Math.min(num, minDp + num);
            minSum = Math.min(minSum, minDp);
            maxDp = Math.max(num, maxDp + num);
            maxSum = Math.max(maxDp, maxSum);
        }

        return Math.max(maxSum, -minSum);

    }

    /**
     * @param n
     * @return
     */
    public int subtractProductAndSum(int n) {

        int product = 1;
        int sum = 0;
        while (n > 0) {
            int member = n % 10;
            product = product * member;
            sum = sum + member;
            n = n / 10;
        }

        return product - sum;

    }


    /**
     * 路径总和 II
     * 给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
     * <p>
     * 叶子节点 是指没有子节点的节点。
     *
     * @param root
     * @param targetSum
     * @return
     */

    List<List<Integer>> ret = new LinkedList();
    Deque<Integer> path = new LinkedList();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        depthFirstSearch(root, targetSum);
        return ret;
    }

    public void depthFirstSearch(TreeNode root, int targetSum) {
        if (root == null) {
            return;
        }

        path.offerLast(root.val);
        targetSum -= root.val;
        if (root.left == null && root.right == null && targetSum == 0) {
            ret.add(new LinkedList<>(path));
        }

        depthFirstSearch(root.left, targetSum);
        depthFirstSearch(root.right, targetSum);
        path.pollLast();

    }


    /**
     * 1289. 下降路径最小和 II
     * 给你一个 n x n 整数矩阵 grid ，请你返回 非零偏移下降路径 数字和的最小值。
     * <p>
     * 非零偏移下降路径 定义为：从 grid 数组中的每一行选择一个数字，且按顺序选出来的数字中，相邻数字不在原数组的同一列。
     *
     * @param grid
     * @return
     */
    public int minFallingPathSum(int[][] grid) {
        int n = grid.length;
        int firstMinVal = 0;
        int secondMinVal = 0;
        int firstMinIndex = -1;

        for (int row = 0; row < n; row++) {

            int curFirstMinVal = Integer.MAX_VALUE;
            int curSecondMinVal = Integer.MAX_VALUE;
            int curFirstMinIndex = -1;
            for (int col = 0; col < n; col++) {
                int curSum;
                curSum = col == firstMinIndex ? (grid[row][col] + secondMinVal) : (grid[row][col] + firstMinVal);
                if (curSum < curFirstMinVal) {
                    curSecondMinVal = curFirstMinVal;
                    curFirstMinVal = curSum;
                    curFirstMinIndex = col;
                } else if (curSum < curSecondMinVal) {
                    curSecondMinVal = curSum;
                }
            }

            firstMinVal = curFirstMinVal;
            secondMinVal = curSecondMinVal;
            firstMinIndex = curFirstMinIndex;
        }
        return firstMinVal;

    }


    /**
     * 617. 合并二叉树
     * 给你两棵二叉树： root1 和 root2 。
     * <p>
     * 想象一下，当你将其中一棵覆盖到另一棵之上时，两棵树上的一些节点将会重叠（而另一些不会）。你需要将这两棵树合并成一棵新二叉树。
     * 合并的规则是：如果两个节点重叠，那么将这两个节点的值相加作为合并后节点的新值；否则，不为 null 的节点将直接作为新二叉树的节点。
     * <p>
     * 返回合并后的二叉树。
     * <p>
     * 注意: 合并过程必须从两个树的根节点开始。
     *
     * @param root1
     * @param root2
     * @return
     */
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) {
            return root2;
        }

        if (root2 == null) {
            return root1;
        }

        TreeNode merge = new TreeNode(root1.val + root2.val);
        merge.left = mergeTrees(root1.left, root2.right);
        merge.right = mergeTrees(root1.right, root2.right);

        return merge;
    }


    public int longestConsecutive(int[] nums) {

        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int streak = 0;
        for (int num : set) {
            if (!set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                }

                streak = Math.max(streak, currentStreak);
            }
        }

        return streak;

    }


    public int sumNumbers(TreeNode root) {
        return sumNumbers(root, 0);
    }

    public int sumNumbers(TreeNode root, int num) {

        if (root == null) {
            return 0;
        }

        int pre = num * 10 + root.val;
        if (root.left == null && root.right == null) {
            return pre;
        }

        return sumNumbers(root.left, pre) + sumNumbers(root.right, pre);
    }
}
