package com.mycompany.app;

/**
 * https://leetcode.com/problems/reverse-linked-list-ii/
 * Given the head of a singly linked list and two integers left and right where left <= right,
 * reverse the nodes of the list from position left to position right, and return the reversed list.
 *
 * Example 1:
 * Input: head = [1,2,3,4,5], left = 2, right = 4
 * Output: [1,4,3,2,5]
 *
 * Example 2:
 * Input: head = [5], left = 1, right = 1
 * Output: [5]
 *
 * Constraints:
 * The number of nodes in the list is n.
 * 1 <= n <= 500
 * -500 <= Node.val <= 500
 * 1 <= left <= right <= n
 *
 * Follow up: Could you do it in one pass?
 */
public class ReverseLinkedListII {
    class ListNode{
        int val;
        ListNode next;
        ListNode(){}
        ListNode(int val){
            this.val = val;
        }
        ListNode(int val, ListNode next){
            this.val = val;
            this.next = next;
        }
    }
    //要注意两个点：一个是left可能从head开始，所以要建一个dummyHead->pre，以防最后面return的时候需要用
    //再就是在reverse LinkedList的时候，注意ptr2可能出现的null
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;
        ListNode ptr = head;
        ListNode pre = dummyHead;
        ListNode cur = head;
        ListNode next = head.next;
        int pos = 0;
        while(pos<right){
            if(pos==left-1){
                ptr = pre;
            }
            if(pos>left-1){
                cur.next = pre;
            }
            pre = cur;
            cur = next;
            next = next==null ? null : next.next;
            pos++;
        }
        ListNode tail = ptr.next;
        ptr.next = pre;
        tail.next = cur;
        return dummyHead.next;
    }
}
