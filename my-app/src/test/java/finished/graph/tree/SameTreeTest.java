package finished.graph.tree;

import org.junit.Test;
import finished.graph.tree.SameTree.*;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/10/17.
 */
public class SameTreeTest {
    SameTree objectUnderTest = new SameTree();
    TreeNode root1 = new TreeNode(0);
    TreeNode root2 = new TreeNode(0);
    {
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(2);
        root1.left.left = new TreeNode(3);
        root1.left.right = new TreeNode(4);
        root1.right.left = new TreeNode(5);
        root1.right.right = new TreeNode(6);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(2);
        root2.left.left = new TreeNode(3);
        root2.left.right = new TreeNode(7);
        root2.right.left = new TreeNode(5);
        root2.right.right = new TreeNode(6);
    }
    @Test
    public void isSameTree() throws Exception {
        assertTrue(objectUnderTest.isSameTree(root1, root1));
        assertFalse(objectUnderTest.isSameTree(root1, root2));
    }

}