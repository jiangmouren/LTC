package Finished.arrayANDstring;

import org.junit.Test;

/**
 * Created by eljian on 6/23/2017.
 */
public class RotateArrayTest {
    RotateArray objectUnderTest = new RotateArray();
    int[] nums = {1,2,3,4,5,6,7};
    @Test
    public void rotateArray() throws Exception {
        objectUnderTest.rotateArray2(nums, 3);
        for(int tmp : nums){
            System.out.print(tmp);
        }
    }

}