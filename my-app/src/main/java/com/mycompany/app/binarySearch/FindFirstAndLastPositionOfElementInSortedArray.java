package com.mycompany.app.binarySearch;

/**
 * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
 * Given an array of integers nums sorted in ascending order, find the starting and ending position of a given target value.
 * If target is not found in the array, return [-1, -1].
 * Follow up: Could you write an algorithm with O(log n) runtime complexity?
 *
 * Example 1:
 * Input: nums = [5,7,7,8,8,10], target = 8
 * Output: [3,4]
 *
 * Example 2:
 * Input: nums = [5,7,7,8,8,10], target = 6
 * Output: [-1,-1]
 *
 * Example 3:
 * Input: nums = [], target = 0
 * Output: [-1,-1]
 *
 * Constraints:
 * 0 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * nums is a non-decreasing array.
 * -10^9 <= target <= 10^9
 */

//典型的BinarySearch找最左，或者最右的问题
public class FindFirstAndLastPositionOfElementInSortedArray {
    public int[] searchRange(int[] nums, int target) {
        int[] res = new int[2];
        int left = search(nums, target, 0, nums.length-1, true);
        int right = search(nums, target, 0, nums.length-1, false);
        res[0] = left;
        res[1] = right;
        return res;
    }

    private int search(int[] nums, int target, int start, int end, boolean findLeft){
        //termination
        if(start>end){
            return -1;
        }

        int mid = (start + end) / 2;
        if(nums[mid]<target){
            return search(nums, target, mid+1, end, findLeft);
        }
        else if(nums[mid]>target){
            return search(nums, target, start, mid-1, findLeft);
        }
        else{
            int partialRes = -1;
            if(findLeft){
                partialRes = search(nums, target, start, mid-1, findLeft);
            }
            else{
                partialRes = search(nums, target, mid+1, end, findLeft);
            }
            if(partialRes!=-1){
                return partialRes;
            }
            else{
                return mid;
            }
        }
    }
}
