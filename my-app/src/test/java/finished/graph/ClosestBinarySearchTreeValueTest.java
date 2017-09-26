package finished.graph;

import finished.graph.ClosestBinarySearchTreeValue;
import org.junit.Test;
import finished.graph.ClosestBinarySearchTreeValue.*;

/**
 * Created by eljian on 7/13/2017.
 */
public class ClosestBinarySearchTreeValueTest {
    ClosestBinarySearchTreeValue objectUnderTest = new ClosestBinarySearchTreeValue();
    TreeNode root = new TreeNode(10);
    {
        root.left = new TreeNode(5);
        root.right = new TreeNode(20);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(8);
        root.left.right.left = new TreeNode(6);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(22);
    }
    @Test
    public void find() throws Exception {
        int result1 = objectUnderTest.find(root, 5.6f);
        System.out.println(result1);
        int result2 = objectUnderTest.findRecursive(root, 5.6f);
        System.out.println(result2);
    }

}