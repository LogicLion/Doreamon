package com.example.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @author wzh
 * @date 2023/6/13
 */
class Solution3 {

    /**
     * 509. 斐波那契数
     * 斐波那契数 （通常用 F(n) 表示）形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。
     *
     * @param n
     * @return
     */
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

    public int climbStairs(int n) {

        int p = 0;
        int q = 0;
        int r = 1;
        for (int i = 1; i <= n; i++) {
            p = q;
            q = r;
            r = p + q;
        }

        return r;

    }


    /**
     * 1375. 二进制字符串前缀一致的次数
     *
     * @param flips
     * @return
     */
    public int numTimesAllBlue(int[] flips) {

        int ans = 0;
        int max = 0;
        for (int i = 0; i < flips.length; i++) {
            max = Math.max(max, flips[i]);

            if (i + 1 == max) {
                ans++;
            }
        }

        return ans;
    }


    /**
     * 55. 跳跃游戏
     * 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
     * <p>
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * <p>
     * 判断你是否能够到达最后一个下标。
     *
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {

        int most = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i <= most) {
                most = Math.max(most, i + nums[i]);
            } else {
                return false;
            }
        }

        return most >= nums.length - 1;

    }


    /**
     * 45. 跳跃游戏 II
     * 给定一个长度为 n 的 0 索引整数数组 nums。初始位置为 nums[0]。
     * <p>
     * 每个元素 nums[i] 表示从索引 i 向前跳转的最大长度。换句话说，如果你在 nums[i] 处，你可以跳转到任意 nums[i + j] 处:
     * <p>
     * 0 <= j <= nums[i]
     * i + j < n
     * 返回到达nums[n - 1] 的最小跳跃次数。生成的测试用例可以到达 nums[n - 1]。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/jump-game-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        int n = nums.length;

        int ans = 0;
        int most = 0;
        int targetRight = 0;
        for (int i = 0; i < n; i++) {
            if (most < n) {
                int j = i + nums[i];
            }
        }

        return ans;
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
        int res = nums[0];
        int pre = 0;
        for (int num : nums) {
            pre = Math.max(num, pre + num);
            res = Math.max(res, pre);
        }
        return res;
    }


    /**
     * 56. 合并区间
     * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返回一个不重叠的区间数组，
     * 该数组需恰好覆盖输入中的所有区间。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/merge-intervals
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {

        if (intervals.length <= 0) {
            return new int[0][];
        }

        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));

        List<int[]> merges = new ArrayList<>();

        for (int[] interval : intervals) {
            int L = interval[0];
            int R = interval[1];

            if (merges.isEmpty() || L > merges.get(merges.size() - 1)[1]) {
                merges.add(new int[]{L, R});
            } else {
                merges.get(merges.size() - 1)[1] = Math.max(R, merges.get(merges.size() - 1)[1]);
            }
        }

        return merges.toArray(new int[merges.size()][]);
    }


    /**
     * 57. 插入区间
     * 给你一个 无重叠的 ，按照区间起始端点排序的区间列表。
     * <p>
     * 在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
     *
     * @param intervals
     * @param newInterval
     * @return
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {

        List<int[]> ans = new ArrayList<>();

        boolean placed = false;

        int L = newInterval[0];
        int R = newInterval[1];

        for (int[] interval : intervals) {
            if (interval[0] > R) {
                //插入区间在当前遍历的区间的左侧，且无交集
                if (!placed) {
                    ans.add(new int[]{L, R});
                    placed = true;
                }

                ans.add(interval);
            } else if (interval[1] < L) {
                //插入区间在当前遍历的区间的右侧，且无交集
                ans.add(interval);
            } else {
                //插入区间与当前遍历区间有交集，计算他们的并集
                L = Math.min(L, interval[0]);
                R = Math.max(R, interval[1]);
            }
        }

        if (!placed) {
            ans.add(new int[]{L, R});
        }

        return ans.toArray(new int[ans.size()][]);

    }


    /**
     * 2679. 矩阵中的和
     * 给你一个下标从 0开始的二维整数数组nums。一开始你的分数为0。你需要执行以下操作直到矩阵变为空：
     * <p>
     * 矩阵中每一行选取最大的一个数，并删除它。如果一行中有多个最大的数，选择任意一个并删除。
     * 在步骤 1 删除的所有数字中找到最大的一个数字，将它添加到你的 分数中。
     * 请你返回最后的 分数。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/sum-in-a-matrix
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param nums
     * @return
     */
    public int matrixSum(int[][] nums) {
        for (int[] num : nums) {
            Arrays.sort(num);
        }

        printTwoDimensionalArray(nums);

        int ans = 0;
        for (int j = 0; j < nums[0].length; j++) {


            int max = 0;
            for (int i = 0; i < nums.length; i++) {
                max = Math.max(nums[i][j], max);
            }


            System.out.println(j + "行：max：" + max);
            ans += max;
        }

        return ans;
    }


