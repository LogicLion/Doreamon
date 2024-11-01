package com.example.lib;

import java.util.Stack;

/**
 * @author wzh
 * @date 2024/3/28
 */
class BSTIterator {

    Stack<TreeNode> stack;
    TreeNode cur;

    public BSTIterator(TreeNode root) {
        cur = root;
        stack = new Stack<>();
    }

    public int next() {
        while (cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
        TreeNode node = stack.pop();
        int ret = node.val;
        cur = node.right;
        return ret;
    }

    public boolean hasNext() {

        return cur != null && !stack.isEmpty();
    }


}
