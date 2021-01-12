/**
 * Question:
 * https://leetcode.com/problems/merge-two-sorted-lists/
 * Merge two sorted linked lists and return it as a sorted list.
 * The list should be made by splicing together the nodes of the first two lists.
 *
 * Example 1:
 * Input: l1 = [1,2,4], l2 = [1,3,4]
 * Output: [1,1,2,3,4,4]
 *
 * Example 2:
 * Input: l1 = [], l2 = []
 * Output: []
 *
 * Example 3:
 * Input: l1 = [], l2 = [0]
 * Output: [0]
 *
 * Constraints:
 * The number of nodes in both lists is in the range [0, 50].
 * -100 <= Node.val <= 100
 * Both l1 and l2 are sorted in non-decreasing order.
 */

/**
 * Analysis:
 * Say our goal is to merge the original two lists instead of constructing a new list.
 */
package com.mycompany.app;

public class MergeTwoSortedLists{
    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode ptr1 = l1;
        ListNode ptr2 = l2;
        ListNode dummyHead = new ListNode();
        ListNode ptr = dummyHead;

        while(ptr1!=null && ptr2!=null){
            if(ptr1.val<=ptr2.val){
                ptr.next = ptr1;
                ptr1 = ptr1.next;
            }
            else{
                ptr.next = ptr2;
                ptr2 = ptr2.next;
            }
            ptr = ptr.next;
        }

        if(ptr1!=null){
            ptr.next = ptr1;
        }
        else{
            ptr.next = ptr2;
        }
        return dummyHead.next;
    }

}
