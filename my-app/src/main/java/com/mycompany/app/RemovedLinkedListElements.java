package com.mycompany.app;

/**
 * https://leetcode.com/problems/remove-linked-list-elements/
 * Remove all elements from a linked list of integers that have value val.
 *
 * Example:
 * Input:  1->2->6->3->4->5->6, val = 6
 * Output: 1->2->3->4->5
 */
public class RemovedLinkedListElements {
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public ListNode removeElements(ListNode head, int val) {
        if(head==null){//唯一要注意的就是Handle head==null的情况。
            return null;
        }
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;
        ListNode pre = dummyHead;
        ListNode cur = head;
        ListNode nxt = cur.next;
        while(cur!=null){
            if(cur.val==val){
                pre.next = nxt;
                cur = nxt;
                nxt = (nxt==null) ? null : nxt.next;
            }
            else{
                pre = cur;
                cur = nxt;
                nxt = (nxt==null) ? null : nxt.next;
            }
        }
        return dummyHead.next;
    }
}
