package com.mycompany.app;

import org.junit.Test;
import com.mycompany.app.AddTwoNumbers.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

//import static org.junit.Assert.*;

/**
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Input: (2 -> 4 -> 6) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 1 -> 1
 * Input: (2 -> 4) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 5
 */
public class AddTwoNumbersTest {
    AddTwoNumbers obj = new AddTwoNumbers();

    LinkNode l1 = new LinkNode(2);
    LinkNode l2 = new LinkNode(5);
    {
        l1.next = new LinkNode(4);
        l2.next = new LinkNode(6);
        l1.next.next = new LinkNode(3);
        l2.next.next = new LinkNode(4);
    }

    LinkNode l3 = new LinkNode(2);
    LinkNode l4 = new LinkNode(5);
    {
        l3.next = new LinkNode(4);
        l4.next = new LinkNode(6);
        l3.next.next = new LinkNode(6);
        l4.next.next = new LinkNode(4);
    }

    LinkNode l5 = new LinkNode(2);
    LinkNode l6 = new LinkNode(5);
    {
        l5.next = new LinkNode(4);
        l6.next = new LinkNode(6);
        l6.next.next = new LinkNode(4);
    }

    @Test
    public void addTwoNumbers() throws Exception {
        LinkNode result1 = obj.solution(l1, l2);
        LinkNode result2 = obj.solution(l3, l4);
        LinkNode result3 = obj.solution(l5, l6);
        for(LinkNode ptr=result1; ptr!=null; ptr=ptr.next){
            System.out.println(ptr.num);
        }
        for(LinkNode ptr=result2; ptr!=null; ptr=ptr.next){
            System.out.println(ptr.num);
        }
        for(LinkNode ptr=result3; ptr!=null; ptr=ptr.next){
            System.out.println(ptr.num);
        }
        //assertTrue(false);
    }
}