package com.example.lib;

/**
 * @author wzh
 * @date 2023/5/24
 */
class ListNode {

    public ListNode() {
    }

    public ListNode(int value) {
        this.val = value;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    int val = 0;
    ListNode next = null;


    public static void printListNode(ListNode head) {
        StringBuffer sb = new StringBuffer();

        if (head == null) {
            return;
        } else {
            sb.append("{");
            sb.append(head.val);
        }

        ListNode current = head;
        while (current.next != null) {
            sb.append(",");
            current = current.next;
            sb.append(current.val);
        }

        sb.append("}");
        System.out.println(sb);
    }
}
