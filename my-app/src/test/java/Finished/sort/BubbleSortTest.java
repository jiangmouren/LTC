package Finished.sort;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/16/2017.
 */
public class BubbleSortTest {
    BubbleSort objectUnderTest = new BubbleSort();
    int[] nums1 = {0};
    int[] nums2 = {0, 3, 5, 2};
    int[] nums3 = {6, 3, 2, 1};
    int[] result1 = {0};
    int[] result2 = {0, 2, 3, 5};
    int[] result3 = {1, 2, 3, 6};
    @Test
    public void bubbleSort() throws Exception {
        assertTrue(Arrays.equals(result1, objectUnderTest.bubbleSort(nums1)));
        assertTrue(Arrays.equals(result2, objectUnderTest.bubbleSort(nums2)));
        assertTrue(Arrays.equals(result3, objectUnderTest.bubbleSort(nums3)));
    }
}