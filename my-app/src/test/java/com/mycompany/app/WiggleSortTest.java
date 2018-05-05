package com.mycompany.app;

import com.mycompany.app.WiggleSort;
import org.junit.Test;

/**
 * Created by eljian on 8/4/2017.
 */
public class WiggleSortTest {
    WiggleSort objectUnderTest = new WiggleSort();
    int[] nums = {3, 5, 2, 1, 6, 4};
    @Test
    public void wiggleSort() throws Exception {
        objectUnderTest.wiggleSort(nums);
        printList(nums);
    }

    private void printList(int[] nums){
        for(int num : nums){
            System.out.print(num + ", ");
        }
    }

}