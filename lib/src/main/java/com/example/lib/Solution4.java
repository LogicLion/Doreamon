package com.example.lib;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

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


    public int distMoney(int money, int children) {
        if (money < children) {
            return -1;
        }
        if (money < children + 7) {
            return 0;
        }

        //先假定每个人分配了1美元
        int i = money - children;
        int i1 = i / 7;
        int i2 = i % 7;
        if (i1 == children && i2 > 0 || i1 > children) {
            //每人分配了8美元依旧有溢出款,必然有一个人超过8美元
            return children - 1;
        }
        if (i2 == 3 && i1 == children - 1) {
            //最后一个人分配到了4美元
            return i1 - 1;
        } else {
            return i1;
        }
    }


    int n;
    int m;

    public void solve(char[][] board) {
        //行数
        n = board.length;
        if (n == 0) {
            return;
        }

        //列数
        m = board[0].length;

        for (int i = 0; i < n; i++) {
            dfs(board, i, 0);
            dfs(board, i, m - 1);
        }

        for (int j = 0; j < m; j++) {
            dfs(board, 0, j);
            dfs(board, n - 1, j);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 'A') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    public void dfs(char[][] board, int x, int y) {
        if (x < 0 || x > n - 1 || y < 0 || y > m - 1 || board[x][y] != 'O') {
            return;
        }
        board[x][y] = 'A';
        dfs(board, x - 1, y);
        dfs(board, x + 1, y);
        dfs(board, x, y - 1);
        dfs(board, x, y + 1);
    }


    public int singleNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> set : map.entrySet()) {
            Integer key = set.getKey();
            Integer value = set.getValue();

            if (value == 1) {
                return key;
            }
        }
        return 0;
    }


    Map<Node1, Node1> map = new HashMap<>();

    /**
     * 138. 随机链表的复制
     * 给你一个长度为 n 的链表，每个节点包含一个额外增加的随机指针 random ，该指针可以指向链表中的任何节点或空节点。
     * <p>
     * 构造这个链表的 深拷贝。 深拷贝应该正好由 n 个 全新 节点组成，其中每个新节点的值都设为其对应的原节点的值。
     * 新节点的 next 指针和 random 指针也都应指向复制链表中的新节点，并使原链表和复制链表中的这些指针能够表示相同的链表状态。
     * 复制链表中的指针都不应指向原链表中的节点 。
     *
     * @param head
     * @return
     */


