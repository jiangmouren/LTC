package com.mycompany.app;

import com.mycompany.app.LowestCommonAncestorOfABinaryTree;
import org.junit.Test;
import com.mycompany.app.LowestCommonAncestorOfABinaryTree.*;

/**
 * Created by eljian on 9/19/2017.
 */
public class LowestCommonAncestorOfABinaryTreeTest {
    LowestCommonAncestorOfABinaryTree obj = new LowestCommonAncestorOfABinaryTree();
    TreeNode root = new TreeNode(3);
    TreeNode node1 = new TreeNode(6);
    TreeNode node2 = new TreeNode(4);
    {
        root.left = new TreeNode(5);
        root.left.left = node1;
        root.left.right = new TreeNode(2);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = node2;

        root.right = new TreeNode(1);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
    }
    @Test
    public void lowestCommonAncestor() throws Exception {
        System.out.println(obj.lowestCommonAncestor(root, node1, node2).val);
    }

}