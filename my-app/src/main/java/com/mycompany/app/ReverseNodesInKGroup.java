package com.mycompany.app;

/**
 * https://leetcode.com/problems/reverse-nodes-in-k-group/
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
 *
 * k is a positive integer and is less than or equal to the length of the linked list.
 * If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.
 *
 * Follow up:
 * Could you solve the problem in O(1) extra memory space?
 * You may not alter the values in the list's nodes, only nodes itself may be changed.
 *
 * Example 1:
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [2,1,4,3,5]
 *
 * Example 2:
 * Input: head = [1,2,3,4,5], k = 3
 * Output: [3,2,1,4,5]
 *
 * Example 3:
 * Input: head = [1,2,3,4,5], k = 1
 * Output: [1,2,3,4,5]
 *
 * Example 4:
 * Input: head = [1], k = 1
 * Output: [1]
 *
 * Constraints:
 * The number of nodes in the list is in the range sz.
 * 1 <= sz <= 5000
 * 0 <= Node.val <= 1000
 * 1 <= k <= sz
 */

public class ReverseNodesInKGroup {
    class ListNode{
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * 底下这种是recursion的写法，用iterative写，思路是一样的，只不过写起来控制条件共复杂，需要考虑的edgecase也更多
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        int cnt = 0;
        ListNode ptr = head;
        while(ptr!=null && cnt<k){
            ptr = ptr.next;
            cnt++;
        }
        if(cnt<k){
            return head;
        }

        ListNode dummyTail = ptr;
        ListNode pre = head;
        ListNode cur = head.next;
        ListNode next = cur==null ? null : cur.next;
        while(cur!=dummyTail){
            cur.next = pre;
            pre = cur;
            cur = next;
            next = next==null ? null : next.next;
        }
        head.next = reverseKGroup(dummyTail, k);
        //pre is the new head
        return pre;
    }
}