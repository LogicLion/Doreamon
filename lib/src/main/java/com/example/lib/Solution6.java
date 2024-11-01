package com.example.lib;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author wzh
 * @date 2024/3/28
 */
class Solution6 {

    public int max = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return max;
    }

    public int maxGain(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = Math.max(0, maxGain(root.left));
        int right = Math.max(0, maxGain(root.right));

        //更新答案
        max = Math.max(max, root.val + left + right);

        //返回当前节点的最大贡献值
        return root.val + Math.max(left, right);
    }


    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();

        queue.offer(root);
        while (!queue.isEmpty()) {
            double sum = 0;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                sum += node.val;

                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            res.add(sum / size);
        }
        return res;
    }


    public int maxSubArray(int[] nums) {

        int dp = 0;
        int res = nums[0];
        for (int i = 0; i < nums.length; i++) {
            dp = Math.max(dp + nums[i], nums[i]);
            res = Math.max(res, dp);
        }

        return res;
    }


    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            res.add(list);
        }

        return res;
    }


    int pre = -1;
    int res = Integer.MAX_VALUE;

    public int getMinimumDifference(TreeNode root) {

        int pre = -1;
        inOrder(root);

        return res;
    }

    private void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        if (pre == -1) {
            pre = root.val;
        } else {
            res = Math.min(res, root.val - pre);
            pre = root.val;
        }
        inOrder(root.right);
    }


    int order = 1;
    int ans = 0;

    public int kthSmallest(TreeNode root, int k) {
        inOrder(root, k);
        return ans;
    }

    private void inOrder(TreeNode root, int k) {
        if (root == null || order > k) {
            return;
        }
        inOrder(root.left, k);
        if (order == k) {
            ans = root.val;
        }
        order++;
        inOrder(root.right, k);
    }

    long preVal = Long.MIN_VALUE;

    public boolean isValidBST(TreeNode root) {

        if (root == null) {
            return true;
        }

        if (!isValidBST(root.left)) {
            return false;
        }
        if (preVal != -1 && root.val <= preVal) {
            return false;
        }
        preVal = root.val;
        return isValidBST(root.right);
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


    public int[] distributeCandies(int candies, int num_people) {
        int[] candiesNums = new int[num_people];
        int count = 0;
        while (candies > 0) {

            if (candies >= count + 1) {
                candiesNums[count % num_people] += count + 1;
                candies -= count + 1;
            } else {
                candiesNums[count % num_people] += candies;
                candies = 0;
            }
            count++;

        }
        return candiesNums;

    }


    public int numIslands(char[][] grid) {

        int m = grid.length;
        if (m == 0) {
            return 0;
        }
        int n = grid[0].length;
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfs(grid, i, j);
                }
            }
        }

        return count;
    }

