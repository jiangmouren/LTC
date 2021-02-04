package com.mycompany.app;

/**
 * https://leetcode.com/problems/palindrome-linked-list/
 * Given a singly linked list, determine if it is a palindrome.
 *
 * Example 1:
 * Input: 1->2
 * Output: false
 *
 * Example 2:
 * Input: 1->2->2->1
 * Output: true
 *
 * Follow up:
 * Could you do it in O(n) time and O(1) space?
 */

public class PalindromeLinkedList{
    class ListNode{
        int val;
        ListNode next;
        ListNode(){ }
        ListNode(int val){
            this.val = val;
        }
    }
    public boolean isPalindrome(ListNode head) {
        if(head==null || head.next==null){
            return true;
        }
        //我能想到的space O(1)的解法就是把后半段reverse了
        ListNode ptr0 = head;
        ListNode ptr1 = head;
        while(ptr1.next!=null){
            ptr1 = ptr1.next;
            if(ptr1.next!=null){
                ptr1 = ptr1.next;
            }
            ptr0 = ptr0.next;
        }
        //list has at least 2 valid nodes
        ListNode pre = ptr0;
        ListNode cur = ptr0.next;
        while(cur!=null){
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        ListNode left = head;
        ListNode right = ptr1;
        while(right!=ptr0){
            if(left.val!=right.val){
                return false;
            }
            left = left.next;
            right = right.next;
        }
        return right.val==left.val;
    }
}