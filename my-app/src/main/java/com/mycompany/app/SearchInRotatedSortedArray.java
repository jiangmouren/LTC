package com.mycompany.app;

/**
 * https://leetcode.com/problems/search-in-rotated-sorted-array/
 * You are given an integer array nums sorted in ascending order (with distinct values), and an integer target.
 * Suppose that nums is rotated at some pivot unknown to you beforehand (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
 * If target is found in the array return its index, otherwise, return -1.
 *
 * Example 1:
 * Input: nums = [4,5,6,7,0,1,2], target = 0
 * Output: 4
 *
 * Example 2:
 * Input: nums = [4,5,6,7,0,1,2], target = 3
 * Output: -1
 *
 * Example 3:
 * Input: nums = [1], target = 0
 * Output: -1
 *
 *
 * Constraints:
 * 1 <= nums.length <= 5000
 * -104 <= nums[i] <= 104
 * All values of nums are unique.
 * nums is guaranteed to be rotated at some pivot.
 * -104 <= target <= 104
 */

public class SearchInRotatedSortedArray {
    public int search(int[] nums, int target) {
        return searchHelper(nums, target, 0, nums.length-1);
    }

    private int searchHelper(int[] nums, int target, int left, int right){
        //base case
        if(right < left){
            return -1;
        }
        int midIdx = (left + right) / 2;
        //case 0: found at mid
        if(nums[midIdx] == target){
            return midIdx;
        }
        //case 1: ordered
        else if(nums[left]<nums[right]){
            //out of range, this is important to maintain O(lgn) complexity.
            if(nums[left]>target || nums[right]<target){
                return -1;
            }

            if(nums[midIdx] > target){
                return searchHelper(nums, target, left, midIdx-1);
            }
            else{
                return searchHelper(nums, target, midIdx+1, right);
            }
        }
        //case 2: cross pivot
        else{
            //Always at least one side is ordered. I want to search that side first, so I can either found it or early terminate.
            if(nums[midIdx]>nums[left]){
                //pivot on right part
                int leftResult = searchHelper(nums, target, left, midIdx-1);
                if(leftResult == -1){
                    return searchHelper(nums, target, midIdx+1, right);
                }
                return leftResult;
            }
            else{
                //pivot on left part
                int rightResult = searchHelper(nums, target, midIdx+1, right);
                if(rightResult==-1){
                    return searchHelper(nums, target, left, midIdx-1);
                }
                return rightResult;
            }
        }
    }
}
