package com.mycompany.app;

/**
 * https://leetcode.com/problems/merge-sorted-array/
 * Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
 *
 * The number of elements initialized in nums1 and nums2 are m and n respectively. You may assume that nums1 has a size equal to m + n such that it has enough space to hold additional elements from nums2.
 *
 * Example 1:
 * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * Output: [1,2,2,3,5,6]
 *
 * Example 2:
 * Input: nums1 = [1], m = 1, nums2 = [], n = 0
 * Output: [1]
 *
 * Constraints:
 * nums1.length == m + n
 * nums2.length == n
 * 0 <= m, n <= 200
 * 1 <= m + n <= 200
 * -109 <= nums1[i], nums2[i] <= 109
 */

public class MergeSortedArray{
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        //我觉得要领就是从后往前
        int ptr1 = m-1;
        int ptr2 = n-1;
        int ptr = nums1.length-1;
        while(ptr1>=0 && ptr2>=0){
            if(nums1[ptr1]>=nums2[ptr2]){
                nums1[ptr] = nums1[ptr1];
                ptr1--;
            }
            else{
                nums1[ptr] = nums2[ptr2];
                ptr2--;
            }
            ptr--;
        }
        //if ptr1 reaches 0 before ptr2, need to put remaining nums2 entries into nums1
        //else no need to do anything
        if(ptr2>=0){
            while(ptr2>=0){
                nums1[ptr] = nums2[ptr2];
                ptr2--;
                ptr--;
            }
        }
    }
}