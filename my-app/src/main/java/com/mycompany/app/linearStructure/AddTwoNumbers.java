package com.mycompany.app.linearStructure;
/**
 * https://leetcode.com/problems/add-two-numbers/
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 */
public class AddTwoNumbers {
    public class ListNode {
        int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode();
        ListNode ptr = head;
        ListNode ptr1 = l1;
        ListNode ptr2 = l2;
        int c = 0;
        while(ptr1!=null || ptr2!=null){
            if(ptr1!=null){
                c += ptr1.val;
                ptr1 = ptr1.next;
            }
            if(ptr2!=null){
                c += ptr2.val;
                ptr2 = ptr2.next;
            }
            if(c<10){
                ptr.next = new ListNode(c);
                ptr = ptr.next;
                c = 0;
            }
            else{
                ptr.next = new ListNode(c-10);
                ptr = ptr.next;
                c = 1;
            }
        }
        //check c by the end
        if(c>0){
            ptr.next = new ListNode(c);
        }
        return head.next;
    }
}
