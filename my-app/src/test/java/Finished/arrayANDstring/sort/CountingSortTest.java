package Finished.arrayANDstring.sort;

import org.junit.Test;

/**
 * Created by eljian on 6/20/2017.
 */
public class CountingSortTest {
    CountingSort objectUnderTest = new CountingSort();
    int[] nums = {2, 0, 1, 4};
    @Test
    public void countingSort() throws Exception {
        objectUnderTest.countingSort(nums);
        for(int token : nums){
            System.out.println(token);
        }

    }

}