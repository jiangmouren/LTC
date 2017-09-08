package finished.array_and_string.sort;

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

/**
 * Created by eljian on 6/19/2017.
 */
public class QuickSortTest {
    QuickSort objectUnderTest = new QuickSort();
    int[] nums1 = {};
    int[] nums2 = {1, 3, 2};
    int[] nums3 = {3, 2, 1};
    int[] nums4 = {3, 3, 1};
    int[] result2 = {1, 2, 3};
    int[] result3 = {1, 2, 3};
    int[] result4 = {1, 3, 3};
    @Test
    public void quickSort() throws Exception {
        assertTrue(objectUnderTest.quickSort(nums1).length==0);
        assertTrue(Arrays.equals(objectUnderTest.quickSort(nums2), result2));
        //for(int token : objectUnderTest.insertionSort(nums3)){
        //    System.out.println(token);
        //}
        assertTrue(Arrays.equals(objectUnderTest.quickSort(nums3), result3));
        assertTrue(Arrays.equals(objectUnderTest.quickSort(nums4), result4));
    }

}