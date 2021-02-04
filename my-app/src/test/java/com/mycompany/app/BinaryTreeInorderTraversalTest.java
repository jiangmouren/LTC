package com.mycompany.app;

import com.mycompany.app.BinaryTreeInorderTraversal;
import org.junit.Test;
import com.mycompany.app.BinaryTreeInorderTraversal.*;
import java.util.*;

/**
 * Created by eljian on 6/20/2017.
 */
public class BinaryTreeInorderTraversalTest {
    BinaryTreeInorderTraversal objectUnderTest = new BinaryTreeInorderTraversal();
    TreeNode root = new TreeNode(1);
    {
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
    }
    //Expected result: 42513
    /*
    @Test
    public void iterativeInorderTraversal() throws Exception {
        printList(objectUnderTest.iterativeInorderTraversal(root));
        System.out.println();
    }

    @Test
    public void recursiveInorderTraversal() throws Exception {
        printList(objectUnderTest.recursiveInorderTraversal(root));
        System.out.println();
    }

    @Test
    public void recursiveInorderTraversal2() throws Exception {
        printList(objectUnderTest.recursiveInorderTraversal2(root));
        System.out.println();
    }

    private void printList(List<Integer> list){
        for(int token : list){
            System.out.print(token);
        }
    }
     */

}