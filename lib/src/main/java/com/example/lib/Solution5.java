package com.example.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * @author wzh
 * @date 2024/2/29
 */
class Solution5 {
    public boolean isHappy(int n) {

        Set<Integer> set = new HashSet<>();
        set.add(n);
        while (true) {
            n = getNext(n);

            if (n == 1) {
                return true;
            } else if (set.contains(n)) {
                return false;
            } else {
                set.add(n);
            }
        }

    }

    public int getNext(int n) {
        int total = 0;
        while (n > 0) {
            int i = n % 10;
            total += i * i;
            n = n / 10;
        }

        return total;

    }


    public int findPeakElement(int[] nums) {

        int l = 0;
        int r = nums.length - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (nums[mid] < nums[mid + 1]) {
                l = mid;
            } else {
                r = mid + 1;
            }
        }

        return l;
    }


    public int removeElement(int[] nums, int val) {
        int l = 0;
        for (int i = 0; i < nums.length; i++) {

            if (nums[i] != val) {
                nums[l] = nums[i];
                l++;
            }

        }

        return l;
    }


    public boolean validPartition(int[] nums) {
        int n = nums.length;
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int i = 1; i < n; i++) {
            if (dp[i - 1] && nums[i] == nums[i - 1]) {

            }
        }

        return false;
    }


    class MyStack {

        Queue<Integer> queue1;
        Queue<Integer> queue2;

        public MyStack() {
            queue1 = new LinkedList<>();
            queue2 = new LinkedList<>();
        }

        public void push(int x) {
            queue2.offer(x);
            while (!queue1.isEmpty()) {
                queue2.offer(queue1.poll());
            }

            Queue<Integer> temp = queue1;
            queue1 = queue2;
            queue2 = temp;


        }

        public int pop() {
            return queue1.poll();
        }

        public int top() {
            return queue1.peek();

        }

        public boolean empty() {

            return queue1.isEmpty();
        }
    }


