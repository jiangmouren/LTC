package finished.graph.tree;

import org.junit.Test;
import finished.graph.tree.BinaryTreeLevelOrderTraversal.*;
import java.util.*;

/**
 * Created by eljian on 6/23/2017.
 */
public class BinaryTreeLevelOrderTraversalTest {
    BinaryTreeLevelOrderTraversal objectUnderTest = new BinaryTreeLevelOrderTraversal();
    TreeNode root = new TreeNode(3);
    {
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
    }

    @Test
    public void levelOrder() throws Exception {
        List<List<Integer>> result = objectUnderTest.levelOrder(root);
        for(List<Integer> list : result){
            for(int node : list){
                System.out.print(node + ", ");
            }
            System.out.println();
        }
    }

}