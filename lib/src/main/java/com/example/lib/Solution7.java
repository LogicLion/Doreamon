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
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * @author wzh
 * @date 2024/6/14
 */
class Solution7 {

    public int hammingWeight(int n) {
        return Integer.bitCount(n);

    }

    public String simplifyPath(String path) {

        Deque<String> stack = new ArrayDeque<>();

        String[] dirs = path.split("/");
        for (String dir : dirs) {
            if (dir.equals("..") && dir.length() > 0) {
                stack.pollLast();
            } else if (!dir.equals(".")) {
                stack.offerLast(dir);
            }
        }

        StringBuilder sb = new StringBuilder();
        if (stack.isEmpty()) {
            sb.append("/");
        } else {
            while (!stack.isEmpty()) {
                sb.append("/");
                sb.append(stack.pollFirst());
            }
        }
        return sb.toString();
    }

    public int findKthLargest(int[] nums, int k) {

        int n = nums.length - 1;
        //实际找的是数组n-k的位置
        return quickSort(nums, 0, n - 1, n - k);
    }


    private int quickSort(int[] nums, int l, int r, int k) {
        if (l == r) {
            //当l和r重合是，数组中的k就是结果
            return nums[k];
        }
        //基准值
        int x = nums[l];

        int i = l - 1, j = r + 1;
        while (i < j) {

            i++;
            j--;
//            //双指针，左边往右一直找，直到停在大于等于基准值
            while (nums[i] < x) {
                i++;
            }

            //右边往左一直找，直到小于等于基准值
            while (nums[j] > x) {
                j--;
            }

//            do i++; while (nums[i] < x);
//            do j--; while (nums[j] > x);


            if (i < j) {
                //调换位置，让左边小于等于基准值，右边大于等于基准值
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }


        if (k <= j) {
            //说明当前有j个数大于选择的基准值，但是我们要找的要求是第k大，j比k大，k在[l,j]之中，所以只需对[l,j]排序即可
            return quickSort(nums, l, j, k);
        } else {
            //说明当前有j个数小于选择的基准值，比要求的k小了，所以在右边找，k在[j+1,r]之中
            return quickSort(nums, j + 1, r, k);
        }

    }


    public int temperatureTrend(int[] temperatureA, int[] temperatureB) {
        int m = temperatureA.length;
        int n = temperatureB.length;
        int[] a = new int[m - 1];
        int[] b = new int[n - 1];

        for (int i = 0; i < m - 1; i++) {
            if (temperatureA[i + 1] == temperatureA[i]) {
                a[i] = 0;
            } else if (temperatureA[i + 1] > temperatureA[i]) {
                a[i] = 1;
            } else {
                a[i] = -1;
            }
        }
        for (int i = 0; i < n - 1; i++) {
            if (temperatureB[i + 1] == temperatureB[i]) {
                b[i] = 0;
            } else if (temperatureB[i + 1] > temperatureB[i]) {
                b[i] = 1;
            } else {
                b[i] = -1;
            }
        }

        int ans = 0;
        for (int i = 0; i < m - 1; i++) {
            int j = i;
            while (j < m - 1 && a[j] == b[j]) {
                j++;
            }
            ans = Math.max(ans, j - i);
        }
        return ans;
    }


    /**
     * 冒泡排序及优化
     *
     * @param array
     */
    public void popupSort(int array[]) {
        int sortCount = 0;

        //记录最后一次交换的位置
        int lastExchangeIndex = 0;

        //无序数列的边界，每次比较只需要比到这里为止
        int sortBorder = array.length - 1;

        for (int i = 0; i < array.length - 1; i++) {
            //有序标记，每一轮的初始值都是true
            boolean isSorted = true;
            for (int j = 0; j < sortBorder; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    isSorted = false;
                    lastExchangeIndex = j;
                }
                sortCount++;
            }

            sortBorder = lastExchangeIndex;

            //若已经有序，提前退出冒泡排序
            if (isSorted) {
                break;
            }
        }

        System.out.println("比较次数：" + sortCount);
    }


