package com.mycompany.app;

import com.mycompany.app.AddTwoNumbersII;
import org.junit.Test;
import com.mycompany.app.AddTwoNumbersII.*;
import static org.junit.Assert.*;

/**
 * Created by eljian on 9/14/2017.
 */
public class AddTwoNumbersIITest {
    AddTwoNumbersII obj = new AddTwoNumbersII();
    ListNode l1 = new ListNode(7);
    ListNode l2 = new ListNode(5);

    ListNode l4 = new ListNode(7);
    ListNode l5 = new ListNode(5);

    ListNode l6 = new ListNode(7);
    ListNode l7 = new ListNode(5);

    ListNode l3 = new ListNode(7);
    {
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);
        l1.next.next.next = new ListNode(3);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        l4.next = new ListNode(2);
        l4.next.next = new ListNode(4);
        l4.next.next.next = new ListNode(3);
        l5.next = new ListNode(6);
        l5.next.next = new ListNode(4);

        l6.next = new ListNode(2);
        l6.next.next = new ListNode(4);
        l6.next.next.next = new ListNode(3);
        l7.next = new ListNode(6);
        l7.next.next = new ListNode(4);

        l3.next = new ListNode(8);
        l3.next.next = new ListNode(0);
        l3.next.next.next = new ListNode(7);
    }
    @Test
    public void add1() throws Exception {
        ListNode res = obj.add1(l1, l2);
        obj.printList(res);
        assertTrue(checkList(res, l3));
    }
    @Test
    public void add2() throws Exception {
        ListNode res = obj.add2(l4, l5);
        obj.printList(res);
        assertTrue(checkList(res, l3));
    }

    @Test
    public void add3() throws Exception {
        ListNode res = obj.add3(l6, l7);
        obj.printList(res);
        assertTrue(checkList(res, l3));
    }

    private boolean checkList(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }

        ListNode ptr1 = l1, ptr2 = l2;
        while (ptr1 != null && ptr2 != null) {
            if(ptr1.val!=ptr2.val){
                return false;
            }
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }
        if (ptr1 != null || ptr2 != null) {
            return false;
        }
        return true;
    }
}