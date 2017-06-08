package com.mycompany.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/7/2017.
 */
public class InvertBinaryTreeTest {
    InvertBinaryTree objectUnderTest = new InvertBinaryTree();
    @Test
    public void invertTree() throws Exception {
        InvertBinaryTree.TreeNode root = new InvertBinaryTree.TreeNode(4);
        root.left = new InvertBinaryTree.TreeNode(7);
        root.right = new InvertBinaryTree.TreeNode(2);
        root.left.left = new InvertBinaryTree.TreeNode(9);
        root.left.right = new InvertBinaryTree.TreeNode(6);
        root.right.left = new InvertBinaryTree.TreeNode(3);
        root.right.right = new InvertBinaryTree.TreeNode(1);
        inOderTree(root);
        inOderTree(objectUnderTest.invertTree(root));
    }
    //Use an in-order traversal to test, easy to identify, because result would be symmetric
    public void inOderTree(InvertBinaryTree.TreeNode root){
        //Termination Conditions
        if(root==null) return;
        inOderTree(root.left);
        System.out.print(root.val);
        inOderTree(root.right);
    }

}