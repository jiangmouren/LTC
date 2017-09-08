package finished;

import org.junit.Test;
import finished.BinaryTreeTraversal.*;
import java.util.*;

/**
 * Created by eljian on 8/1/2017.
 */
public class BinaryTreeTraversalTest {

    private BinaryTreeTraversal objectUnderTest = new BinaryTreeTraversal();
    TreeNode root = new TreeNode(14);
    {
        root.left = new TreeNode(8);
        root.right = new TreeNode(20);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(9);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(18);
        root.left.right.right = new TreeNode(11);
    }

    @Test
    public void preOrder1() throws Exception {
        printList(objectUnderTest.preOrder1(root));
        System.out.println();
    }

    @Test
    public void preOrder2() throws Exception {
        printList(objectUnderTest.preOrder2(root));
        System.out.println();
    }

    @Test
    public void preOrder3() throws Exception {
        printList(objectUnderTest.preOrder3(root));
        System.out.println();
    }

    @Test
    public void inOrder1() throws Exception {
        printList(objectUnderTest.inOrder1(root));
        System.out.println();
    }

    @Test
    public void inOrder2() throws Exception {
        printList(objectUnderTest.inOrder2(root));
        System.out.println();
    }

    @Test
    public void postOrder1() throws Exception {
        printList(objectUnderTest.postOrder1(root));
        System.out.println();
    }

    @Test
    public void postOrder2() throws Exception {
        printList(objectUnderTest.postOrder2(root));
        System.out.println();
    }

    @Test
    public void postOrder3() throws Exception {
        printList(objectUnderTest.postOrder3(root));
        System.out.println();
    }

    @Test
    public void postOrder4() throws Exception {
        printList(objectUnderTest.postOrder4(root));
        System.out.println();
    }

    private void printList(List<TreeNode> list){
        for(TreeNode node : list){
            System.out.print(node.val + ", ");
        }
    }

}
