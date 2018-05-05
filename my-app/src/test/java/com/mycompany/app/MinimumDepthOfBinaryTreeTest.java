package com.mycompany.app;

import com.mycompany.app.MinimumDepthOfBinaryTree;
import org.junit.Test;
import com.mycompany.app.MinimumDepthOfBinaryTree.*;

/**
 * Created by eljian on 7/7/2017.
 */
public class MinimumDepthOfBinaryTreeTest {
    MinimumDepthOfBinaryTree objectUnderTest = new MinimumDepthOfBinaryTree();
    TreeNode root = new TreeNode(0);
    {
        root.left = new TreeNode(1);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(6);
        root.left.left.left = new TreeNode(7);
    }
    @Test
    public void minDepth() throws Exception {
        int result = objectUnderTest.minDepth(root);
        System.out.println(result);
    }
}