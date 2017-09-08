package finished.array_and_string;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/13/17.
 * array1.equals(array2) is the same as array1 == array2
 * Arrays.equals(array1, array2) compares the contents of the arrays.
 */
public class TwoSumTest {
    TwoSum objectUnderTest = new TwoSum();
    int[] nums = {2, 7, 11, 15};
    int target = 9;
    int[] result = {0, 1};
    @Test
    public void twoSum() throws Exception {
        int[] tmp = objectUnderTest.twoSum(nums, target);
        System.out.println(tmp[0]);
        System.out.println(tmp[1]);

        assertTrue(Arrays.equals(objectUnderTest.twoSum(nums, target), result));
        //array1.equals(array2) is the same as array1 == array2, i.e. is it the same array. As @alf points out it's not what most people expect.
        //Arrays.equals(array1, array2) compares the contents of the arrays.
        //assertTrue(objectUnderTest.twoSum(nums, target).equals(result));
    }

}