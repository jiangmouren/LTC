package com.mycompany.app;

import com.mycompany.app.InorderSuccessorInBST;
import org.junit.Test;
import com.mycompany.app.InorderSuccessorInBST.*;
import static org.junit.Assert.*;

/**
 * Created by eljian on 7/20/2017.
 */
public class InorderSuccessorInBSTTest {
    InorderSuccessorInBST objectUnderTest = new InorderSuccessorInBST();
    TreeNode root = new TreeNode(10);
    {
        root.left = new TreeNode(6);
        root.right = new TreeNode(20);
        root.left.left = new TreeNode(5);
        root.left.right = new TreeNode(8);
        root.right.left = new TreeNode(12);
        root.right.right = new TreeNode(23);
    }

    @Test
    public void successor() throws Exception {
        int target1 = 8;
        int target2 = 23;
        TreeNode result1 = objectUnderTest.successor(root, 8);
        TreeNode result2 = objectUnderTest.successor(root, 23);
        assertEquals(result1.val, 10);
        assertNull(result2);
    }

}