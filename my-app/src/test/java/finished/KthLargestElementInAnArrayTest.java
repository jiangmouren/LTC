package finished;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 8/16/17.
 */
public class KthLargestElementInAnArrayTest {
    KthLargestElementInAnArray objectUnderTest = new KthLargestElementInAnArray();
    int[] array = {3, 2, 1, 5, 6, 4};
    @Test
    public void kthLargest() throws Exception {
        assertEquals(objectUnderTest.kthLargest(2, array), 5);
    }

    @Test
    public void kthSmallest() throws Exception {
        assertEquals(objectUnderTest.kthSmallest(2, array), 2);
    }

}