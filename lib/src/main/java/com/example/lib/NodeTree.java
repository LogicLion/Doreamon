package com.example.lib;

import java.util.List;

/**
 * @author wzh
 * @date 2024/2/19
 */
class NodeTree {
    public int val;
    public List<NodeTree> children;

    public NodeTree() {
    }

    public NodeTree(int _val) {
        val = _val;
    }

    public NodeTree(int _val, List<NodeTree> _children) {
        val = _val;
        children = _children;
    }
}
