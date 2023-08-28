package com.example.lib;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wzh
 * @date 2023/6/2
 */
class Solution2 {

    /**
     * 2559. 统计范围内的元音字符串数
     * 给你一个下标从 0 开始的字符串数组 words 以及一个二维整数数组 queries 。
     * <p>
     * 每个查询 queries[i] = [li, ri] 会要求我们统计在 words 中下标在 li 到 ri 范围内（包含 这两个值）并且以元音开头和结尾的字符串的数目。
     * <p>
     * 返回一个整数数组，其中数组的第 i 个元素对应第 i 个查询的答案。
     * <p>
     * 注意：元音字母是 'a'、'e'、'i'、'o' 和 'u' 。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/count-vowel-strings-in-ranges
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param words
     * @param queries
     * @return
     */
    public int[] vowelStrings(String[] words, int[][] queries) {

        int[] vowelCounts = new int[words.length + 1];
        vowelCounts[0] = 0;
        int count = 0;
        for (int i = 0; i < words.length; i++) {
            if (isVowelString(words[i])) {
                ++count;
            }

            vowelCounts[i + 1] = count;
        }

        System.out.println("统计数组：" + arrayToString(vowelCounts));

        int[] vowelSum = new int[queries.length];
        for (int j = 0; j < queries.length; j++) {
            int start = queries[j][0], end = queries[j][1];
            vowelSum[j] = vowelCounts[end + 1] - vowelCounts[start];
        }
        return vowelSum;
    }

    public boolean isVowelString(String word) {
        return isVowelLetter(word.charAt(0)) && isVowelLetter(word.charAt(word.length() - 1));
    }

    public boolean isVowelLetter(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }


    public String arrayToString(int[] array) {
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


    public List<List<Integer>> permute(int[] nums) {
        int len = nums.length;
        // 使用一个动态数组保存所有可能的全排列
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) {
            return res;
        }

        boolean[] used = new boolean[len];
        Deque<Integer> path = new ArrayDeque<>(len);

        dfs(nums, len, 0, path, used, res);
        return res;
    }

