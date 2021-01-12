package com.mycompany.app;

/**
 * Question:
 * Reverse a singly linked list.
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

/**
 * Analysis:
 * Do not lose the link is the rule.
 * Initially people might want to go all the way to the end and reverse from there.
 * Won't work. Because you can go to head to tail not the other way around.
 * Can only revert following the direction.
 * Need three pointers.
 */
public class ReverseLinkedList {
    public static class ListNode{
        int val;
        ListNode next;
        ListNode(){}
        ListNode(int x){val=x;}
        ListNode(int x, ListNode next){
            this.val = x;
            this.next = next;
        }
    }

    public ListNode reverseList(ListNode head) {
        if(head==null){
            return null;
        }
        ListNode pre = null;
        ListNode cur = head;
        ListNode nxt = head.next;
        while(cur!=null){
            cur.next = pre;
            pre = cur;
            cur = nxt;
            if(nxt!=null){
                nxt = nxt.next;
            }
        }
        return pre;
    }

    public ListNode reverseListSln2(ListNode head) {
        if(head==null){
            return null;
        }
        ListNode dummyHead = new ListNode();
        helper(head, dummyHead);
        return dummyHead.next;
    }

    private void helper(ListNode cur, ListNode ptr){
        if(cur.next==null){
            ptr.next = cur;
            return;
        }

        helper(cur.next, ptr);
        cur.next.next = cur;
        cur.next = null;//不要遗忘了这个，否则就会在新list尾部出现cycle
        return;
    }
}
