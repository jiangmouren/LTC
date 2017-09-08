package finished.dp;

import org.junit.Test;

/**
 * Created by eljian on 6/22/2017.
 */
public class MaximumSubarrayTest {
    MaximumSubarray objectUnderTest = new MaximumSubarray();
    int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
    @Test
    public void maxmumSubarray() throws Exception {
        int result = objectUnderTest.maxmumSubarray(nums);
        System.out.print(result);
    }

}