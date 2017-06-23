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
    public boolean linkedListCycle(ListNode head){
        if(head==null) return false;
        ListNode ptr1 = head, ptr2 = head.next;
        while(ptr1!=null && ptr2!=null){
            //if(ptr1==ptr2 && ptr1!=head) return true;
            //Originally I was using this one to avoid the initial case, bad idea since they can rejoin at head.
            //And once that it is the case they will rejoin again and again at head(if they also both started at head)
            if(ptr1==ptr2) return true;
            if(ptr1==ptr2 && ptr1!=head) return true;
            ptr1 = ptr1.next;
            ptr2=ptr2.next;
            if(ptr2==null || ptr2.next==null) return false;
            else ptr2=ptr2.next;
        }
        return false;
    }
}