    public List<String> findWords(char[][] board, String[] words) {

        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }

        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        Set res = new HashSet<String>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(board, visited, trie, i, j, res);
            }
        }

        return new ArrayList<>(res);
    }

    public void dfs(char[][] board, boolean[][] visited, Trie trie, int x, int y, Set res) {
        if (x < 0 || y < 0 || x >= board.length || y >= board[0].length) {
            return;
        }

        if (visited[x][y]) {
            return;
        }
        char c = board[x][y];
        int index = c - 'a';
        Trie curr = trie.children[index];
        if (curr == null) {
            return;
        }

        visited[x][y] = true;

        if (!"".equals(curr.word)) {
            res.add(curr.word);
        }

        dfs(board, visited, curr, x + 1, y, res);
        dfs(board, visited, curr, x - 1, y, res);
        dfs(board, visited, curr, x, y + 1, res);
        dfs(board, visited, curr, x, y - 1, res);
        visited[x][y] = false;
    }


    public String smallestString(String s) {

        boolean isPlaced = false;
        char[] charArray = s.toCharArray();
        int n = charArray.length;
        for (int i = 0; i < n; i++) {

            if (i != n - 1 && !isPlaced && charArray[i] == 'a') {
                continue;
            }

            if (i == n - 1 && !isPlaced && charArray[i] == 'a') {
                charArray[i] = 'z';
                break;
            }
            if (charArray[i] == 'a' && isPlaced) {
                break;
            }
            if (charArray[i] != 'a') {
                isPlaced = true;
                charArray[i]--;
            }

        }

        return new String(charArray);
    }


    public boolean isValidSudoku(char[][] board) {

        boolean row[][] = new boolean[9][10];
        boolean col[][] = new boolean[9][10];
        boolean box[][] = new boolean[9][10];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if (board[i][j] != '.') {
                    int num = board[i][j] - '0';
                    int boxIndex = (i / 3) * 3 + j / 3;

                    if (row[i][num] || col[j][num] || box[boxIndex][num]) {
                        return false;
                    }

                    row[i][num] = true;
                    col[j][num] = true;
                    box[boxIndex][num] = true;
                }
            }
        }

        return true;

    }


    public boolean wordBreak(String s, List<String> wordDict) {

        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int i = 0; i < n; i++) {
            if (!dp[i]) {
                continue;
            }
            for (String word : wordDict) {
                if (i + word.length() <= n && dp[i] && s.startsWith(word, i)) {
                    dp[i + word.length()] = true;
                }
            }
        }

        return dp[n];
    }

    public int maximalSquare(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        int[][] dp = new int[n][m];
        int maxMatrix = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                    }

                    maxMatrix = Math.max(maxMatrix, dp[i][j]);
                }
            }
        }

        return maxMatrix * maxMatrix;
    }


    public void gameOfLife(int[][] board) {

        int[] neighbors = {-1, 0, 1};
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                int liveNeighbors = 0;
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        int x = i + neighbors[k];
                        int y = j + neighbors[l];

                        if ((x != i || y != j) && x >= 0 && y >= 0 && x < m && y < n && (board[x][y] == 1 || board[x][y] == -1)) {
                            liveNeighbors++;
                        }
                    }
                }

                // 规则 1 或规则 3
                if ((board[i][j] == 1) && (liveNeighbors < 2 || liveNeighbors > 3)) {
                    // -1 代表这个细胞过去是活的现在死了
                    board[i][j] = -1;
                }
                // 规则 4
                if (board[i][j] == 0 && liveNeighbors == 3) {
                    // 2 代表这个细胞过去是死的现在活了
                    board[i][j] = 2;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == -1) {
                    board[i][j] = 0;
                }

                if (board[i][j] == 2) {
                    board[i][j] = 1;
                }
            }
        }

    }


    public List<List<Integer>> combine(int n, int k) {

        List<List<Integer>> res = new ArrayList<>();
        LinkedList<Integer> combine = new LinkedList<>();
        dfs(n, k, 1, combine, res);
        return res;
    }

    private void dfs(int n, int k, int startIndex, LinkedList<Integer> combine, List<List<Integer>> res) {
        if (combine.size() == k) {
            res.add(new ArrayList<>(combine));
            return;
        }

        for (int j = startIndex; j <= n; j++) {
            combine.add(j);
            dfs(n, k, startIndex + 1, combine, res);
            combine.removeLast();
        }
    }


    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();

        combine(candidates, target, 0, new LinkedList<Integer>(), res);
        return res;
    }

    private void combine(int[] candidates, int target, int startIndex, LinkedList<Integer> es, List<List<Integer>> res) {
        if (target < 0) {
            return;
        }

        if (target == 0) {
            res.add(new ArrayList<>(es));
            return;
        }

        for (int j = startIndex; j < candidates.length; j++) {
            if (candidates[j] > target) {
                continue;
            }
            es.add(candidates[j]);
            combine(candidates, target - candidates[j], j, es, res);
            es.removeLast();
        }

    }


    public int sumOfTheDigitsOfHarshadNumber(int x) {
        int temp = x;
        int sum = 0;
        while (temp > 0) {
            sum += temp % 10;
            temp = temp / 10;
        }

        return x % sum == 0 ? sum : -1;
    }


    public List<List<Integer>> permute(int[] nums) {
        int len = nums.length;

        List<List<Integer>> res = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();

        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }

        dfs(list, 0, path, res);
        return res;
    }

    private void dfs(List<Integer> nums, int startIndex, Deque<Integer> path, List<List<Integer>> res) {
        if (path.size() == nums.size()) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = startIndex; i < nums.size(); i++) {

            path.addLast(nums.get(i));
            Collections.swap(nums, startIndex, i);
            System.out.println("  递归之前 => " + path);

            dfs(nums, startIndex + 1, path, res);
            Collections.swap(nums, startIndex, i);
            path.removeLast();

            System.out.println("递归之后 => " + path);

        }
    }


    public int trailingZeroes(int n) {
        int res = 0;

        while (n > 0) {
            res += n / 5;
            n /= 5;
        }

        return res;

    }


    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<String>();
        backtrack(ans, new StringBuilder(), 0, 0, n);
        return ans;
    }

    public void backtrack(List<String> ans, StringBuilder cur, int open, int close, int max) {
        if (cur.length() == max * 2) {
            //括号添加满了
            ans.add(cur.toString());
            return;
        }

        //下面是双重递归，可以理解为产生了递归树图结构的左右分支
        if (open < max) {
            //只要左括号没满，就可以添加左括号，可以理解为产生了左分支
            cur.append('(');
            System.out.println("递归之前'(',open:" + open + ",close:" + close + " => " + cur.toString());
            backtrack(ans, cur, open + 1, close, max);
            cur.deleteCharAt(cur.length() - 1);
            System.out.println("递归之后'(',open:" + open + ",close:" + close + "  => " + cur.toString());

            //最后是在“（”都移除完后，在这里递归结束
        }
        if (close < open) {
            //而右括号的添加除了需满足上面的条件，还需满足右括号个数不能大于左括号个数，可以理解为产生了右分支
            cur.append(')');
            System.out.println("递归之前')' ,open:" + open + ",close:" + close + " => " + cur.toString());
            backtrack(ans, cur, open, close + 1, max);
            cur.deleteCharAt(cur.length() - 1);
            System.out.println("递归之后')' ,open:" + open + ",close:" + close + " => " + cur.toString());
        }
    }

    public double myPow(double x, int n) {
        long N = n;
        if (N < 0) {
            return 1.0d / multi(x, -N);
        } else {
            return multi(x, N);
        }
    }

    public double multi(double x, long n) {
        if (n == 0) {
            return 1;
        }

        // 贡献的初始值为 x
        double x_contribute = x;
        while (n > 0) {
            if (n % 2 == 1) {
                x_contribute *= (x_contribute * x);
            } else {
                x_contribute *= x_contribute;
            }
            n /= 2;
        }

        return x_contribute;
    }


