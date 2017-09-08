package finished.bit_manipulation;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/6/17.
 */
public class SingleNumberTest {
    SingleNumber objectUnderTest = new SingleNumber();
    @Test
    public void singleNumber() throws Exception {
        int nums1[] = {0, 0, 1, 2, 2};
        int nums2[] = {2};
        int nums3[] = {23, 23, 11, 11, 13, 14, 99, 14, 99};
        int result1 = 1;
        int result2 = 2;
        int result3 = 13;
        assertTrue(result1==objectUnderTest.singleNumber(nums1));
        assertTrue(result2==objectUnderTest.singleNumber(nums2));
        assertTrue(result3==objectUnderTest.singleNumber(nums3));
    }

}