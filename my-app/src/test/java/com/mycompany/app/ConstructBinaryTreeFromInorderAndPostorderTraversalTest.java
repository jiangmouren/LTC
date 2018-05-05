package com.mycompany.app;

import com.mycompany.app.ConstructBinaryTreeFromInorderAndPostorderTraversal;
import org.junit.Test;
import com.mycompany.app.ConstructBinaryTreeFromInorderAndPostorderTraversal.*;

/**
 * Say we have the following inputs:
 *         0
 *      1     2
 *    3   4  5  6
 * So the inorder = {3, 1, 4, 0, 5, 2, 6};
 * the postorder = {3, 4, 1, 5, 6, 2, 0};
 */
public class ConstructBinaryTreeFromInorderAndPostorderTraversalTest {
    ConstructBinaryTreeFromInorderAndPostorderTraversal obj = new ConstructBinaryTreeFromInorderAndPostorderTraversal();
    int[] inorder = {3, 1, 4, 0, 5, 2, 6};
    int[] postorder = {3, 4, 1, 5, 6, 2, 0};
    @Test
    public void buildTree() throws Exception {
        TreeNode root = obj.buildTree(inorder, postorder);
        printTree(root);
    }

    private void printTree(TreeNode root){
        //base cases
        if(root==null){
            return;
        }
        //recursion case
        printTree(root.left);
        printTree(root.right);
        System.out.print(root.val);
    }
}