//    public int[][] modifiedMatrix(int[][] matrix) {
//
//        int m = matrix.length;
//        int n = matrix[0].length;
//
//        int[][] copyMatrix = new int[m][n];
//
//        int[] maxColumn = new int[n];
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                copyMatrix[i][j] = matrix[i][j];
//                maxColumn[j] = Math.max(maxColumn[j], matrix[i][j]);
//            }
//        }
//
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                if(copyMatrix[i][j] == -1)
//                copyMatrix[i][j] = maxColumn[j];
//            }
//        }
//
//        return copyMatrix;
//    }


    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }

        Map<Node, Node> map = new HashMap<>();
        return dfs(node, map);
    }

    private Node dfs(Node node, Map<Node, Node> map) {

        Node graphNode = map.get(node);
        if (graphNode == null) {
            graphNode = new Node(node.val);
            map.put(node, graphNode);
            for (Node neighbor : node.neighbors) {
                graphNode.neighbors.add(dfs(neighbor, map));
            }

        }
        return graphNode;
    }


    public int coinChange(int[] coins, int amount) {

        //这里的dp表示凑成当前硬币数所需要的最少次数
        int[] dp = new int[amount + 1];
        //amount为0，次数为0
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            dp[i] = -1;
        }

        for (int i = 1; i <= amount; i++) {
            int min = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (i - coin < 0) {
                    //当前需要的硬币数量小于当前硬币值，跳过
                    continue;
                }
                if (i - coin == 0) {
                    //当前需要的硬币数量等于当前硬币值，说明需要的最少次数为1
                    dp[i] = 1;
                    break;
                }

                //说明能通过加上coin凑成当前硬币数所需要的隐蔽数量
                if (dp[i - coin] != -1) {
                    //计算最少次数
                    min = Math.min(min, dp[i - coin] + 1);
                    dp[i] = min;
                }
            }
        }
