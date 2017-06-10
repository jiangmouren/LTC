package Finished;

import Finished.MaximumDepthofBinaryTree;
import org.junit.Test;
import Finished.MaximumDepthofBinaryTree.*;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/8/2017.
 */
public class MaximumDepthofBinaryTreeTest {
    MaximumDepthofBinaryTree objectUnderTest = new MaximumDepthofBinaryTree();
    TreeNode root;
    //Add initializer:
    //We cannot just write non-initializing assignments inside class and outside "code blocks"
    //"code blocks" can be "initializer", "constructor", "method"
    //Refer to: https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
    //https://docs.oracle.com/javase/specs/
    {
        root = new TreeNode(4);
        root.left = new TreeNode(7);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(9);
        root.left.right = new TreeNode(6);
        root.right.left = new TreeNode(3);
        root.right.right = new TreeNode(1);
    }

    @Test
    public void maxDepth1() throws Exception {
        int depth = objectUnderTest.maxDepth1(root);
        assertTrue(depth==3);
    }

    @Test
    public void maxDepth2() throws Exception {
        int depth = objectUnderTest.maxDepth2(root);
        assertTrue(depth==3);
    }

}