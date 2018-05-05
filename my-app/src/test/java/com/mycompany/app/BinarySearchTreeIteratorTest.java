package com.mycompany.app;

import com.mycompany.app.BinarySearchTreeIterator;
import org.junit.Test;
import com.mycompany.app.BinarySearchTreeIterator.*;

/**
 * Created by eljian on 6/27/2017.
 */
public class BinarySearchTreeIteratorTest {
    Node root = new Node(6);
    {
        root.left = new Node(4);
        root.right = new Node(8);
        root.left.left = new Node(2);
        root.left.right = new Node(5);
        root.right.left = new Node(7);
        root.right.right = new Node(9);
    }
    BinarySearchTreeIterator objectUnderTest = new BinarySearchTreeIterator(root);

    @Test
    public void test() throws Exception {
        while(objectUnderTest.hasNext()){
            System.out.println(objectUnderTest.next().val);
        }
    }
}