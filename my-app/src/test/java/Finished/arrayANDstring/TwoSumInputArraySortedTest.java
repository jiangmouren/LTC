package Finished.arrayANDstring;

import Finished.arrayANDstring.TwoSumInputArraySorted;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/10/17.
 */
public class TwoSumInputArraySortedTest {
    TwoSumInputArraySorted objectUnderTest = new TwoSumInputArraySorted();
    int[] numbers = {1, 2, 4, 6, 9};
    int target = 8;
    @Test
    public void twoSum() throws Exception {
        int[] result = objectUnderTest.twoSum(numbers, target);
        assertTrue(result[0]==2);
        assertTrue(result[1]==4);
    }

}