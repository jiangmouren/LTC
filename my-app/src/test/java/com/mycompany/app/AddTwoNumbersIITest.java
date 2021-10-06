package com.mycompany.app;

import com.mycompany.app.linearStructure.AddTwoNumbersII;
import com.mycompany.app.linearStructure.AddTwoNumbersII.*;

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
}