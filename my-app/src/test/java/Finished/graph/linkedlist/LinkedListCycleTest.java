package Finished.graph.linkedlist;

import Finished.graph.linkedlist.LinkedListCycle;
import org.junit.Test;
import Finished.graph.linkedlist.LinkedListCycle.*;
import static org.junit.Assert.*;

/**
 * Created by eljian on 6/22/2017.
 */
public class LinkedListCycleTest {
    LinkedListCycle objectUnderTest = new LinkedListCycle();
    ListNode head1 = new ListNode(0);
    ListNode head2 = new ListNode(0);
    {
        head1.next = new ListNode(1);
        head1.next.next = new ListNode(2);
        head1.next.next.next = head1;
        head2.next = new ListNode(1);
        head2.next.next = new ListNode(2);
    }

    @Test
    public void linkedListCycle() throws Exception {
        assertTrue(objectUnderTest.linkedListCycle(head1));
        assertFalse(objectUnderTest.linkedListCycle(head2));
    }

}