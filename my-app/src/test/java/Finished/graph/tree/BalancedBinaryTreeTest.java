package Finished.graph.tree;

import Finished.graph.tree.BalancedBinaryTree;
import org.junit.Test;

import static org.junit.Assert.*;
import Finished.graph.tree.BalancedBinaryTree.*;

/**
 * Created by eljian on 6/13/2017.
 */
public class BalancedBinaryTreeTest {
    TreeNode root0 = null;
    TreeNode root1 = new TreeNode(0);
    TreeNode root2 = new TreeNode(0);
    {
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(2);
        root1.left.left = new TreeNode(3);
        root1.left.right = new TreeNode(4);
        root1.right.left = new TreeNode(5);
        root1.left.right.left = new TreeNode(6);
        root1.right.left.right = new TreeNode(7);

        root2.left = new TreeNode(1);
        root2.right = new TreeNode(2);
        root2.left.left = new TreeNode(3);
        root2.left.right = new TreeNode(4);
        root2.right.left = new TreeNode(5);
        root2.left.right.left = new TreeNode(6);
        root2.right.right = new TreeNode(7);
    }

    BalancedBinaryTree objectUnderTest = new BalancedBinaryTree();
    @Test
    public void balanced() throws Exception {
        assertTrue(objectUnderTest.balanced(root0));
        assertFalse(objectUnderTest.balanced(root1));
        assertTrue(objectUnderTest.balanced(root2));
    }

}