package com.mycompany.app;

/**
 * Question:
 * Given a linked list, determine if it has a cycle in it.
 * Follow up:
 * Can you solve it without using extra space?
 */

/**
 * Analysis:
 * Standard way would be using HashMap just like in all graph case.
 * For this linear and directed structure, we can use "chaser".
 * Say two pointers, ptr1 is faster than ptr2.
 * If there is no loop, ptr1 should never catch ptr2, until they both are null.
 * If there is loop, they both will be trapped inside that loop.
 * And because ptr2 is faster, it will catch ptr1 on a normal node.
 */

public class LinkedListCycle{
    public static class ListNode{
        int val;
        ListNode next;
        ListNode(int x){this.val = x;}
    }
    public boolean hasCycle(ListNode head) {
        //直观的想法是用visited map
        //O(1) memo的做法应该是chasing pointers，用一个两倍速和一个一倍速的pointer,而且两倍速的还在前面
        //如果他们最后相遇的位置不是在null，那么就一定有cycle
        if(head==null){
            return false;
        }
        ListNode ptr0 = head;
        ListNode ptr1 = head.next;
        while(ptr1!=null && ptr0!=ptr1){
            ptr0 = ptr0.next;
            ptr1 = ptr1.next;
            ptr1 = ptr1==null ? null : ptr1.next;
        }

        return ptr1!=null;
    }
}
