package com.example.lib;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wzh
 * @date 2024/7/5
 */
class Node {
   public int val;
   public List<Node> neighbors;
   public Node() {
      val = 0;
      neighbors = new ArrayList<Node>();
   }
   public Node(int _val) {
      val = _val;
      neighbors = new ArrayList<Node>();
   }
   public Node(int _val, ArrayList<Node> _neighbors) {
      val = _val;
      neighbors = _neighbors;
   }
}
