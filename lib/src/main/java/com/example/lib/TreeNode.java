package com.example.lib;

/**
 * 二叉树
 * @author wzh
 * @date 2023/7/14
 */
class TreeNode {
    int val;

    TreeNode left;

    TreeNode right;


    TreeNode() {
    }


    TreeNode(int val) {
        this.val = val;
    }


    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
