package com.mycompany.app;
import com.mycompany.app.ValidateBST.*;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidateBstTest {
    //     20    //
    //    /      //
    //  20       //
    Node root1 = new Node(20);
    {
        root1.left = new Node(20);
    }
    //   20      //
    //     \     //
    //      20   //
    Node root2 = new Node(20);
    {
        root2.right = new Node(20);
    }
    //       20      //
    //     /    \    //
    //  10        30 //
    //    \          //
    //     25        //
    Node root3 = new Node(20);
    {
        root3.left = new Node(10);
        root3.left.right = new Node(25);
        root3.right = new Node(30);
    }
    //             20       //
    //           /    \     //
    //        10        30  //
    //      /    \          //
    //    5        15       //
    //  /   \        \      //
    // 3     7       17     //
    Node root4 = new Node(20);
    {
        root4.left = new Node(10);
        root4.left.left = new Node(5);
        root4.left.left.left = new Node(3);
        root4.left.left.right = new Node(7);
        root4.left.right = new Node(15);
        root4.left.right.right = new Node(17);
        root4.right = new Node(30);
    }
    ValidateBST obj = new ValidateBST();
    @Test
    public void ValidateBstTest(){
        //test InOrder
        assertFalse(obj.validateInOrder(root1));
        assertFalse(obj.validateInOrder(root2));
        assertFalse(obj.validateInOrder(root3));
        assertTrue(obj.validateInOrder(root4));
        //test InOrderImproved
        assertTrue(obj.validateInOrderImproved(root1));
        assertFalse(obj.validateInOrderImproved(root2));
        assertFalse(obj.validateInOrderImproved(root3));
        assertTrue(obj.validateInOrderImproved(root4));
        //test PreOrder
        assertTrue(obj.validatePreOrder(root1));
        assertFalse(obj.validatePreOrder(root2));
        assertFalse(obj.validatePreOrder(root3));
        assertTrue(obj.validatePreOrder(root4));
        //test PostOrder
        assertTrue(obj.validatePostOrder(root1));
        assertFalse(obj.validatePostOrder(root2));
        assertFalse(obj.validatePostOrder(root3));
        assertTrue(obj.validatePostOrder(root4));
    }
}
