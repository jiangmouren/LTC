package finished.array_and_string.sort;

import org.junit.Test;

/**
 * Created by eljian on 6/27/2017.
 */
public class RadixSortTest {
    RadixSort objectUnderTest = new RadixSort();
    int[] nums1 = {2, 10, 11, 223, 8};
    int[] nums2 = {223, 11, 10, 8, 2};
    @Test
    public void radixSort() throws Exception {
        objectUnderTest.radixSort(nums1);
        objectUnderTest.radixSort(nums2);
        printArray(nums1);
        printArray(nums2);
    }

    private void printArray(int[] nums){
        for(int num : nums){
            System.out.print(num + ", ");
        }
        System.out.println();
    }

}