//    private void dfs(char[][] grid, int i, int j) {
//        int m = grid.length;
//        int n = grid[0].length;
//
//        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] != '1') {
//            return;
//        }
//
//        grid[i][j] = '2';
//
//        dfs(grid, i + 1, j);
//        dfs(grid, i - 1, j);
//        dfs(grid, i, j + 1);
//        dfs(grid, i, j - 1);
//    }


    public void solve(char[][] board) {

        int m = board.length;
        int n = board[0].length;

        for (int i = 0; i < m; i++) {
            dfs(board, i, 0);
            dfs(board, i, n - 1);
        }

        for (int j = 0; j < n; j++) {
            dfs(board, 0, j);
            dfs(board, m - 1, j);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'B') {
                    board[i][j] = 'O';
                } else {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private void dfs(char[][] grid, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;

        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] != 'O') {
            return;
        }

        grid[i][j] = 'B';

        dfs(grid, i + 1, j);
        dfs(grid, i - 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i, j - 1);
    }


    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        ArrayDeque<TreeNode> queue = new ArrayDeque<TreeNode>();

        if (root != null) {
            queue.offer(root);
        }
        boolean flag = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (flag) {
                    list.add(node.val);
                } else {
                    list.add(0, node.val);
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            flag = !flag;
            res.add(list);
        }

        return res;
    }


    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        for (int first = 0; first < n; first++) {
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }

            int third = n - 1;
            int target = -nums[first];

            for (int second = first + 1; second < n; second++) {
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }

                while (second < third && nums[second] + nums[third] > target) {
                    third--;
                }

                if (second == third) {
                    break;
                }

                if (nums[second] + nums[third] == target) {

                    List<Integer> list = new ArrayList<>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    ans.add(list);
                }

            }
        }
        return ans;
    }


    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;

        if (matrix[0][0] > target || matrix[m - 1][n - 1] < target) {
            return false;
        }
        int targetRow = 0;
        for (int i = 1; i < m; i++) {
            if (matrix[i][0] <= target && matrix[i][n - 1] >= target) {
                targetRow = i;
                break;
            }
        }

        for (int j = 0; j < n; j++) {
            if (matrix[targetRow][j] == target) {
                return true;
            }
        }

        return false;

    }


    public TreeNode sortedArrayToBST(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }

    public TreeNode helper(int[] nums, int start, int end) {

        if (start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = helper(nums, start, mid - 1);
        root.right = helper(nums, mid + 1, end);
        return root;
    }

    public void rotate(int[][] matrix) {

        int n = matrix.length;
        for (int i = 0; i < n / 2; ++i) {
            for (int j = 0; j < (n + 1) / 2; ++j) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = temp;
            }
        }
    }


    public int maxOperations(int[] nums) {
        int n = nums.length;
        int ans = 0;
        int num = -1;
        for (int i = 0; i < n - 1; i = i + 2) {
            if (i == 0) {
                num = nums[i] + nums[i + 1];
                ans++;
            } else if (nums[i] + nums[i + 1] == num) {
                ans++;
            } else {
                break;
            }
        }

        return ans;
    }


    public int[] searchRange(int[] nums, int target) {
        int n = nums.length;
        int l = 0;
        int r = n - 1;
        int start = -1;
        int end = -1;
        while (l <= r) {
            int mid = (l + r) / 2;

            if (nums[mid] == target) {
                start = mid;
                r = mid - 1;
            } else if (nums[mid] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }


        l = 0;
        r = n - 1;
        while (l <= r) {

            int mid = (l + r) / 2;

            if (nums[mid] == target) {
                end = mid;
                l = mid + 1;
            } else if (nums[mid] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        return new int[]{start, end};

    }


    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] dp = new int[n][n];

        dp[0][0] = triangle.get(0).get(0);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0) {
                    dp[i][j] = triangle.get(i).get(j) + dp[i - 1][j];
                } else if (j == i) {
                    dp[i][j] = triangle.get(i).get(j) + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = triangle.get(i).get(j) + Math.min(dp[i - 1][j - 1], dp[i - 1][j]);
                }
            }
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, dp[n - 1][i]);
        }

        return ans;

    }

    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        boolean[] row = new boolean[m];
        boolean[] col = new boolean[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = true;
                    col[j] = true;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (row[i] || col[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }


    public int mySqrt(int x) {
        int l = 0;
        int r = x;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            if ((long) mid * mid == x) {
                return mid;
            } else if ((long) mid * mid > x) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        return r;
    }


    public int minPathSum1(int[][] grid) {
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

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) {
                    dp[j] = grid[0][0];
                    continue;
                }

                if (i == 0) {
                    dp[j] = dp[j - 1] + grid[i][j];
                    continue;
                }

                if (j == 0) {
                    dp[j] = dp[j] + grid[i][j];
                } else {
                    dp[j] = Math.min(dp[j], dp[j - 1]) + grid[i][j];
                }
            }

        }

        return dp[n - 1];
    }


    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        if (obstacleGrid[0][0] == 1 || obstacleGrid[m - 1][n - 1] == 1) {
            return 0;
        }

        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        for (int i = 1; i < m; i++) {
            if (dp[i - 1][0] == 0 || obstacleGrid[i][0] == 1) {
                dp[i][0] = 0;
            } else {
                dp[i][0] = 1;
            }
        }

        for (int j = 1; j < n; j++) {
            if (dp[0][j - 1] == 0 || obstacleGrid[0][j] == 1) {
                dp[0][j] = 0;
            } else {
                dp[0][j] = 1;
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m - 1][n - 1];
    }


    public int singleNumber(int[] nums) {

        int ans = 0;
        for (int num : nums) {
            ans ^= num;
        }
        return ans;
    }


    public int accountBalanceAfterPurchase(int purchaseAmount) {
        int i = purchaseAmount % 10;
        if (i != 0) {
            if (i >= 5) {
                return 100 - (purchaseAmount + (10 - i));
            } else {
                return 100 - (purchaseAmount - i);
            }
        } else {
            return 100 - purchaseAmount;
        }

    }

    public List<Integer> findSubstring(String s, String[] words) {
        for (int l = 0; l < s.length(); l++) {
            int r = l;
            char c = s.charAt(l);
        }

        return null;
    }


    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }

        int temp = x;
        int y = 0;
        while (temp > 0) {
            y = y * 10 + temp % 10;
            temp = temp / 10;
        }

        return y == x;
    }


    public boolean isInterleave(String s1, String s2, String s3) {
        int l = s1.length();
        int m = s2.length();
        int n = s3.length();
        if (l + m != n) {
            return false;
        }

        boolean[][] dp = new boolean[l + 1][m + 1];
        dp[0][0] = true;
        for (int i = 0; i <= l; i++) {
            for (int j = 0; j <= m; j++) {
                int k = i + j - 1;

                if (i > 0) {
                    dp[i][j] = dp[i - 1][j] && s1.charAt(i) == s3.charAt(k);
                }

                if (j > 0) {
                    dp[i][j] = dp[i][j] || dp[i][j - 1] && s2.charAt(j) == s3.charAt(k);
                }
            }
        }
        return dp[l][m];

    }

    public List<Integer> spiralOrder(int[][] matrix) {

        List<Integer> list = new ArrayList<>();

        int t = 0;
        int b = matrix.length - 1;
        int l = 0;
        int r = matrix[0].length - 1;

        while (l <= r && t <= b) {
            //向右
            if (l <= r) {
                for (int i = l; i <= r; i++) {
                    list.add(matrix[t][i]);
                }
                t++;
            } else {
                break;
            }

            //向下
            if (t <= b) {
                for (int i = t; i <= b; i++) {
                    list.add(matrix[i][r]);
                }
                r--;
            } else {
                break;
            }

            //向左
            if (l <= r) {
                for (int i = r; i >= l; i--) {
                    list.add(matrix[b][i]);
                }
                b--;
            } else {
                break;
            }

            //向上
            if (t <= b) {
                for (int i = b; i >= t; i--) {
                    list.add(matrix[i][l]);
                }
                l++;
            } else {
                break;
            }
        }
        return list;
    }


    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new LinkedList<>();

        for (int i = 0; i < tokens.length; i++) {
            if ("+".equals(tokens[i])) {
                stack.push(stack.pop() + stack.pop());
            } else if ("-".equals(tokens[i])) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a - b);
            } else if ("*".equals(tokens[i])) {
                stack.push(stack.pop() * stack.pop());
            } else if ("/".equals(tokens[i])) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a / b);
            } else {
                stack.push(Integer.valueOf(tokens[i]));
            }
        }
        return stack.pop();
    }


    public boolean exist(char[][] board, String word) {

        int m = board.length;
        int n = board[0].length;

        //为了防止重复遍历相同的位置，需要额外维护一个与 board\textit{board}board 等大的 visited\textit{visited}visited 数组，用于标识每个位置是否被访问过。每次遍历相邻位置时，需要跳过已经被访问的位置。
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (check(board, visited, word, i, j, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean check(char[][] board, boolean[][] visited, String word, int i, int j, int index) {

        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
            return false;
        }

        //已经访问过
        if (visited[i][j]) {
            return false;
        }

        //标志已经访问过
        visited[i][j] = true;

        boolean flag = false;
        if (board[i][j] == word.charAt(index)) {
            if (index == word.length() - 1) {
                flag = true;
            } else {
                flag = check(board, visited, word, i - 1, j, index + 1)
                        || check(board, visited, word, i + 1, j, index + 1)
                        || check(board, visited, word, i, j - 1, index + 1)
                        || check(board, visited, word, i, j + 1, index + 1);
            }
        } else {
            flag = false;
        }
        //回溯回来的时候，重置访问状态
        visited[i][j] = false;
        return flag;
    }

    public int[] plusOne(int[] digits) {

        boolean flag = false;
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] == 9) {
                flag = true;
                digits[i] = 0;
            } else {
                digits[i]++;
                flag = false;
                break;
            }
        }

        if (flag) {
            int[] res = new int[digits.length + 1];
            res[0] = 1;
            return res;
        } else {
            return digits;
        }
    }


    public int findMinArrowShots(int[][] points) {

        //按气球右边界排序
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] < o2[1]) {
                    return -1;
                } else if (o1[1] > o2[1]) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        //记录箭数，并初始化第一支箭
        int count = 1;

        //在第一只气球的右边界
        int minRight = points[0][1];
        for (int i = 1; i < points.length; i++) {
            if (minRight < points[i][0]) {
                //如果当前气球的左边界大于箭的最右边界，则需要一支新的箭
                count++;
                //更新新的箭，箭的右边界为当前气球的右边界
                minRight = points[i][1];
            }
        }
        return count;
    }


    public String addBinary(String a, String b) {

        int sum = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0; i--, j--) {
            sum += (i >= 0 ? a.charAt(i) - '0' : 0);
            sum += (j >= 0 ? b.charAt(j) - '0' : 0);
            sb.append(sum % 2);
            sum = sum / 2;
        }
        if (sum == 1) {
            sb.append(sum);
        }

        return sb.reverse().toString();

    }


}
