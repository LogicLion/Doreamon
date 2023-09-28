package com.example.lib;

/**
 * @author wzh
 * @date 2023/9/27
 */
class Node {
   int val;
   Node next;
   Node random;

   public Node(int val) {
      this.val = val;
      this.next = null;
      this.random = null;
   }
}