    private void dfs(int[] nums, int len, int depth,
                     Deque<Integer> path, boolean[] used,
                     List<List<Integer>> res) {
        if (depth == len) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < len; i++) {
            if (!used[i]) {
                path.addLast(nums[i]);
                used[i] = true;

                System.out.println("  递归之前 => " + path);
                dfs(nums, len, depth + 1, path, used, res);

                used[i] = false;
                path.removeLast();
                System.out.println("递归之后 => " + path);
            }
        }
    }


    /**
     * 2465. 不同的平均值数目
     *
     * @param nums
     * @return
     */
    public int distinctAverages(int[] nums) {

        Arrays.sort(nums);

        int l = 0;
        int r = nums.length - 1;
        Set set = new HashSet<Integer>();
        while (r - 1 >= l) {
            int sum = nums[l] + nums[r];
            set.add(sum);
            l++;
            r--;
        }

        return set.size();
    }


    /**
     * 2460. 对数组执行操作
     *
     * @param nums
     * @return
     */
    public int[] applyOperations(int[] nums) {

        int n = nums.length;
        for (int i = 0, j = 0; i < n; i++) {
            if (i + 1 < n && nums[i] == nums[i + 1]) {
                nums[i] *= 2;
                nums[i + 1] = 0;
//                System.out.println("i=" + i);
//                System.out.println("nums[" + i + "]=" + nums[i]);
            }

            //一旦出现nums[i]=0的时候j是没有变化的，而i是一直在走的
            //i,j不同步之后所有的遇上nums[i]!=0的时候都i,j都要进行交换
            if (nums[i] != 0) {
//                System.out.println("交换前：nums[" + i + "]=" + nums[i] + "nums[" + j + "]=" + nums[j]);
                swap(nums, i, j);
                System.out.println("交换：i:" + i + ",j:" + j);
//                System.out.println("交换后：nums[" + i + "]=" + nums[i] + "nums[" + j + "]=" + nums[j]);
                j++;
            }

            System.out.println(arrayToString(nums));
        }
        return nums;
    }


    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    /**
     * 2352. 相等行列对
     *
     * @param grid
     * @return
     */
    public int equalPairs(int[][] grid) {

        int count = 0;
        int row = grid.length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                boolean result = checkEqual(grid, i, j);
                if (result) {
                    count++;
                }
            }
        }

        return count;
    }

    public boolean checkEqual(int[][] grid, int row, int col) {
        int length = grid.length;
        for (int i = 0; i < length; i++) {
            if (grid[row][i] != grid[i][col]) {
                return false;
            }
        }
        return true;
    }


    /**
     * 1156. 单字符重复子串的最大长度
     * 如果字符串中的所有字符都相同，那么这个字符串是单字符重复的字符串。
     * <p>
     * 给你一个字符串text，你只能交换其中两个字符一次或者什么都不做，然后得到一些单字符重复的子串。返回其中最长的子串的长度。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/swap-for-longest-repeated-character-substring
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param text
     * @return
     */
    public int maxRepOpt1(String text) {

        char[] cList = text.toCharArray();
        int length = cList.length;
        Map<Character, Integer> count = new HashMap<>();
        for (int i = 0; i < length; i++) {
            char c = cList[i];
            count.put(c, count.getOrDefault(c, 0) + 1);
        }

        int res = 0;
        for (int i = 0; i < length; i++) {
            int j = i;
            while (j < length && cList[j] == cList[i]) {
                j++;
            }

            int k = j + 1;
            while (k < length && cList[k] == cList[i]) {
                k++;
            }

            res = Math.max(res, Math.min(k - i, count.getOrDefault(cList[i], 0)));
        }

        return res;
    }


    /**
     * 2611. 老鼠和奶酪
     * <p>
     * 有两只老鼠和n块不同类型的奶酪，每块奶酪都只能被其中一只老鼠吃掉。
     * <p>
     * 下标为 i处的奶酪被吃掉的得分为：
     * <p>
     * 如果第一只老鼠吃掉，则得分为reward1[i]。
     * 如果第二只老鼠吃掉，则得分为reward2[i]。
     * 给你一个正整数数组reward1，一个正整数数组reward2，和一个非负整数k。
     * <p>
     * 请你返回第一只老鼠恰好吃掉 k块奶酪的情况下，最大得分为多少。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/mice-and-cheese
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param reward1
     * @param reward2
     * @param k
     * @return
     */
    public int miceAndCheese(int[] reward1, int[] reward2, int k) {
        int ans = 0;
        int n = reward2.length;
        int[] diffs = new int[n];
        for (int i = 0; i < n; i++) {
            ans += reward2[i];
            diffs[i] = reward1[i] - reward2[i];
        }

        Arrays.sort(diffs);

        for (int j = n - 1; j >= n - k; j--) {
            ans += diffs[j];
        }
        return ans;
    }


    /**
     * 1171. 从链表中删去总和值为零的连续节点
     * 给你一个链表的头节点head，请你编写代码，反复删去链表中由 总和值为 0 的连续节点组成的序列，直到不存在这样的序列为止。
     * <p>
     * 删除完毕后，请你返回最终结果链表的头节点
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/remove-zero-sum-consecutive-nodes-from-linked-list
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param head
     * @return
     */
    public ListNode removeZeroSumSublists(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        Map<Integer, ListNode> seen = new HashMap<>();

        // 首次遍历建立 节点处链表和<->节点 哈希表
        // 若同一和出现多次会覆盖，即记录该sum出现的最后一次节点
        int sum = 0;
        for (ListNode node = dummy; node != null; node = node.next) {
            sum += node.val;
            seen.put(sum, node);
        }

        // 第二遍遍历 若当前节点处sum在下一处出现了则表明两结点之间所有节点和为0 直接删除区间所有节点
        sum = 0;
        for (ListNode node = dummy; node != null; node = node.next) {
            sum += node.val;
            node.next = seen.get(sum).next;
        }
        return dummy.next;
    }


    /**
     * 560. 和为 K 的子数组
     * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的连续子数组的个数 。
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum(int[] nums, int k) {

        int count = 0;

        //前缀和
        int pre = 0;

        //借助哈希进行统计，key是前缀和，value是该前缀和的出现次数
        Map<Integer, Integer> map = new HashMap();

        map.put(0, 1);
        for (int num : nums) {
            //当前前缀和
            pre += num;

            //查找是否存在值为pre-k的前缀和，如何存在，则符合条件
            if (map.containsKey(pre - k)) {
                count += map.getOrDefault(pre - k, 0);
            }

            //记录前缀和,更新该前缀和的出现次数
            map.put(pre, map.getOrDefault(pre, 0) + 1);
        }

        return count;
    }


    /**
     * 1248. 统计「优美子数组」
     * 给你一个整数数组nums 和一个整数 k。如果某个连续子数组中恰好有 k 个奇数数字，我们就认为这个子数组是「优美子数组」。
     * <p>
     * 请返回这个数组中 「优美子数组」 的数目。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/count-number-of-nice-subarrays
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param nums
     * @param k
     * @return
     */
    public int numberOfSubarrays(int[] nums, int k) {


        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % 2 != 0) {
                sum++;
            }
            nums[i] = sum;
        }

        for (int num : nums) {
            if (map.containsKey(num - k)) {
                count += map.getOrDefault(num - k, 0);
            }

            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        return count;
    }


    /**
     * 58. 最后一个单词的长度
     * 给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中 最后一个 单词的长度。
     * <p>
     * 单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/length-of-last-word
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param s
     * @return
     */
    public int lengthOfLastWord(String s) {
        int count = 0;
        for (int i = s.length() - 1; i >= 0; i--) {

            if (count == 0) {
                if (s.charAt(i) != ' ') {
                    count++;
                }
            } else {
                if (s.charAt(i) != ' ') {
                    count++;
                } else {
                    break;
                }
            }
        }

        return count;

    }


    /**
     * 66. 加一
     * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
     * <p>
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     * <p>
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/plus-one
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param digits
     * @return
     */
    public int[] plusOne(int[] digits) {

        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] == 9) {
                digits[i] = 0;
            } else {
                digits[i] += 1;
                return digits;
            }
        }

        int[] newDigits = new int[digits.length + 1];
        newDigits[0] = 1;
        return newDigits;
    }


    /**
     * 2475. 数组中不等三元组的数目
     * <p>
     * 给你一个下标从 0 开始的正整数数组 nums 。请你找出并统计满足下述条件的三元组 (i, j, k) 的数目：
     * <p>
     * 0 <= i < j < k < nums.length
     * nums[i]、nums[j] 和 nums[k] 两两不同 。
     * 换句话说：nums[i] != nums[j]、nums[i] != nums[k] 且 nums[j] != nums[k] 。
     * 返回满足上述条件三元组的数目。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/number-of-unequal-triplets-in-array
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param nums
     * @return
     */
    public int unequalTriplets(int[] nums) {
        int n = nums.length;
        if (n < 3) {
            return 0;
        }

        Arrays.sort(nums);
        int count = 0;

        for (int i = 0; i < n; i++) {


        }

        return 0;
    }





}