//    public int maxProfit(int[] prices) {
//
//        int minPrice = Integer.MAX_VALUE;
//        int maxProfit = 0;
//
//        for (int i = 0; i < prices.length; i++) {
//            minPrice = Math.min(minPrice, prices[i]);
//            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
//        }
//
//        return maxProfit;
//    }

    public int maxProfit(int[] prices) {

        int lastPrice = prices[0];
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > lastPrice) {
                maxProfit += prices[i] - lastPrice;
            }
            lastPrice = prices[i];
        }

        return maxProfit;
    }


    public boolean hasCycle(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null) {
            //快指针先走1步
            fast = fast.next;

            if (fast != null) {
                //不为空再走1步
                fast = fast.next;
            }

            //判断跟慢指针是否重合
            if (fast == slow) {
                return true;
            }

            //慢指针走一步
            slow = slow.next;
        }

        return false;
    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode preHead = new ListNode();
        ListNode dummyNode = preHead;

        boolean carry = false;
        while (l1 != null || l2 != null) {
            int sum = 0;
            if (carry) {
                sum += 1;
                carry = false;
            }

            if (l1 != null) {

                int temp = sum + l1.val;
                sum = temp % 10;
                if (temp / 10 > 0) {
                    carry = true;
                }

                l1 = l1.next;
            }

            if (l2 != null) {
                int temp = sum + l2.val;
                sum = temp % 10;
                if (temp / 10 > 0) {
                    carry = true;
                }

                l2 = l2.next;
            }
            dummyNode.next = new ListNode(sum);
            dummyNode = dummyNode.next;
        }

        if (carry) {
            dummyNode.next = new ListNode(1);
        }

        return preHead.next;
    }


    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        ListNode preHead = new ListNode();
        ListNode preNode = preHead;

        while (list1 != null && list2 != null) {

            if (list1.val < list2.val) {
                preNode.next = list1;
                list1 = list1.next;
            } else {
                preNode.next = list2;
                list2 = list2.next;
            }
            preNode = preNode.next;
        }

        preNode.next = list2 == null ? list1 : list2;

        return preHead.next;
    }


    public ListNode reverseList(ListNode head) {

        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;

            prev = curr;
            curr = next;
        }

        return prev;
    }


    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummyNode = new ListNode();
        dummyNode.next = head;
        ListNode preNode = dummyNode;
        for (int i = 0; i < left - 1; i++) {
            preNode = preNode.next;
        }

        ListNode cur = preNode.next;
        for (int i = 0; i < right - left; i++) {
            ListNode next = cur.next;
            preNode.next = cur.next;
            next.next = cur;
            cur.next = next.next;
        }

        return dummyNode.next;
    }


    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode dummyNode = new ListNode();
        dummyNode.next = head;
        ListNode preNode = head;
        int lenght = 0;
        while (preNode != null) {
            preNode = preNode.next;
            lenght++;
        }

        preNode = dummyNode;
        for (int i = 0; i < lenght - n; i++) {
            preNode = preNode.next;
        }

        preNode.next = preNode.next.next;

        return dummyNode.next;
    }


    public void sort() {
        int[] a = {5, 4, 6, 8, 9};
        //使用快速排序对a数组进行从小到大排序
    }

    /**
     * 判断一个字符串是否是回文字符串。
     * 该方法会忽略字符串中的非字母和数字字符，以及忽略大小写。
     *
     * @param s 待判断的字符串。
     * @return 如果字符串是回文字符串，则返回true；否则返回false。
     */
    public boolean isPalindrome(String s) {
        int i = 0; // 定义字符串前端的指针
        int j = s.length() - 1; // 定义字符串后端的指针
        while (i < j) {
            // 如果前端和后端的字符不相等，则字符串不是回文
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++; // 移动前端指针
            j--; // 移动后端指针
        }
        return true; // 字符串遍历完毕，未发现不相等字符，是回文
    }

    /**
     * 把一个字符串中的小写字母换成大写字母
     */
    public String swapCase(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLowerCase(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
            } else if (Character.isUpperCase(chars[i])) {
                chars[i] = Character.toLowerCase(chars[i]);
            }
        }
        return new String(chars);
    }

    public String capitalizeTitle(String title) {
        StringBuilder sb = new StringBuilder();
        int l = 0;
        int r = 0;
        int n = title.length();
        while (r < n) {
            while (r < n && !Character.isWhitespace(title.charAt(r))) {
                r++;
            }

            if (r - l > 2) {
                sb.append(Character.toUpperCase(title.charAt(l)));
                l++;
            }

            while (l < r) {
                sb.append(Character.toLowerCase(title.charAt(l)));
                l++;
            }

            while (r < n && Character.isWhitespace(title.charAt(r))) {
                sb.append(" ");
                r++;
            }

            l = r;

        }

        return sb.toString();

    }


    public ListNode deleteDuplicates(ListNode head) {

        ListNode dummyNode = new ListNode();
        dummyNode.next = head;

        ListNode cur = dummyNode;
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

        return dummyNode.next;
    }


    public ListNode rotateRight(ListNode head, int k) {

        if (k == 0 || head == null || head.next == null) {
            return head;
        }

        int n = 0;
        ListNode iter = head;
        while (iter != null) {
            n++;
            iter = iter.next;
        }
        k = k % n;
        if (k == 0) {
            return head;
        }
        iter.next = head;
        int add = n - k;
        while (add > 0) {
            iter = iter.next;
            add--;
        }
        head = iter.next;
        iter.next = null;

        return head;

    }


    public int removeDuplicates(int[] nums) {

        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length <= 2) {
            return nums.length;
        }

        int slow = 2;
        for (int fast = 2; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow - 2]) {

                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow;
    }


    public int hIndex(int[] citations) {

        int n = citations.length;
        int[] counter = new int[n + 1];
        for (int i = 0; i < n; i++) {
            counter[Math.min(citations[i], n)]++;
        }

        int s = 0;
        for (int i = n; i >= 0; i--) {
            s += counter[i];
            if (s >= i) {
                return i;
            }
        }
        return 0;
    }


    public int romanToInt(String s) {

        Map<Character, Integer> map = new HashMap<Character, Integer>() {{
            put('I', 1);
            put('V', 5);
            put('X', 10);
            put('L', 50);
            put('C', 100);
            put('D', 500);
            put('M', 1000);
        }};

        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            Integer value = map.get(s.charAt(i));

            if (i + 1 < s.length() && value < map.get(s.charAt(i + 1))) {
                ans -= value;
            } else {
                ans += value;
            }
        }

        return ans;
    }


    public long maxArrayValue(int[] nums) {
        long res = nums[nums.length - 1];
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] <= res) {
                res = res + nums[i];
            } else {
                res = nums[i];
            }
        }

        return res;

    }


    public int lengthOfLastWord(String s) {
        int index = s.length() - 1;
        while (index >= 0 && s.charAt(index) == ' ') {
            index--;
        }

        int len = 0;
        while (index >= 0 && s.charAt(index) != ' ') {
            len++;
            index--;
        }

        return len;
    }


    public String longestCommonPrefix(String[] strs) {

        if (strs == null || strs.length == 0) {
            return "";
        }
        String prefix = strs[0];

        for (int i = 1; i < strs.length; i++) {
            prefix = longestCommonPrefix(prefix, strs[i]);
        }
        return prefix;
    }


    public String longestCommonPrefix(String strs1, String strs2) {
        int index = 0;
        while (index < strs1.length() && index < strs2.length()) {
            if (strs1.charAt(index) == strs2.charAt(index)) {
                index++;
            } else {
                break;
            }
        }
        return strs1.substring(0, index);
    }


    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        int end = s.length() - 1;
        int start = s.length() - 1;

        while (start >= 0) {
            while (end >= 0 && s.charAt(end) == ' ') {
                end--;
            }

            if (end < 0) {
                break;
            }

            start = end;

            while (start >= 0 && s.charAt(start) != ' ') {
                start--;
            }

            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(s.substring(start + 1, end + 1));

            end = start;
        }

        return sb.toString();
    }


    public String intToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder sb = new StringBuilder();


        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                sb.append(symbols[i]);
                num -= values[i];
            }
        }

        return sb.toString();
    }


    public int strStr(String haystack, String needle) {
        if (haystack.length() < needle.length()) {
            return -1;
        }
        for (int i = 0; i < haystack.length() - needle.length(); i++) {
            int r = 0;
            while (r < needle.length() && haystack.charAt(i + r) == needle.charAt(r)) {
                r++;

                if (r == needle.length()) {
                    return i;
                }
            }
        }

        return -1;
    }


    public List<String> summaryRanges(int[] nums) {
        int n = nums.length;
        List<String> res = new ArrayList<>();

        int l = 0;
        int r = 0;
        while (r < n) {
            while (r + 1 < n && nums[r + 1] - nums[r] == 1) {
                r++;
            }
            if (l == r) {
                res.add(nums[l] + "");
            } else {
                res.add(nums[l] + "->" + nums[r]);
            }
            l = r + 1;
            r = l;
        }

        return res;
    }


    public int[][] merge(int[][] intervals) {

        if (intervals == null || intervals.length == 0) {
            return intervals;
        }

        Arrays.sort(intervals, new Comparator<int[]>() {

            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        List<int[]> res = new ArrayList<>();

        res.add(new int[]{intervals[0][0], intervals[0][1]});
        for (int i = 1; i < intervals.length; i++) {

            int lastIndex = res.size() - 1;
            int[] last = res.get(lastIndex);
            if (intervals[i][0] <= last[1]) {
//                res.set(lastIndex, new int[]{last[0], Math.max(last[1], intervals[i][1])});
                last[1] = Math.max(last[1], intervals[i][1]);
            } else {
                res.add(new int[]{intervals[i][0], intervals[i][1]});
            }
        }

        return res.toArray(new int[res.size()][]);
    }


    public int lengthOfLongestSubstring(String s) {

        Set<Character> set = new HashSet<>();
        int start = 0, end = 0, maxLen = 0;
        while (end < s.length()) {
            if (set.contains(s.charAt(end))) {
                set.remove(s.charAt(start));
                start++;
            } else {
                set.add(s.charAt(end));
                end++;
                maxLen = Math.max(maxLen, end - start);
            }
        }

        return maxLen;
    }


    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        int[] dp = new int[n];
        dp[0] = nums[0];

        int end = 0;

        for (int i = 1; i < n; i++) {

            if (nums[i] > dp[end]) {
                end++;
                dp[end] = nums[i];
            } else {
                int left = 0;
                int right = end;
                while (left < right) {
                    int mid = (left + right) / 2;
                    if (dp[mid] < nums[i]) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }

                dp[left] = nums[i];
            }

        }

        return end + 1;
    }


//    public Node copyRandomList(Node head) {
//
//        if (head == null) {
//            return null;
//        }
//
//        //把每个节点复制一个节点，并拼接在原节点后面
//        for (Node node = head; node != null; node = node.next.next) {
//            Node newNode = new Node(node.val);
//            newNode.next = node.next;
//            node.next = newNode;
//        }
//
//        //串联起random的指向
//        for (Node node = head; node != null; node = node.next.next) {
//            node.next.random = node.random == null ? null : node.random.next;
//        }
//
//        //拆分链表
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
//
//    }


    public int trap(int[] height) {

        int n = height.length;
        if (n == 0) {
            return 0;
        }
        int leftMax = 0, rightMax = 0;
        int left = 1, right = n - 2, sum = 0;
        while (left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if (leftMax < rightMax) {
                sum += leftMax - height[left];
                left++;
            } else {
                sum += rightMax - height[right];
                right--;
            }
        }
        return sum;
    }

    public String convert(String s, int numRows) {
        if (numRows < 2) {
            return s;
        }
        StringBuilder[] sb = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            sb[i] = new StringBuilder();
        }
        int i = 0;
        int flag = -1;
        for (char c : s.toCharArray()) {
            sb[i].append(c);
            if (i == 0 || i == numRows - 1) flag = -flag;
        }

        StringBuilder res = new StringBuilder();
        for (int j = 0; j < numRows; j++) {
            res.append(sb[j]);
        }

        return res.toString();
    }


    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> ans = new ArrayList<>();
        boolean placed = false;
        int L = newInterval[0];
        int R = newInterval[1];
        for (int[] interval : intervals) {
            if (interval[0] > R) {
                //在插入区间右侧且无交集
                if (!placed) {
                    ans.add(new int[]{L, R});
                    placed = true;
                }
                ans.add(interval);
            } else if (interval[1] < L) {
                ans.add(interval);
            } else {
                L = Math.min(interval[0], L);
                R = Math.max(interval[1], R);
            }
        }

        if (!placed) {
            ans.add(new int[]{L, R});
        }

        return ans.toArray(new int[ans.size()][2]);
    }


    public TreeNode invertTree(TreeNode root) {
        if (root != null) {
            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;
            invertTree(root.left);
            invertTree(root.right);
        }

        return root;
    }


    public boolean isSymmetric(TreeNode root) {
        return isSymmetric(root.left, root.right);
    }

    private boolean isSymmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        } else if (left == null || right == null) {
            return false;
        } else {
            return left.val == right.val && isSymmetric(left.right, right.left) && isSymmetric(left.left, right.right);
        }
    }

    public int countNodes(TreeNode root) {

        if (root == null) {
            return 0;
        }
        return countNodes(root.left) + countNodes(root.right) + 1;
    }

    public Node1 connect(Node1 root) {

        if (root == null) {
            return null;
        }
        Queue<Node1> queue = new LinkedList<>();

        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();

            Node1 last = null;
            for (int i = 0; i < size; i++) {
                Node1 node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }

                if (i != 0) {
                    last.next = node;
                }
                last = node;
            }
        }

        return root;
    }


    TreeNode last = null;

    public void flatten(TreeNode root) {

        List<TreeNode> list = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();

        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                list.add(node);
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            node = node.right;
        }

        for (int i = 1; i < list.size(); i++) {
            list.get(i - 1).right = list.get(i);
            list.get(i - 1).left = null;
        }

    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        if (targetSum == root.val && root.left == null && root.right == null) {
            return true;
        }

        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }


    public int searchInsert(int[] nums, int target) {

        if (nums[0] > target) {
            return 0;
        }
        if (nums[nums.length - 1] < target) {
            return nums.length;
        }
        int l = 0;
        int r = nums.length - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        return l;


    }


    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> ans = new ArrayList<>();
        int n = words.length;

        int left = 0;
        int right = 0;
        while (right < n) {
            left = right;
            int sumLen = 0;
            while (right < n && sumLen + words[right].length() + right - left <= maxWidth) {
                sumLen += words[right].length();
                right++;
            }


            if (right == n) {
                //如果当前行是最后一行
                StringBuffer sb = join(words, left, n, " ");
                sb.append(blank(maxWidth - sumLen));
                return ans;
            }

            int wordNum = right - left;
            int numSpaces = maxWidth - sumLen;

            if (right - left == 1) {
                //如果当前行只有一个单词
                StringBuilder sb = new StringBuilder(words[left]);
                for (int i = sumLen; i < maxWidth; i++) {
                    sb.append(" ");
                }
                ans.add(sb.toString());
                continue;
            }


            int avgSpaces = numSpaces / (wordNum - 1);
            int extraSpaces = numSpaces % (wordNum - 1);
            StringBuilder sb = new StringBuilder();
            sb.append(join(words, left, left + extraSpaces + 1, blank(avgSpaces + 1)));
            sb.append(blank(avgSpaces));
            sb.append(join(words, left + extraSpaces + 1, right, blank(avgSpaces)));

            ans.add(sb.toString());
        }

        return ans;

    }

    public String blank(int n) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public StringBuffer join(String[] words, int left, int end, String sep) {
        StringBuffer sb = new StringBuffer(words[left]);
        for (int i = left + 1; i < end; i++) {
            sb.append(sep).append(words[i]);
        }
        return sb;
    }


    public ListNode partition(ListNode head, int x) {

        if (head == null) {
            return null;
        }
        ListNode small = new ListNode(-1);
        ListNode smallHead = small;
        ListNode big = new ListNode(-1);
        ListNode bigHead = big;

        while (head != null) {
            if (head.val < x) {
                small.next = head;
                small = small.next;
            } else {
                big.next = head;
                big = big.next;
            }
            head = head.next;

        }

        small.next = bigHead.next;
        big.next = null;
        return smallHead.next;
    }


    public List<Integer> rightSideView(TreeNode root) {

        List<Integer> ans = new ArrayList<>();

        rightSideView(root, 0, ans);
        return ans;
    }

    public void rightSideView(TreeNode root, int level, List<Integer> ans) {

        if (root == null) {
            return;
        }
        if (ans.size() == level) {
            ans.add(root.val);
        }

        rightSideView(root.right, level + 1, ans);
        rightSideView(root.left, level + 1, ans);
    }


    public int sumNumbers(TreeNode root) {
        if (root == null) {
            return 0;
        }

        if (root.left == null && root.right == null) {
            return root.val;
        }
        return sumNumbers(root.left, root.val) + sumNumbers(root.right, root.val);
    }

    public int sumNumbers(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }

        sum = sum * 10 + root.val;
        if (root.left == null && root.right == null) {
            return sum;
        }

        return sumNumbers(root.left, sum) + sumNumbers(root.right, sum);

    }




}
