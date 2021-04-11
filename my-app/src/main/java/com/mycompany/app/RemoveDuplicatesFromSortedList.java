package com.mycompany.app;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-list/
 * Given the head of a sorted linked list, delete all duplicates such that each element appears only once.
 * Return the linked list sorted as well.
 *
 * Example 1:
 * Input: head = [1,1,2]
 * Output: [1,2]
 *
 * Example 2:
 * Input: head = [1,1,2,3,3]
 * Output: [1,2,3]
 *
 * Constraints:
 * The number of nodes in the list is in the range [0, 300].
 * -100 <= Node.val <= 100
 * The list is guaranteed to be sorted in ascending order.
 */

public class RemoveDuplicatesFromSortedList {
    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode deleteDuplicates(ListNode head) {
        //这里面唯一要考虑的是但重复出现的时候，是删前面的还是删后面的
        //删前面的，需要reconnect之前的node,所以删后面的比较省事,只要jump pointer就可以了
        ListNode ptr0 = head;
        ListNode ptr1 = ptr0;
        while(ptr0!=null){
            while(ptr1!=null && ptr1.val==ptr0.val){
                ptr1 = ptr1.next;
            }
            ptr0.next = ptr1;
            ptr0 = ptr0.next;
        }
        return head;
    }
}
