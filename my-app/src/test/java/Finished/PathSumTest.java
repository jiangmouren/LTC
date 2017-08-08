package Finished;

import org.junit.Test;
import Finished.PathSum.*;
import static org.junit.Assert.*;

/**
 * Created by eljian on 8/3/2017.
 */

public class PathSumTest {
    PathSum objectUnderTest = new PathSum();
    TreeNode root = new TreeNode(5);
    {
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.right = new TreeNode(1);
    }
    @Test
    public void find() throws Exception {
        assertTrue(objectUnderTest.find(root, 22));
    }

}
