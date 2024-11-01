package com.example.lib;

/**
 * 双向链表
 *
 * @author wzh
 * @date 2024/7/2
 */
class DLinkedNode {

    int key;
    int value;
    DLinkedNode prev;
    DLinkedNode next;

    public DLinkedNode() {
    }

    public DLinkedNode(int key, int value) {
        this.value = value;
    }
}
