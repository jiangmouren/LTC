package com.mycompany.app;

import com.mycompany.app.FlattenBinaryTreeToLinkedList;
import org.junit.Test;
import com.mycompany.app.FlattenBinaryTreeToLinkedList.*;

/**
 * Created by eljian on 8/3/2017.
 */
public class FlattenBinaryTreeToLinkedListTest {
    FlattenBinaryTreeToLinkedList objectUderTest = new FlattenBinaryTreeToLinkedList();
    @Test
    public void flat1() throws Exception {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(6);
        printLinkedList(objectUderTest.flat1(root));
    }

    @Test
    public void flat2() throws Exception {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(6);
        printLinkedList(objectUderTest.flat2(root));
    }

    private void printLinkedList(TreeNode root){
        TreeNode ptr = root;
        while(ptr!=null){
            System.out.print(ptr.val + ", ");
            ptr = ptr.right;
        }
    }

}