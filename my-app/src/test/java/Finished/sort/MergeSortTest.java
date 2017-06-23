package Finished.sort;

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/18/17.
 */
public class MergeSortTest {
    MergeSort objectUnderTest = new MergeSort();
    int[] nums1 = {1, 4, 2, 9, 7};
    int[] nums2 = {1, 2, 2, 9, 7};
    int[] nums3 = {9, 7, 4, 2, 1};
    int[] result1 = {1, 2, 4, 7, 9};
    int[] result2 = {1, 2, 2, 7, 9};
    int[] result3 = {1, 2, 4, 7, 9};

    @Test
    public void mergeSort1() throws Exception {
        //int[] tmp = objectUnderTest.mergeSort1(nums1);
        //for(int token : tmp){
        //    System.out.println(token);
        //}
        assertTrue(Arrays.equals(result1, objectUnderTest.mergeSort2(nums1)));
        assertTrue(Arrays.equals(result2, objectUnderTest.mergeSort2(nums2)));
        assertTrue(Arrays.equals(result3, objectUnderTest.mergeSort2(nums3)));
    }

}