package finished;

import org.junit.Test;
import finished.IntersectionOfTwoLinkedLists.*;
import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 8/10/17.
 */
public class IntersectionOfTwoLinkedListsTest {
    Node a1 = new Node(1);
    Node a2 = new Node(2);
    Node b1 = new Node(3);
    Node b2 = new Node(4);
    Node b3 = new Node(5);
    Node c1 = new Node(6);
    Node c2 = new Node(7);
    Node c3 = new Node(8);
    {
        a1.next = a2;
        b1.next = b2;
        a2.next = c1;
        b2.next = b3;
        b3.next = c1;
        c1.next = c2;
        c2.next = c3;
    }
    IntersectionOfTwoLinkedLists objectUnderTest = new IntersectionOfTwoLinkedLists();
    @Test
    public void find() throws Exception {
        assertEquals(objectUnderTest.find(a1, b1).val, 6);
    }

}