package Finished.graph.linkedlist;

/**
 * Created by jiangmouren on 6/4/17.
 */

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
        ListNode(int x){val=x;}
    }
    public ListNode reverseList(ListNode head) {
        ListNode ptr1=null, ptr2=head, ptr3=head.next;
        while(ptr2!=null){
            ptr2.next=ptr1;
            ptr1=ptr2;
            ptr2=ptr3;
            if(ptr3!=null)
                ptr3=ptr3.next;
        }
        return ptr1;
    }
}
