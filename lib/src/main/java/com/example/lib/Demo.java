package com.example.lib;

/**
 * @author wzh
 * @date 2023/7/27
 */
public class Demo {

   void test() {
      Runnable runnable = new Runnable() {
         @Override
         public void run() {
            System.out.println("leavesC");
         }
      };
      runnable.run();
   }

}
