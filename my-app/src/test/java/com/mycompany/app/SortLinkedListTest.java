package com.mycompany.app;
import com.mycompany.app.SortLinkedList.*;
import org.junit.Test;

public class SortLinkedListTest {
    LinkedNode node0 = new LinkedNode(0);
    LinkedNode node1 = new LinkedNode(4);
    LinkedNode node2 = new LinkedNode(2);
    LinkedNode node3 = new LinkedNode(10);
    LinkedNode node4 = new LinkedNode(10);
    LinkedNode node5 = new LinkedNode(5);
    LinkedNode node6 = new LinkedNode(1);
    {
        node0.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
    }

    @Test
    public void sort(){
        SortLinkedList obj = new SortLinkedList();
        LinkedNode result = obj.sort(node0);
        while(result != null){
            System.out.println(result.data);
            result = result.next;
        }
    }
}
