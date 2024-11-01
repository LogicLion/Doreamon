package com.example.lib;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wzh
 * @date 2024/3/11
 */
class LRUCache {

    int capacity;
    int size = 0;
    Map<Integer, DLinkedNode> cache;


    /**
     * 虚拟头结点
     */
    DLinkedNode head;

    /**
     * 虚拟尾结点
     */
    DLinkedNode tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    public int get(int key) {

        DLinkedNode node = cache.get(key);
        if (node != null) {
            moveToHead(node);
            return node.value;
        } else {
            return -1;
        }

    }

    public void put(int key, int value) {

        DLinkedNode node = cache.get(key);
        if (node == null) {
            DLinkedNode newNode = new DLinkedNode(key, value);
            addToHead(newNode);
            cache.put(key, newNode);
            size++;
            if (size > capacity) {
                DLinkedNode tail1 = removeTail();
                cache.remove(tail1.key);
                size--;
            }
        } else {
            removeNode(node);
            cache.remove(key);
            DLinkedNode newNode = new DLinkedNode(key, value);
            addToHead(newNode);
            cache.put(key, newNode);
        }
    }

    public void addToHead(DLinkedNode node) {
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        node.prev = head;
    }

    public void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    public DLinkedNode removeTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }
}