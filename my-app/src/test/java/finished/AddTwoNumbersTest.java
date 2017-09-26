package finished;

import org.junit.Test;
import finished.AddTwoNumbers.*;

/**
 * Created by eljian on 9/14/2017.
 */
public class AddTwoNumbersTest {
    AddTwoNumbers obj = new AddTwoNumbers();
    ListNode l1 = new ListNode(2);
    ListNode l2 = new ListNode(5);
    {
        l1.next = new ListNode(4);
        l2.next = new ListNode(6);
        l1.next.next = new ListNode(3);
        l2.next.next = new ListNode(4);
    }
    @Test
    public void addTwoNumbers() throws Exception {
        ListNode result = obj.addTwoNumbers(l1, l2);
        for(ListNode ptr=result; ptr!=null; ptr=ptr.next){
            System.out.println(ptr.val);
        }
    }
}