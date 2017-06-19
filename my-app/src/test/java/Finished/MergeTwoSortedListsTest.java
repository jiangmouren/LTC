package Finished;

import Finished.MergeTwoSortedLists;
import org.junit.Test;

import static org.junit.Assert.*;
import Finished.MergeTwoSortedLists.*;

/**
 * Created by eljian on 6/14/2017.
 */
public class MergeTwoSortedListsTest {
    MergeTwoSortedLists objectUnderTest = new MergeTwoSortedLists();
    ListNode head1 = new ListNode(0);
    ListNode head2 = new ListNode(1);
    ListNode head3 = new ListNode(0);
    {
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(4);
        head1.next.next.next = new ListNode(6);
        head2.next = new ListNode(3);
        head2.next.next = new ListNode(5);
        head2.next.next.next = new ListNode(7);
        head3.next = new ListNode(2);
        head3.next.next = new ListNode(4);
        head3.next.next.next = new ListNode(6);
    }

    @Test
    public void mergeTwoSortedLists() throws Exception {
        ListNode result1 = objectUnderTest.mergeTwoSortedLists(head1, head2);
        printList(result1);
        System.out.println();
        //printList(head3);
        ListNode result2 = objectUnderTest.mergeTwoSortedLists(head3, null);
        printList(result2);
        assertNull(objectUnderTest.mergeTwoSortedLists(null, null));
    }

    private void printList(ListNode head){
        ListNode ptr = head;
        while(ptr!=null){
            System.out.print(ptr.val);
            ptr = ptr.next;
        }
    }

}