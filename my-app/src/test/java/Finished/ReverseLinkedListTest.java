package Finished;

import Finished.ReverseLinkedList;
import org.junit.Test;
import Finished.ReverseLinkedList.*;

/**
 * Created by jiangmouren on 6/10/17.
 */
public class ReverseLinkedListTest {
    ReverseLinkedList objectUnderTest = new ReverseLinkedList();
    ListNode head = new ListNode(1);
    {
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
    }
    @Test
    public void reverseList() throws Exception {
        printList(objectUnderTest.reverseList(head));
    }

    private void printList(ListNode head){
        while(head!=null){
            System.out.print(head.val);
            head=head.next;
        }
        return;
    }

}