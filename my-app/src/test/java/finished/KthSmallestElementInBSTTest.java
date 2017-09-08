package finished;

import org.junit.Test;
import finished.KthSmallestElementInBST.*;
import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 8/10/17.
 *           15
 *      10       25
 *    7    12  16   30
 */
public class KthSmallestElementInBSTTest {
    KthSmallestElementInBST objectUnderTest = new KthSmallestElementInBST();
    Node root = new Node(15);
    {
        root.left = new Node(10);
        root.right = new Node(25);
        root.left.left = new Node(7);
        root.left.right = new Node(12);
        root.right.left = new Node(16);
        root.right.right = new Node(30);
    }
    @Test
    public void find1() throws Exception {
        assertEquals(objectUnderTest.find1(root, 4).val, 15);
        assertEquals(objectUnderTest.find1(root, 1).val, 7);
        assertEquals(objectUnderTest.find1(root, 5).val, 16);
    }

    @Test
    public void find2() throws Exception {
        assertEquals(objectUnderTest.find2(root, 4).val, 15);
        assertEquals(objectUnderTest.find2(root, 1).val, 7);
        assertEquals(objectUnderTest.find2(root, 5).val, 16);
    }
}