//    public Node copyRandomList1(Node head) {
//        if (head == null) {
//            return null;
//        }
//
//        for (Node node = head; node != null; node = node.next.next) {
//            Node newNode = new Node(node.val);
//            newNode.next = node.next;
//            node.next = newNode;
//        }
//
//        for (Node node = head; node != null; node = node.next.next) {
//            node.next.random = node.random == null ? null : node.random.next;
//        }
//
//        Node headNew = head.next;
//        for (Node node = head; node != null; node = node.next) {
//
//            Node nodeNew = node.next;
//            node.next = node.next.next;
//
//            nodeNew.next = (nodeNew.next != null) ? nodeNew.next.next : null;
//        }
//
//        return headNew;
//    }


    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        if (n <= 2) {
            return n;
        }
        int slow = 2;
        int fast = 2;

        while (fast < n) {
            if (nums[slow - 2] != nums[fast]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }

        return slow;
    }


    /**
     * 2652. 倍数求和
     * 给你一个正整数 n ，请你计算在 [1，n] 范围内能被 3、5、7 整除的所有整数之和。
     * <p>
     * 返回一个整数，用于表示给定范围内所有满足约束条件的数字之和。
     *
     * @param n
     * @return
     */
    public int sumOfMultiples(int n) {

        int sum = 0;
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 || i % 5 == 0 || i % 7 == 0) {
                sum += i;
            }
        }
        return sum;
    }


    public int fib(int n) {
        if (n < 2) {
            return n;
        }
        int p = 0;
        int q = 0;
        int r = 1;
        for (int i = 2; i <= n; i++) {
            p = q;
            q = r;
            r = p + q;
        }

        return r;
    }

    /**
     * 1137. 第 N 个泰波那契数
     *
     * @param n
     * @return
     */
    public int tribonacci(int n) {
        if (n == 0) {
            return 0;
        }
        if (n <= 2) {
            return 1;
        }

        int p = 0;
        int q = 0;
        int r = 1;
        int s = 1;

        for (int i = 3; i <= n; i++) {
            p = q;
            q = r;
            r = s;
            s = p + q + r;
        }

        return s;
    }


    /**
     * 746. 使用最小花费爬楼梯
     *
     * @param cost
     * @return
     */
    public int minCostClimbingStairs(int[] cost) {

        int n = cost.length;

        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 0;

        for (int i = 2; i <= n; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }

        return dp[n];
    }


    /**
     * 198. 打家劫舍
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        int n = nums.length;

        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return nums[0];
        }
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }

        return dp[n - 1];
    }


    /**
     * 740. 删除并获得点数
     * <p>
     * 给你一个整数数组 nums ，你可以对它进行一些操作。
     * <p>
     * 每次操作中，选择任意一个 nums[i] ，删除它并获得 nums[i] 的点数。之后，你必须删除 所有 等于 nums[i] - 1 和 nums[i] + 1 的元素。
     * <p>
     * 开始你拥有 0 个点数。返回你能通过这些操作获得的最大点数。
     *
     * @param nums
     * @return
     */
    public int deleteAndEarn(int[] nums) {

        int maxVal = 0;
        for (int val : nums) {
            maxVal = Math.max(val, maxVal);
        }

        int[] sum = new int[maxVal + 1];

        for (int val : nums) {
            sum[val] += val;
        }

        return rob(sum);

    }


    /**
     * 62. 不同路径
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {

        int[][] dp = new int[m][n];

        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }

        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }


        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];
    }


    /**
     * 64. 最小路径和
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        dp[0][0] = grid[0][0];

        for (int i = 1; i < m; i++) {
            dp[i][0] = grid[i][0] + dp[i - 1][0];
        }

        for (int j = 1; j < n; j++) {
            dp[0][j] = grid[0][j] + dp[0][j - 1];
        }


        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        return dp[m - 1][n - 1];

    }


    /**
     * 63. 不同路径 II
     *
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        if (obstacleGrid[0][0] == 1 || obstacleGrid[m - 1][n - 1] == 1) {
            return 0;
        }

        dp[0][0] = 1;

        for (int i = 1; i < m; i++) {
            if (obstacleGrid[i][0] == 1 || dp[i - 1][0] == 0) {
                dp[i][0] = 0;
            } else {
                dp[i][0] = 1;
            }
        }

        for (int j = 1; j < n; j++) {
            if (obstacleGrid[0][j] == 1 || dp[0][j - 1] == 0) {
                dp[0][j] = 0;
            } else {
                dp[0][j] = 1;
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                    continue;
                }

                if (obstacleGrid[i - 1][j] == 1 && obstacleGrid[i][j - 1] == 1) {
                    dp[i][j] = 0;
                    continue;
                }

                if (obstacleGrid[i - 1][j] != 1) {
                    dp[i][j] += dp[i - 1][j];
                }

                if (obstacleGrid[i][j - 1] != 1) {
                    dp[i][j] += dp[i][j - 1];
                }
            }
        }

        return dp[m - 1][n - 1];
    }


    /**
     * 120. 三角形最小路径和
     *
     * @param triangle
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle) {

        int m = triangle.size();
        if (m == 0) {
            return 0;
        }
        List<Integer> dp = new ArrayList<>();
        List<Integer> lastDp = new ArrayList<>();

        lastDp.add(0);
        dp.add(triangle.get(0).get(0));

        for (int i = 1; i < m; i++) {

            lastDp.clear();
            lastDp.addAll(dp);
            dp.clear();
            List<Integer> integerList = triangle.get(i);
            for (int j = 0; j < integerList.size(); j++) {

                System.out.println("dp:size:" + lastDp.size());
                System.out.println("integerList:size:" + integerList.size());
                if (j == 0) {
                    dp.add(j, lastDp.get(j) + integerList.get(j));
                } else if (j == integerList.size() - 1) {
                    dp.add(j, lastDp.get(j - 1) + integerList.get(j));
                } else {
                    dp.add(j, Math.min(lastDp.get(j) + integerList.get(j), lastDp.get(j - 1) + integerList.get(j)));
                }
            }

        }

        int minValue = dp.get(0);
        for (int k = 0; k < dp.size(); k++) {
            minValue = Math.min(minValue, dp.get(k));
        }


        return minValue;
    }


    public int climbStairs(int n) {

        if (n == 0) {
            return 0;
        }

        int p = 0;
        int q = 0;
        int r = 1;


        for (int i = 2; i <= n; i++) {
            p = q;
            q = r;
            r = p + q;

        }
        return r;
    }


    public int minFallingPathSum1(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    dp[i][j] = matrix[0][j];
                    continue;
                }

                if (j == 0) {
                    dp[i][j] = matrix[i][j] + Math.min(dp[i - 1][j], dp[i - 1][j + 1]);
                } else if (j == n - 1) {
                    dp[i][j] = matrix[i][j] + Math.min(dp[i - 1][j], dp[i - 1][j - 1]);
                } else {
                    dp[i][j] = matrix[i][j] + Math.min(Math.min(dp[i - 1][j], dp[i - 1][j - 1]), dp[i - 1][j + 1]);
                }

            }
        }

        int minVal = dp[m - 1][0];
        for (int k = 0; k < n; k++) {
            minVal = Math.min(minVal, dp[m - 1][k]);
        }

        return minVal;
    }


    /**
     * 221. 最大正方形
     *
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        int[][] dp = new int[rows][columns];

        int maxSide = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i - 1][j - 1]), dp[i][j - 1]) + 1;
                    }

                    maxSide = Math.max(dp[i][j], maxSide);
                }
            }
        }

        int maxSquare = maxSide * maxSide;
        return maxSquare;

    }


    public int maximumNumberOfStringPairs(String[] words) {

        int ans = 0;
        Set<String> set = new HashSet<>();

        StringBuilder sb = new StringBuilder();
        for (String word : words) {

            sb.setLength(0);
            if (set.contains(sb.append(word).reverse().toString())) {

                ans++;
            }
            set.add(word);
        }
        return ans;
    }

    public int lengthOfLIS(int[] nums) {
        int len = nums.length;
        if (len < 2) {
            return len;
        }
        int[] dp = new int[len];
        int end = 0;
        dp[0] = nums[0];


        for (int num : nums) {
            if (dp[end] < num) {
                end++;
                dp[end] = num;
            } else {
                int left = 0;
                int right = end;
                while (left < right) {
                    int mid = left + (right - left) / 2;
                    if (num > dp[mid]) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }

                dp[left] = num;
            }
        }

        //返回的是长度
        return end + 1;


    }


    public int maximumSwap(int num) {
        char[] charArray = String.valueOf(num).toCharArray();

        int n = charArray.length;
        int maxIndex = n - 1;
        int idx1 = -1;
        int idx2 = 0;

        for (int i = n - 1; i >= 0; i--) {
            if (charArray[maxIndex] < charArray[i]) {
                //当前最大
                maxIndex = i;
            } else if (charArray[maxIndex] > charArray[i]) {
                idx1 = i;
                idx2 = maxIndex;
            }
        }

        if (idx1 == -1) {
            return num;
        }

        char temp = charArray[idx1];
        charArray[idx1] = charArray[idx2];
        charArray[idx2] = temp;

        return Integer.parseInt(new String(charArray));
    }


    public int alternatingSubarray(int[] nums) {
        int n = nums.length;
        int i = 0;

        int res = -1;
        while (i < n - 1) {
            if (nums[i + 1] - nums[i] != 1) {
                //直接跳过
                i++;
                continue;
            }

            int i0 = i;
            i += 2;
            while (i < n && nums[i] == nums[i - 2]) {
                i++;
            }

            res = Math.max(res, i - i0);
            i--;
        }
        return res;
    }


    public void merge(int[] nums1, int m, int[] nums2, int n) {


        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        while (i >= 0 || j >= 0) {
            if (i == -1) {
                nums1[k] = nums2[j];
                j--;
                k--;
                continue;
            }

            if (j == -1) {
                nums1[k] = nums1[i];
                i--;
                k--;
                continue;
            }

            if (nums1[i] < nums2[j]) {

                nums1[k] = nums2[j];
                j--;
            } else {
                nums1[k] = nums1[i];
                i--;
            }
            k--;

        }

    }


    public int majorityElement(int[] nums) {
        int count = 0;
        int candidate = 0;

        for (int num : nums) {

            if (candidate != num) {
                if (count == 0) {
                    candidate = num;
                } else {
                    count--;
                }
            } else {
                count++;
            }

        }

        return candidate;
    }


    public boolean canJump(int[] nums) {

        int most = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (most > i) {
                most = Math.max(most, nums[i] + i);
            } else {
                return false;
            }
        }

        return true;
    }


    public int jump(int[] nums) {

        int steps = 0;
        int maxPosition = 0;
        int end = 0;

        for (int i = 0; i < nums.length; i++) {
            maxPosition = Math.max(maxPosition, nums[i] + i);

            if (end == i) {
                end = maxPosition;
                steps++;
            }
        }

        return steps;
    }


    class RandomizedSet {

        List<Integer> nums;
        Map<Integer, Integer> indexs;
        Random mRandom;

        public RandomizedSet() {
            nums = new ArrayList<>();
            indexs = new HashMap<>();
            mRandom = new Random();
        }

        public boolean insert(int val) {
            if (indexs.containsKey(val)) {
                return false;
            }

            nums.add(val);
            indexs.put(val, nums.size() - 1);
            return true;
        }

        public boolean remove(int val) {
            if (!indexs.containsKey(val)) {
                return false;
            }
            int index = indexs.get(val);
            int last = nums.get(nums.size() - 1);
            nums.set(index, last);
            indexs.put(last, index);
            nums.remove(nums.size() - 1);
            indexs.remove(val);
            return true;

        }

        public int getRandom() {

            int random = mRandom.nextInt(nums.size());
            return nums.get(random);
        }
    }


    public int[] productExceptSelf(int[] nums) {
        int length = nums.length;
        int[] res = new int[length];

        res[0] = 1;
        for (int i = 1; i < length; i++) {
            res[i] = nums[i - 1] * res[i - 1];
        }

        int R = 1;
        for (int j = length - 2; j >= 0; j--) {
            R *= nums[j + 1];
            res[j] *= R;
        }

        return res;

    }


    public int candy(int[] ratings) {
        int length = ratings.length;
        int[] left = new int[length];
        left[0] = 1;
        for (int i = 1; i < length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }

        int res2 = 0;
        int right = 1;
        for (int j = length - 1; j >= 0; j--) {
            if (j < length - 1 && ratings[j] > ratings[j + 1]) {
                right++;
            } else {
                right = 1;
            }

            res2 += Math.max(left[j], right);
        }

        return res2;
    }


    /**
     * 125. 验证回文串
     *
     * @param s
     * @return
     */
    public boolean isPalindrome(String s) {

        char[] chars = s.toCharArray();
        int n = s.length();
        int l = 0;
        int r = n - 1;

        while (l <= r) {
            if (!Character.isLetter(chars[l])) {
                l++;
                continue;
            }

            if (!Character.isLetter(chars[r])) {
                r--;
                continue;
            }
            if (Character.toLowerCase(chars[l]) != Character.toLowerCase(chars[r])) {
                return false;
            } else {
                l++;
                r--;
            }
        }

        return true;
    }


    /**
     * 392. 判断子序列
     * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
     * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence(String s, String t) {
        int l1 = 0;
        int l2 = 0;

        while (l1 <= s.length() && l2 <= t.length()) {
            if (l1 == s.length()) {
                return true;
            } else if (l2 == t.length()) {
                return false;
            }

            if (s.charAt(l1) == t.charAt(l2)) {
                l1++;
            }
            l2++;
        }
        return false;
    }


    public int[] twoSum(int[] numbers, int target) {

        int n = numbers.length;
        int l = 0;
        int r = n - 1;
        while (l < r) {
            if (numbers[l] + numbers[r] > target) {
                r--;
            } else if (numbers[l] + numbers[r] < target) {
                l++;
            } else {
                return new int[]{l + 1, r + 1};
            }
        }

        return new int[]{-1, -1};
    }


    public int[] distinctDifferenceArray(int[] nums) {
        int n = nums.length;
        Set<Integer> set = new HashSet<>();

        //后缀
        int[] sufNum = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            set.add(nums[i]);
            sufNum[i] = set.size();
        }

        int[] res = new int[n];
        set.clear();
        for (int j = 0; j < n; j++) {
            set.add(nums[j]);
            res[j] = set.size() - sufNum[j + 1];
        }

        return res;
    }


    public boolean containsNearbyDuplicate(int[] nums, int k) {

        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            if (map.containsKey(num) && i - map.get(num) <= k) {
                return true;
            }
            map.put(num, i);
        }

        return false;
    }


    public boolean isSameTree(TreeNode p, TreeNode q) {

        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null) {
            return false;
        } else if (p.val == q.val) {
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        } else {
            return false;
        }

    }


    public List<Integer> postorder(NodeTree root) {

        List<Integer> list = new ArrayList();
        iterate(list, root);

        return list;
    }

    public void iterate(List<Integer> list, NodeTree root) {
        if (root == null) {
            return;
        }
        if (root.children == null || root.children.size() == 0) {

            list.add(root.val);
        } else {
            for (NodeTree child : root.children) {
                iterate(list, child);
            }

            list.add(root.val);
        }

    }


    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int leftDepth = maxDepth(root.left);
            int rightDepth = maxDepth(root.right);
            return Math.max(leftDepth, rightDepth) + 1;
        }
    }


    public boolean isValid(String s) {

        if (s.length() % 2 == 1) {
            return false;
        }
        Map<Character, Character> pairs = new HashMap<Character, Character>() {{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};


        char[] chars = s.toCharArray();

        Stack<Character> stack = new Stack<>();
        for (char c : chars) {

            if (pairs.containsKey(c)) {
                if (stack.isEmpty() || stack.peek() != pairs.get(c)) {
                    return false;
                }

                stack.pop();
            } else {
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }


    public int minSubArrayLen(int target, int[] nums) {


        int left = 0;
        int right = 0;

        int tempSum = 0;
        int res = Integer.MAX_VALUE;

        while (right < nums.length) {
            tempSum += nums[right];


            while (tempSum >= target) {
                res = Math.min(res, right - left + 1);
                tempSum -= nums[left];
                left++;
            }
            right++;
        }

        return res == Integer.MAX_VALUE ? 0 : res;


    }


    public void moveZeroes(int[] nums) {

        //如果数组没有0，那么快慢指针始终指向同一个位置，每个位置自己和自己交换；如果数组有0，快指针先走一步，此时慢指针对应的就是0，所以要交换。

        int l = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                if (i > l) {//左右指针指向同一个位置时不用交换

                    int temp = nums[i];
                    nums[i] = nums[l];
                    nums[l] = temp;
                }
                //非0的时候左指针移动
                l++;
            }
        }
    }


    Map<Integer, Integer> indexMap;


//    public TreeNode buildTree(int[] inorder, int[] postorder) {
//        indexMap = new HashMap<>();
//        for (int i = 0; i < inorder.length; i++) {
//            indexMap.put(inorder[i], i);
//        }
//        return buildTreeHelper(postorder, 0, postorder.length - 1, inorder, 0, inorder.length - 1);
//
//    }
//
//    private TreeNode buildTreeHelper(int[] postorder, int p_start, int p_end, int[] inorder, int i_start, int i_end) {
//        if (p_start > p_end) {
//            return null;
//        }
//
//        TreeNode rootNode = new TreeNode(postorder[p_end]);
//
//        int i_root_index = indexMap.get(postorder[p_end]);
//
//        int leftNum = i_root_index - i_start;
//
//        rootNode.left = buildTreeHelper(postorder, p_start, p_start + leftNum - 1, inorder, i_start, i_start + leftNum);
//        rootNode.right = buildTreeHelper(postorder, p_start + leftNum + 1, p_end - 1, inorder, i_root_index + 1, i_end);
//        return rootNode;
//    }


    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {

        indexMap = new HashMap<>();
        for (int i = 0; i < postorder.length; i++) {
            indexMap.put(postorder[i], i);
        }
        return buildTreeHelper(preorder, 0, preorder.length - 1, postorder, 0, postorder.length - 1);
    }


    private TreeNode buildTreeHelper(int[] preorder, int pre_start, int pre_end, int[] postorder, int post_start, int post_end) {
        if (pre_start > pre_end) {
            return null;
        }

        TreeNode rootNode = new TreeNode(preorder[pre_start]);

        int leftCount = 0;
        if (pre_start < pre_end) {
            leftCount = indexMap.get(postorder[pre_start]) - post_start + 1;
        }


        rootNode.left = buildTreeHelper(preorder, pre_start + 1, pre_start + leftCount, postorder, post_start, post_start + leftCount - 1);
        rootNode.right = buildTreeHelper(preorder, pre_start + leftCount + 1, pre_end, postorder, post_start + leftCount, post_end - 1);
        return rootNode;
    }


    public long kthLargestLevelSum(TreeNode root, int k) {
        Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
        queue.offer(root);
        List<Long> levelSumArray = new ArrayList<>();
        while (!queue.isEmpty()) {
            List<TreeNode> temp = new ArrayList<>(queue);
            long levelSum = 0;
            queue.clear();
            for (TreeNode node : temp) {
                levelSum += node.val;
                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            levelSumArray.add(levelSum);
        }

        if (levelSumArray.size() < k) {
            return -1;
        }

        Collections.sort(levelSumArray);
        return levelSumArray.get(levelSumArray.size() - k);


    }


    public boolean canConstruct(String ransomNote, String magazine) {
        for (int i = 0; i < ransomNote.length(); i++) {
            String c = String.valueOf(ransomNote.charAt(i));
            if (magazine.contains(c)) {
                magazine = magazine.replaceFirst(c, "");
            } else {
                return false;
            }
        }

        return true;
    }


    public void rotate(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }


    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> s2t = new HashMap<>();
        Map<Character, Character> t2s = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char s1 = s.charAt(i);
            char t1 = t.charAt(i);
            if (s2t.containsKey(s1)) {
                if (s2t.get(s1) != t1) {
                    return false;
                }
            } else {
                s2t.put(s1, t1);
            }

            if (t2s.containsKey(t1)) {
                if (t2s.get(t1) != s1) {
                    return false;
                }
            } else {
                t2s.put(t1, s1);
            }
        }

        return true;
    }


    public boolean wordPattern(String pattern, String s) {
        String[] wordList = s.split(" ");

        if (pattern.length() != wordList.length) {
            return false;
        }

        Map<Character, String> s2t = new HashMap<>();
        Map<String, Character> t2s = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            char s1 = pattern.charAt(i);
            String t1 = wordList[i];
            if (s2t.containsKey(s1)) {
                if (!s2t.get(s1).equals(t1)) {
                    return false;
                }
            } else {
                s2t.put(s1, t1);
            }

            if (t2s.containsKey(t1)) {
                if (t2s.get(t1) != s1) {
                    return false;
                }
            } else {
                t2s.put(t1, s1);
            }
        }

        return true;
    }


    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        Map<Character, Integer> map = new HashMap<>();
        for (Character x : s.toCharArray()) {
            map.put(x, map.getOrDefault(x, 0) + 1);
        }

        for (Character y : t.toCharArray()) {
            map.put(y, map.getOrDefault(y, 0) - 1);

            if (map.get(y) < 0) {
                return false;
            }
        }


        return true;


    }


    public List<List<String>> groupAnagrams(String[] strs) {

        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String s1 = new String(chars);
            List<String> list = map.getOrDefault(s1, new ArrayList<>());
            list.add(s);
            map.put(s1, list);
        }

        return new ArrayList<List<String>>(map.values());
    }


    public int maxArea(int[] height) {

        int l = 0;
        int r = height.length - 1;

        int res = 0;
        while (l < r) {
            int minHeight = Math.min(height[l], height[r]);
            res = Math.max(res, (r - l) * minHeight);

            if (height[l] < height[r]) {
                l++;
            } else {
                r--;
            }
        }

        return res;

    }


}



