package com.mycompany.app;
/**
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 */
public class AddTwoNumbers {
    public static class ListNode{
        int val;
        ListNode next;
        ListNode(int x){
            this.val = x;
        }
    }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode ptr1 = l1;
        ListNode ptr2 = l2;
        ListNode dummyHead = new ListNode(0);
        ListNode ptr = dummyHead;
        int c = 0;
        while(ptr1!=null || ptr2!=null){
            int tmp = c;
            if(ptr1!=null){
                tmp += ptr1.val;
                ptr1 = ptr1.next;
            }
            if(ptr2!=null){
                tmp += ptr2.val;
                ptr2 = ptr2.next;
            }
            if(tmp>9){//maximum 18 for LSB and 19 for others
                tmp -= 10;
                c = 1;
            }
            ListNode node = new ListNode(tmp);
            ptr.next = node;
            ptr = ptr.next;
        }
        return dummyHead.next;
    }
}