    public static void printTwoDimensionalArray(int[][] intervals) {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < intervals.length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append("[");
            for (int j = 0; j < intervals[0].length; j++) {
                if (j != 0) {
                    sb.append(",");
                }
                sb.append(intervals[i][j]);
            }

            sb.append("]");
        }

        sb.append("]");

        System.out.println(sb);

    }


    /**
     * 62. 不同路径
     * 一个机器人位于一个 m x n网格的左上角 （起始点在下图中标记为 “Start” ）。
     * <p>
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
     * <p>
     * 问总共有多少条不同的路径？
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/unique-paths
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        int[][] f = new int[m][n];
        for (int i = 0; i < m; i++) {
            f[i][0] = 1;
        }

        for (int j = 0; j < n; j++) {
            f[0][j] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                f[i][j] = f[i - 1][j] + f[i][j - 1];
            }
        }

        return f[m - 1][n - 1];
    }


    /**
     * 63. 不同路径 II
     * 一个机器人位于一个m x n网格的左上角 （起始点在下图中标记为 “Start” ）。
     * <p>
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish”）。
     * <p>
     * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
     * <p>
     * 网格中的障碍物和空位置分别用 1 和 0 来表示。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/unique-paths-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        if (obstacleGrid[0][0] == 1 || obstacleGrid[m - 1][n - 1] == 1) {
            //起点或终点有障碍物，直接返回0
            return 0;
        }

        int[][] f = new int[m][n];
        for (int i = 0; i < m; i++) {
            if (obstacleGrid[i][0] == 0) {
                f[i][0] = 1;
            } else {
                //这里处理很重要，第一行和第一列一旦遇到障碍物表示后面都是不可达的
                break;
            }
        }

        for (int j = 0; j < n; j++) {
            if (obstacleGrid[0][j] == 0) {
                f[0][j] = 1;
            } else {
                break;
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {

                if (obstacleGrid[i][j] == 0) {
                    f[i][j] = f[i - 1][j] + f[i][j - 1];
                }
            }
        }


        return f[m - 1][n - 1];

    }


    /**
     * 2178. 拆分成最多数目的正偶数之和
     * 给你一个整数finalSum。请你将它拆分成若干个互不相同 的正偶数之和，且拆分出来的正偶数数目最多。
     * <p>
     * 比方说，给你finalSum = 12，那么这些拆分是符合要求 的（互不相同的正偶数且和为finalSum）：(2 + 10)，(2 + 4 + 6)和(4 + 8)。
     * 它们中，(2 + 4 + 6)包含最多数目的整数。注意finalSum不能拆分成(2 + 2 + 4 + 4)，因为拆分出来的整数必须互不相同。
     * 请你返回一个整数数组，表示将整数拆分成 最多 数目的正偶数数组。如果没有办法将finalSum进行拆分，请你返回一个空数组。你可以按 任意顺序返回这些整数。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/maximum-split-of-positive-even-integers
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param finalSum
     * @return
     */
    public List<Long> maximumEvenSplit(long finalSum) {

        List<Long> res = new ArrayList<>();
        if (finalSum % 2 > 0) {
            return res;
        }

        for (long i = 2L; i <= finalSum; i += 2) {
            res.add(i);
            finalSum = finalSum - i;
        }

        res.set(res.size() - 1, res.get(res.size() - 1) + finalSum);
        return res;
    }


    /**
     * 64. 最小路径和
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                if (i == 0 && j == 0) {
                    continue;
                } else if (i == 0) {
                    grid[i][j] = grid[i][j - 1] + grid[i][j];
                } else if (j == 0) {
                    grid[i][j] = grid[i - 1][j] + grid[i][j];
                } else {
                    grid[i][j] = Math.min(grid[i - 1][j], grid[i][j - 1]) + grid[i][j];
                }
            }
        }

        return grid[n - 1][m - 1];

    }


    /**
     * 69. x 的平方根
     * 给你一个非负整数 x ，计算并返回x的 算术平方根 。
     * <p>
     * 由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。
     * <p>
     * 注意：不允许使用任何内置指数函数和算符，例如 pow(x, 0.5) 或者 x ** 0.5 。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/sqrtx
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param x
     * @return
     */
    public int mySqrt(int x) {
        int l = 0;
        int r = x;
        int ans = -1;
        while (l <= r) {

            int mid = l + (r - l) / 2;
            if (mid * mid < x) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return ans;
    }


    /**
     * 74. 搜索二维矩阵
     * 给你一个满足下述两条属性的 m x n 整数矩阵：
     * <p>
     * 每行中的整数从左到右按非递减顺序排列。
     * 每行的第一个整数大于前一行的最后一个整数。
     * 给你一个整数 target ，如果 target 在矩阵中，返回 true ；否则，返回 false 。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/search-a-2d-matrix
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {

        int m = matrix.length;
        int n = matrix[0].length;

        //纵向搜索每一行
        int t = 0;
        int b = m - 1;

        //目标行
        int targetRow = 0;
        int midRow;
        while (t < b) {

            //这里+1很重要，
            // 因为midRow==t的话就会死循环，也就是midRow计算出来后不能等于当前的t
            //因为t<b，而b和t的差距最小为1，会出现t==midRow的情况
            //所以+1会让b+t的差距最小为2，就不会在计算了(b + t + 1) / 2之后midRow的值还等于t的情况
            //二分法，细节是魔鬼

            midRow = (b + t + 1) / 2;
            if (matrix[midRow][0] < target) {
                t = midRow;
                targetRow = t;
            } else if (matrix[midRow][0] > target) {
                b = midRow - 1;
            } else {
                return true;
            }
        }


        int l = 0;
        int r = n - 1;
        int midCol;

        while (l <= r) {
            midCol = (l + r) / 2;
            if (matrix[targetRow][midCol] < target) {
                l = midCol + 1;
            } else if (matrix[targetRow][midCol] > target) {
                r = midCol - 1;
            } else {
                return true;
            }
        }

        return false;

    }


    /**
     * 33. 搜索旋转排序数组
     * 整数数组 nums 按升序排列，数组中的值 互不相同 。
     * <p>
     * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，
     * 使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
     * 例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为[4,5,6,7,0,1,2] 。
     * <p>
     * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回-1。
     * <p>
     * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/search-in-rotated-sorted-array
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {

        int l = 0;
        int r = nums.length - 1;
        int mid = 0;

        //对半分，总有一半是有序的

        while (l <= r) {
            mid = (l + r + 1) / 2;

            if (nums[l] < nums[mid]) {
                //左半有序

                if (target >= nums[l] && target <= nums[mid]) {
                    //在该区间
                    if (target == nums[l]) {
                        return l;
                    } else if (target == nums[mid]) {
                        return mid;
                    }
                    r = mid - 1;
                } else {
                    //不在该区间
                    l = mid + 1;
                }
            } else {

                //右半有序
                if (target >= nums[mid] && target <= nums[r]) {
                    //在该区间
                    if (target == nums[r]) {
                        return r;
                    } else if (target == nums[mid]) {
                        return mid;
                    }
                    l = mid + 1;
                } else {
                    //不在该区间
                    r = mid - 1;
                }
            }
        }

        System.out.println("l的坐标：" + l);
        System.out.println("r的坐标：" + r);
        System.out.println("mid的坐标：" + mid);


        return -1;
    }


    /**
     * 704. 二分查找
     *
     * @param nums
     * @param target
     * @return
     */
    public int search1(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;

        while (l <= r) {
            int mid = (l + r) / 2;

            if (nums[mid] > target) {
                r = mid - 1;
            } else if (nums[mid] < target) {
                l = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }


    /**
     * 2544. 交替数字和
     * 给你一个正整数 n 。n 中的每一位数字都会按下述规则分配一个符号：
     * <p>
     * 最高有效位 上的数字分配到 正 号。
     * 剩余每位上数字的符号都与其相邻数字相反。
     * 返回所有数字及其对应符号的和。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/alternating-digit-sum
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param n
     * @return
     */
    public int alternateDigitSum(int n) {
        int sign = 1;
        int ans = 0;
        while (n > 0) {
            ans += (n % 10) * sign;
            n = n / 10;
            sign = sign * (-1);
        }

        if (sign == -1) {
            //说明是偶数位数，结果取反
            ans = -ans;
        }

        return ans;
    }


    /**
     * 82. 删除排序链表中的重复元素 II
     * 给定一个已排序的链表的头 head ， 删除原始链表中所有重复数字的节点，只留下不同的数字 。返回 已排序的链表 。
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode cur = dummy;

        while (cur.next != null && cur.next.next != null) {

            if (cur.next.val == cur.next.next.val) {
                int x = cur.next.val;

                while (cur.next != null && cur.next.val == x) {
                    cur.next = cur.next.next;
                }
            } else {
                cur = cur.next;
            }

        }

        return dummy.next;
    }


    /**
     * 931. 下降路径最小和
     * 给你一个 n x n 的 方形 整数数组matrix ，请你找出并返回通过 matrix 的下降路径 的 最小和 。
     * <p>
     * 下降路径 可以从第一行中的任何元素开始，并从每一行中选择一个元素。
     * 在下一行选择的元素和当前行所选元素最多相隔一列（即位于正下方或者沿对角线向左或者向右的第一个元素）。
     * 具体来说，位置 (row, col) 的下一个元素应当是 (row + 1, col - 1)、(row + 1, col) 或者 (row + 1, col + 1) 。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/minimum-falling-path-sum
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param matrix
     * @return
     */
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0];
        }

        int ans = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    matrix[i][j] = Math.min(matrix[i - 1][j], matrix[i - 1][j + 1]) + matrix[i][j];
                } else if (j == n - 1) {
                    matrix[i][j] = Math.min(matrix[i - 1][j], matrix[i - 1][j - 1]) + matrix[i][j];
                } else {
                    int min = Math.min(matrix[i - 1][j - 1], matrix[i - 1][j + 1]);
                    matrix[i][j] = Math.min(min, matrix[i - 1][j]) + matrix[i][j];
                }

                if (i == n - 1) {
                    if (j == 0) {
                        ans = matrix[i][j];
                    } else {
                        ans = Math.min(matrix[i][j], ans);
                    }
                }

            }
        }

        return ans;
    }


    /**
     * 83. 删除排序链表中的重复元素
     * 给定一个已排序的链表的头 head ， 删除所有重复的元素，使每个元素只出现一次 。返回 已排序的链表 。
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicates1(ListNode head) {

        ListNode cur = head;
        while (cur.next != null) {
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }

        return head;
    }


    /**
     * 94. 二叉树的中序遍历
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {

        List<Integer> res = new ArrayList<>();

        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                //不断往左子树方向走，每走一次就将当前节点保存到栈中
                //这是模拟递归的调用
                stack.push(root);
                root = root.left;
            } else {
                //当前节点为空，说明左边走到头了，从栈中弹出节点并保存
                //然后转向右边节点，继续上面整个过程
                TreeNode pop = stack.pop();
                res.add(pop.val);
                root = pop.right;
            }
        }

        return res;
    }


    /**
     * 101. 对称二叉树
     * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {

        return check(root.left, root.right);
    }

    public boolean check(TreeNode p1, TreeNode p2) {
        if (p1 == null && p2 == null) {
            return true;
        } else if (p1 != null && p2 != null && p1.val == p2.val) {
            return check(p1.left, p2.right) && check(p1.right, p2.left);
        } else {
            return false;
        }
    }


    public int maxProfit(int[] prices) {
        int inPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            if (price >= inPrice) {
                //卖出
                maxProfit += price - inPrice;
            }
            inPrice = price;
        }

        return maxProfit;
    }


    /**
     * 86. 分隔链表
     * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
     * <p>
     * 你应当 保留 两个分区中每个节点的初始相对位置。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/partition-list
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        ListNode smallNode = new ListNode(0);
        ListNode largeNode = new ListNode(0);

        ListNode small = smallNode;
        ListNode large = largeNode;

        while (head != null) {
            if (head.val <= x) {
                smallNode.next = head;
                smallNode = smallNode.next;
            } else {
                largeNode.next = head;
                largeNode = largeNode.next;
            }
            head = head.next;
        }
        //最后一个值必须指向空，不然它指向的还是最后一次指向的节点
        largeNode.next = null;
        smallNode.next = large.next;

        return small.next;
    }


    /**
     * 104. 二叉树的最大深度
     * 给定一个二叉树，找出其最大深度。
     * <p>
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     * <p>
     * 说明: 叶子节点是指没有子节点的节点。
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int leftDepth = maxDepth(root.left);
            int rightDepth = maxDepth(root.right);
            return Math.max(leftDepth, rightDepth) + 1;
        }

    }


    public boolean isInterleave(String s1, String s2, String s3) {

        int l = s1.length();
        int m = s2.length();
        int n = s3.length();
        if (l + m != n) {
            return false;
        }

        boolean[][] dp = new boolean[l + 1][m + 1];

        //作为边界条件
        dp[0][0] = true;

        for (int i = 0; i <= l; i++) {
            for (int j = 0; j <= m; j++) {
                int k = i + j - 1;

                if (i > 0) {
                    dp[i][j] = dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(k);
                }

                if (j > 0) {
                    dp[i][j] = dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(k);
                }
            }
        }
        return dp[l][m];

    }


    /**
     * 102. 二叉树的层序遍历
     * 给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        queue.offer(root);
        while (!queue.isEmpty()) {
            int currLevelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < currLevelSize; i++) {
                TreeNode poll = queue.poll();
                level.add(poll.val);

                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
            res.add(level);
        }


        Collections.reverse(res);


        return res;
    }


    /**
     * 112. 路径总和
     * 给你二叉树的根节点root 和一个表示目标和的整数targetSum 。判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和targetSum 。如果存在，返回 true ；否则，返回 false 。
     * <p>
     * 叶子节点 是指没有子节点的节点。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/path-sum
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param root
     * @param targetSum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {

        if (root == null) {
            return false;
        }

        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }

        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }


    /**
     * 2500. 删除每行中的最大值
     * 给你一个 m x n 大小的矩阵 grid ，由若干正整数组成。
     * <p>
     * 执行下述操作，直到 grid 变为空矩阵：
     * <p>
     * 从每一行删除值最大的元素。如果存在多个这样的值，删除其中任何一个。
     * 将删除元素中的最大值与答案相加。
     * 注意 每执行一次操作，矩阵中列的数据就会减 1 。
     * <p>
     * 返回执行上述操作后的答案。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/delete-greatest-value-in-each-row
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param grid
     * @return
     */
    public int deleteGreatestValue(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        for (int i = 0; i < m; i++) {
            Arrays.sort(grid[i]);
        }

        int ans = 0;
        for (int j = 0; j < n; j++) {

            int maxVal = 0;
            for (int i = 0; i < m; i++) {
                maxVal = Math.max(maxVal, grid[i][j]);
            }
            ans += maxVal;
        }

        return ans;

    }


}
