/**
 * Question:
 * Merge two sorted linked lists and return it as a new list.
 * The new list should be made by splicing together the nodes of the first two lists.
 */

/**
 * Analysis:
 * Say our goal is to merge the original two lists instead of constructing a new list.
 */
package Finished.sort;

public class MergeTwoSortedLists{
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x){this.val=x;}
    }

    public ListNode mergeTwoSortedLists(ListNode root1, ListNode root2){
        ListNode head=null, tail=null;
        ListNode ptr1=root1, ptr2=root2;
        while(ptr1!=null || ptr2!=null){
            if(ptr1==null){
                //for sure ptr2!=null
                if(head==null){
                    head = ptr2;
                    break;
                }
                else{
                    tail.next = ptr2;
                    //tail = tail.next;
                    break;
                }
            }
            else if(ptr2==null){
                if(head==null){
                    head = ptr1;
                    break;
                }
                else{
                    tail.next = ptr1;
                    break;
                }
            }
            else{
                if(head==null){
                    //assume the sorted in ascending order
                    if(ptr1.val<ptr2.val){
                        head = ptr1;
                        tail = ptr1;
                        ptr1 = ptr1.next;
                    }
                    else{
                        head = ptr2;
                        tail = ptr2;
                        ptr2 = ptr2.next;
                    }
                }
                else{
                    if(ptr1.val<ptr2.val){
                        tail.next = ptr1;
                        ptr1 = ptr1.next;
                        tail = tail.next;
                    }
                    else{
                        tail.next = ptr2;
                        ptr2 = ptr2.next;
                        tail = tail.next;
                    }
                }
            }
        }
        return head;
    }

}