//        System.out.println(Arrays.toString(dp));
        return dp[amount];

    }


    public List<Integer> findAnagrams(String s, String p) {
        int sLen = s.length(), pLen = p.length();

        if (sLen < pLen) {
            return new ArrayList<Integer>();
        }

        List<Integer> ans = new ArrayList<Integer>();
        int[] count = new int[26];
        //初始滑动窗口[0,pLen-1]
        for (int i = 0; i < pLen; ++i) {
            ++count[s.charAt(i) - 'a'];
            --count[p.charAt(i) - 'a'];
        }

        System.out.println("第一次比较后：" + Arrays.toString(count));
        int differ = 0;
        //统计[0,pLen-1]之间的不同字母个数
        for (int j = 0; j < 26; ++j) {
            if (count[j] != 0) {
                ++differ;
            }
        }

        if (differ == 0) {
            ans.add(0);
        }

        for (int i = 0; i < sLen - pLen; ++i) {
            //将移除i位置的字符数统计
            if (count[s.charAt(i) - 'a'] == 1) {  // 窗口中字母 s[i] 的数量与字符串 p 中的数量从不同变得相同
                --differ;
            } else if (count[s.charAt(i) - 'a'] == 0) {  // 窗口中字母 s[i] 的数量与字符串 p 中的数量从相同变得不同
                ++differ;
            }
            //i位置的字符数统计-1
            --count[s.charAt(i) - 'a'];
//            System.out.println("第" + i + "次滑动窗口移除前一个字符：" + Arrays.toString(count));

            //即将增加i+pLen位置的字符数统计
            if (count[s.charAt(i + pLen) - 'a'] == -1) {  // 窗口中字母 s[i+pLen] 的数量与字符串 p 中的数量从不同变得相同
                --differ;
            } else if (count[s.charAt(i + pLen) - 'a'] == 0) {  // 窗口中字母 s[i+pLen] 的数量与字符串 p 中的数量从相同变得不同
                ++differ;
            }
            //i+pLen位置的字符数统计+1
            ++count[s.charAt(i + pLen) - 'a'];
//            System.out.println("第" + i + "次滑动窗口增加后一个字符：" + Arrays.toString(count));

            if (differ == 0) {
                ans.add(i + 1);
            }
        }

        return ans;

    }


    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        TreeNode node = buildTreeHelper(map, preorder, inorder, 0, preorder.length - 1, 0, preorder.length - 1);

        return node;
    }

    public TreeNode buildTreeHelper(Map<Integer, Integer> map, int[] preorder, int[] inorder, int preStart, int preEnd, int inStart, int inEnd) {

        if (preStart > preEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[preStart]);
        int rootIndex = map.get(root.val);
        int preNum = rootIndex - inStart;
        root.left = buildTreeHelper(map, preorder, inorder, preStart + 1, preStart + preNum, inStart, rootIndex - 1);
        root.right = buildTreeHelper(map, preorder, inorder, preStart + preNum + 1, preEnd, rootIndex + 1, inEnd);
        return root;
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {

        //入度数组(入度即是指向该结点的结点数量)
        int[] inDegree = new int[numCourses];

        //邻接表(统计每个结点的后继结点有哪些)
        HashSet<Integer>[] adj = new HashSet[numCourses];

        for (int i = 0; i < numCourses; i++) {
            //初始化邻接表
            adj[i] = new HashSet<>();
        }

        for (int[] prerequisite : prerequisites) {
            //统计每个结点的后继结点有哪些
            adj[prerequisite[1]].add(prerequisite[0]);
            //统计每个结点的入度
            inDegree[prerequisite[0]]++;
        }


        //队列，广度优先遍历
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < numCourses; i++) {

            if (inDegree[i] == 0) {
                //将入度为0的结点加入队列
                queue.offer(i);
            }
        }

        // 当前结果集列表里的元素个数，也正好可以作为下标
        int count = 0;
        int[] res = new int[numCourses];

        while (!queue.isEmpty()) {
            Integer cur = queue.poll();
            res[count] = cur;
            count++;
            for (Integer next : adj[cur]) {
                //将后继结点的入度减一
                inDegree[next]--;
                if (inDegree[next] == 0) {
                    //如果入度为0的，马上加入队列
                    queue.offer(next);
                }
            }
        }

        if (count == numCourses) {
            // 如果结果集中的数量不等于结点的数量，就不能完成课程任务，这一点是拓扑排序的结论
            return res;
        }

        return new int[0];


    }


    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //课程表二的算法改个返回值

        //入度数组(入度即是指向该结点的结点数量)
        int[] inDegree = new int[numCourses];

        //邻接表(统计每个结点的后继结点有哪些)
        HashSet<Integer>[] adj = new HashSet[numCourses];

        for (int i = 0; i < numCourses; i++) {
            //初始化邻接表
            adj[i] = new HashSet<>();
        }

        for (int[] prerequisite : prerequisites) {
            //统计每个结点的后继结点有哪些
            adj[prerequisite[1]].add(prerequisite[0]);
            //统计每个结点的入度
            inDegree[prerequisite[0]]++;
        }

        //队列，广度优先遍历
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < numCourses; i++) {

            if (inDegree[i] == 0) {
                //将入度为0的结点加入队列
                queue.offer(i);
            }
        }

        // 当前结果集列表里的元素个数，也正好可以作为下标
        int count = 0;
        int[] res = new int[numCourses];

        while (!queue.isEmpty()) {
            Integer cur = queue.poll();
            res[count] = cur;
            count++;
            for (Integer next : adj[cur]) {
                //将后继结点的入度减一
                inDegree[next]--;
                if (inDegree[next] == 0) {
                    //如果入度为0的，马上加入队列
                    queue.offer(next);
                }
            }
        }

        if (count == numCourses) {
            // 如果结果集中的数量不等于结点的数量，就不能完成课程任务，这一点是拓扑排序的结论
            return true;
        }

        return false;
    }


    public int[] numberGame(int[] nums) {

        int[] res = new int[nums.length];
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i = i + 2) {
            res[i] = nums[i + 1];
            res[i + 1] = nums[i];
        }
        return res;
    }

    public int maxSubarraySumCircular(int[] nums) {

        //最大的环形子数组和 = max(最大子数组和，数组总和-最小子数组和)
        int n = nums.length;

        int maxSum = nums[0];
        int minSum = nums[0];
        int allSum = 0;

        int maxDp = 0;
        int minDp = 0;

        for (int i = 0; i < n; i++) {
            maxDp = Math.max(maxDp + nums[i], nums[i]);
            minDp = Math.min(minDp + nums[i], nums[i]);
            maxSum = Math.max(maxSum, maxDp);
            minSum = Math.min(minSum, minDp);
            allSum += nums[i];
        }

        //极端情况：如果说这数组的所有数都是负数，那么上面的公式还需要变一下，因为这种情况，对于上面的第一种情况sum会等于数组中的最大值，
        // 而对二种情况sum=0（最小的子数组就是本数组，total-total=0）。所以多加一个case，判断最大子数组和是否小于0，小于0，直接返回该maxSubArray
        return maxSum > 0 ? Math.max(maxSum, allSum - minSum) : maxSum;

    }


    public int minMutation(String startGene, String endGene, String[] bank) {

        if (startGene.equals(endGene)) {
            return 0;
        }
        char[] keys = {'A', 'C', 'G', 'T'};

        Set<String> bankSet = new HashSet<>();
        Set<String> visited = new HashSet<>();
        for (String s : bank) {
            bankSet.add(s);
        }

        if (!bankSet.contains(endGene)) {
            return -1;
        }

        bankSet.add(startGene);
        visited.add(startGene);

        Queue<String> queue = new LinkedList<>();
        queue.offer(startGene);

        int step = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 4; k++) {
                        if (curr.charAt(j) != keys[k]) {
                            StringBuilder sb = new StringBuilder(curr);
                            sb.setCharAt(j, keys[k]);

                            if (bankSet.contains(sb.toString()) && !visited.contains(sb.toString())) {

                                if (sb.toString().equals(endGene)) {
                                    return step;
                                }
                                queue.offer(sb.toString());
                                visited.add(sb.toString());

                            }

                        }
                    }
                }
            }
            step++;
        }

        return -1;
    }

    public int[] findIntersectionValues(int[] nums1, int[] nums2) {

        Map<Integer, Integer> map1 = new HashMap<>();
        Map<Integer, Integer> map2 = new HashMap<>();

        for (int num : nums1) {
            map1.put(num, map1.getOrDefault(num, 0) + 1);
        }

        for (int num : nums2) {
            map2.put(num, map2.getOrDefault(num, 0) + 1);
        }

        int[] res = new int[2];

        for (int num : map1.keySet()) {
            if (map2.containsKey(num)) {
                res[1] += map2.get(num);
            }
        }

        for (int num : map2.keySet()) {
            if (map1.containsKey(num)) {
                res[0] += map1.get(num);
            }
        }
        return res;

    }

    public int search(int[] nums, int target) {
        return searchTarget(nums, target, 0, nums.length - 1);
    }

    private int searchTarget(int[] nums, int target, int l, int r) {

        if (l > r) {
            return -1;
        }

        int mid = (l + r) / 2;
        System.out.println("mid:" + mid);

        if (nums[mid] == target) {
            return mid;
        } else if (nums[l] <= nums[mid]) {
            //左半边从小到大有序
            if (nums[mid] > target && target >= nums[l]) {
                //并且target在左半边
                System.out.println("搜" + (l) + "到" + (mid - 1));
                return searchTarget(nums, target, l, mid - 1);
            } else {
                System.out.println("搜" + (mid + 1) + "到" + (r));
                return searchTarget(nums, target, mid + 1, r);
            }
        } else {
            //右半边从小到大有序
            if (nums[mid] < target && target <= nums[r]) {
                //并且target在右半边
                System.out.println("搜" + (mid + 1) + "到" + (r));
                return searchTarget(nums, target, mid + 1, r);
            } else {
                System.out.println("搜" + (l) + "到" + (mid - 1));
                return searchTarget(nums, target, l, mid - 1);
            }
        }

    }

    public int findMin(int[] nums) {

        int l = 0;
        int r = nums.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] < nums[r]) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        return nums[l];

    }


    public ListNode sortList(ListNode head) {
        if (head == null) {
            return head;
        }
        int lenght = 0;
        ListNode node = head;

        while (node != null) {
            lenght++;
            node = node.next;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        for (int subLen = 1; subLen < lenght; subLen <<= 1) {
            ListNode pre = dummy;
            ListNode curr = dummy.next;//curr用于记录拆分链表的位置
            while (curr != null) {
                //拆分subLen长度的链表1
                ListNode node1 = curr;
                for (int i = 1; i < subLen && curr != null && curr.next != null; i++) {
                    curr = curr.next;
                }

                //拆分subLen长度的链表2
                //第二个链表的头  即 链表1尾部的下一个位置
                ListNode node2 = curr.next;
                //断开第一个链表和第二个链表的连接
                curr.next = null;

                // 第二个链表头 重新赋值给curr
                curr = node2;

                for (int i = 1; i < subLen && curr != null && curr.next != null; i++) {
                    curr = curr.next;
                }

                ListNode next = null;
                if (curr != null) {
                    //先记录链表2尾部的下一个位置
                    next = curr.next;
                    //断开第二个链表的连接
                    curr.next = null;
                }

                //将拆出来的node1和node2进行排序和合并
                ListNode merge = merge(node1, node2);

                //将排序好的链表合并到pre后面
                pre.next = merge;

                while (pre.next != null) {
                    //更新pre的位置，pre最终将指向排序好的链表的尾部，也就是下一个待排序的链表的前一个位置
                    pre = pre.next;
                }
                //更新curr的位置，进行下一轮循环
                curr = next;

            }


        }

        return dummy.next;

    }

    private ListNode merge(ListNode node1, ListNode node2) {
        if (node1 == null) {
            return node2;
        }
        if (node2 == null) {
            return node1;
        }

        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (node1 != null && node2 != null) {
            if (node1.val < node2.val) {
                curr.next = node1;
                node1 = node1.next;
            } else {
                curr.next = node2;
                node2 = node2.next;
            }

            curr = curr.next;
        }

        if (node1 == null) curr.next = node2;
        if (node2 == null) curr.next = node1;
        return dummy.next;
    }


    public List<List<String>> solveNQueens(int n) {
        //记录已放置有皇后的列
        Set<Integer> columns = new HashSet<>();
        //记录已放置有皇后的左斜对角线(行下标与列下标之差相等,记录该差)
        Set<Integer> diagonals1 = new HashSet<>();
        //记录已放置有皇后的右斜对角线(行下标与列下标之和相等，记录该和)
        Set<Integer> diagonals2 = new HashSet<>();

        //记录每行皇后所在的列（下标是行，值是列）
        int[] queens = new int[n];
        Arrays.fill(queens, -1);
        //从第0行开始递归

        List<List<String>> solutions = new ArrayList<>();
        dfs(solutions, n, 0, queens, columns, diagonals1, diagonals2);
        return solutions;
    }

    private List<List<String>> dfs(List<List<String>> solutions, int n, int row, int[] queens, Set<Integer> columns, Set<Integer> diagonals1, Set<Integer> diagonals2) {

        if (row == n) {
            List<String> board = generateBoard(queens, n);
            solutions.add(board);
        } else {
            //开始尝试每列放置皇后
            for (int column = 0; column < n; column++) {

                if (columns.contains(column)) {
                    continue;
                }

                if (diagonals1.contains(row - column)) {
                    continue;
                }

                if (diagonals2.contains(row + column)) {
                    continue;
                }

                queens[row] = column;
                columns.add(column);
                diagonals1.add(row - column);
                diagonals2.add(row + column);
                dfs(solutions, n, row + 1, queens, columns, diagonals1, diagonals2);
                queens[row] = -1;
                columns.remove(column);
                diagonals1.remove(row - column);
                diagonals2.remove(row + column);
            }
        }

        return solutions;
    }


    private List<String> generateBoard(int[] queens, int n) {
        List<String> board = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            char[] row = new char[n];
            Arrays.fill(row, '.');
            row[queens[i]] = 'Q';
            board.add(new String(row));
        }
        return board;
    }


    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(k, (o1, o2) -> {
            return nums1[o1[0]] + nums2[o1[1]] - nums1[o2[0]] - nums2[o2[1]];
        });

        int m = nums1.length;
        int n = nums2.length;
        for (int i = 0; i < Math.min(m, k); i++) {
            pq.offer(new int[]{i, 0});
        }
        List<List<Integer>> res = new ArrayList<>();
        while (res.size() < k && !pq.isEmpty()) {
            int[] poll = pq.poll();
            res.add(Arrays.asList(nums1[poll[0]], nums2[poll[1]]));
            if (poll[1] + 1 < n) {
                pq.offer(new int[]{poll[0], poll[1] + 1});
            }

        }

        return res;

    }


    public int calPoints(String[] operations) {

        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (String operation : operations) {
            if ("C".equals(operation)) {
                queue.pop();
            } else if ("D".equals(operation)) {
                queue.push(queue.peek() * 2);
            } else if ("+".equals(operation)) {
                Integer s1 = queue.pop();
                Integer s2 = queue.peek();
                int s3 = s1 + s2;
                queue.push(s1);
                queue.push(s3);
            } else {
                queue.push(Integer.valueOf(operation));
            }
        }

        int sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.pop();
        }
        return sum;
    }


    public long numberOfRightTriangles(int[][] grid) {
        //m行
        int m = grid.length;
        //n列
        int n = grid[0].length;

        //统计每行包含1的个数
        int[] row = new int[n];

        //统计每列包含1的个数
        int[] col = new int[m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    col[i] += 1;
                    row[j] += 1;
                }
            }
        }

        long res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    res += (row[j]) - 1 * (col[i] - 1);
                }
            }
        }

        return res;
    }


    public boolean isArraySpecial(int[] nums) {
        boolean flag = nums[0] % 2 == 0;
        for (int i = 1; i < nums.length; i++) {
            if (flag) {
                if (nums[i] % 2 != 0) {
                    flag = false;
                } else {
                    return false;
                }
            } else {
                if (nums[i] % 2 == 0) {
                    flag = true;
                } else {
                    return false;
                }
            }
        }

        return true;
    }


    public boolean equationsPossible(String[] equations) {

        int[] parent = new int[26];
        //一开始，每个节点的父节点都是自己
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        for (String equation : equations) {
            if (equation.charAt(1) == '=') {
                int x = equation.charAt(0) - 'a';
                int y = equation.charAt(3) - 'a';
                union(parent, x, y);
            }
        }
        for (String equation : equations) {
            if (equation.charAt(1) == '!') {
                int x = equation.charAt(0) - 'a';
                int y = equation.charAt(3) - 'a';
                if (find(parent, x) == find(parent, y)) {
                    return false;
                }
            }
        }

        return true;

    }

    private int find(int[] parent, int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    private void union(int[] parent, int x, int y) {
        parent[find(parent, x)] = find(parent, y);
    }


    public boolean[] isArraySpecial(int[] nums, int[][] queries) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);

        for (int i = 1; i < nums.length; i++) {
            if (((nums[i] ^ nums[i - 1]) & 1) != 0) {
                dp[i] = dp[i - 1] + 1;
            }
        }

        boolean[] res = new boolean[queries.length];
        for (int j = 0; j < queries.length; j++) {
            int start = queries[j][0];
            int end = queries[j][1];
            res[j] = dp[end] >= (end - start + 1);
        }
        return res;
    }


    /**
     * 399. 除法求值
     *
     * @param equations
     * @param values
     * @param queries
     * @return
     */
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        int size = equations.size();

        UnionFind unionFind = new UnionFind(size);
        Map<String, Integer> map = new HashMap<>();
        int id = 0;
        for (int i = 0; i < size; i++) {
            String var1 = equations.get(i).get(0);
            String var2 = equations.get(i).get(1);
            if (!map.containsKey(var1)) {
                map.put(var1, id);
                id++;
            }
            if (!map.containsKey(var2)) {
                map.put(var2, id);
                id++;
            }
            unionFind.union(map.get(var1), map.get(var2), values[i]);
        }

        int queriesSize = queries.size();
        double[] res = new double[queriesSize];
        for (int i = 0; i < queriesSize; i++) {
            String var1 = queries.get(i).get(0);
            String var2 = queries.get(i).get(1);

            Integer id1 = map.get(var1);
            Integer id2 = map.get(var2);

            if (id1 == null || id2 == null) {
                res[i] = -1.0d;
            } else {
                res[i] = unionFind.isConnected(id1, id2);
            }


        }

        return res;
    }


}

class UnionFind {
    private int[] parent;
    private double[] weight;

    public UnionFind(int n) {
        parent = new int[n];
        weight = new double[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            weight[i] = 1.0d;
        }
    }

    public void union(int x, int y, double value) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) {
            return;
        }
        parent[rootX] = rootY;
        weight[rootX] = weight[y] * value / weight[x];
    }

    public int find(int x) {
        if (x != parent[x]) {
            int origin = parent[x];
            parent[x] = find(parent[x]);
            weight[x] *= weight[origin];
        }
        return parent[x];
    }

    public double isConnected(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) {
            return weight[x] / weight[y];
        } else {
            return -1.0d;
        }
    }
}
