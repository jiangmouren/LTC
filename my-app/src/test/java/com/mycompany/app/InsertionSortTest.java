package com.mycompany.app;

import com.mycompany.app.InsertionSort;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

/**
 * Created by eljian on 6/19/2017.
 */
public class InsertionSortTest {
    InsertionSort objectUnderTest = new InsertionSort();
    int[] nums1 = {};
    int[] nums2 = {1, 3, 2};
    int[] nums3 = {3, 2, 1};
    int[] nums4 = {3, 3, 1};
    int[] result2 = {1, 2, 3};
    int[] result3 = {1, 2, 3};
    int[] result4 = {1, 3, 3};
    @Test
    public void insertionSort() throws Exception {
        assertTrue(objectUnderTest.insertionSort(nums1).length==0);
        assertTrue(Arrays.equals(objectUnderTest.insertionSort(nums2), result2));
        //for(int token : objectUnderTest.insertionSort(nums3)){
        //    System.out.println(token);
        //}
        assertTrue(Arrays.equals(objectUnderTest.insertionSort(nums3), result3));
        assertTrue(Arrays.equals(objectUnderTest.insertionSort(nums4), result4));

    }

}