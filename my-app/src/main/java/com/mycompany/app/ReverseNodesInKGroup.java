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
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;
        ListNode ptr = dummyHead;
        ListNode dummyTail;
        int cnt = 0;
        while(cnt<k && ptr!=null){
            ptr = ptr.next;
            cnt++;
        }
        if(cnt<k || ptr==null){
            return head;
        }
        else{
            dummyTail = ptr;
            ListNode ptr0 = head;
            ListNode ptr1 = ptr0==null ? null : ptr0.next;
            ListNode ptr2 = ptr1==null ? null : ptr1.next;
            while(ptr0!=dummyTail){
                ptr1.next = ptr0;
                ptr0 = ptr1;
                ptr1 = ptr2;
                ptr2 = ptr2==null ? null : ptr2.next;
            }
            ListNode temp = dummyHead.next;
            dummyHead.next = dummyTail;
            dummyTail = temp;
            dummyTail.next = reverseKGroup(ptr1, k);
        }
        return dummyHead.next;
    }
}