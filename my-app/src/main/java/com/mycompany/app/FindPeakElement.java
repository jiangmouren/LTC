package com.mycompany.app;

/**
 * https://leetcode.com/problems/find-peak-element/
 * A peak element is an element that is strictly greater than its neighbors.
 * Given an integer array nums, find a peak element, and return its index.
 * If the array contains multiple peaks, return the index to any of the peaks.
 * You may imagine that nums[-1] = nums[n] = -∞.
 * You must write an algorithm that runs in O(log n) time.
 *
 * Example 1:
 * Input: nums = [1,2,3,1]
 * Output: 2
 * Explanation: 3 is a peak element and your function should return the index number 2.
 *
 * Example 2:
 * Input: nums = [1,2,1,3,5,6,4]
 * Output: 5
 * Explanation: Your function can return either index number 1 where the peak element is 2, or index number 5 where the peak element is 6.
 *
 * Constraints:
 * 1 <= nums.length <= 1000
 * -231 <= nums[i] <= 231 - 1
 * nums[i] != nums[i + 1] for all valid i.
 */

//这个题目里下面几个条件很重要：
//1. nums[-1] = nums[n] = -∞
//2. nums[i] != nums[i + 1] for all valid i
//这两个条件保证我的区间外的左侧是一个strictly ascending slope，而区间外的右侧是一个stricaly descending slope
//所以我对mid，只要跟两侧的nbor比一下(不会出现相等的情况):
//           1. mid比两边邻居都大,那么mid is peak
//           2. 如果任意一侧的邻居小，那么在那一侧一定有一个peak，可以相应的移动mid
//           3. 移动完新的区间，依然保持原区间的特性，既两个外侧都是严格下降的
public class FindPeakElement{
    public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length-1;
        int res = left;

        while(left<=right){
            int mid = (left + right)/2;
            if((mid-1<left || nums[mid]>nums[mid-1]) && (mid+1>right || nums[mid]>nums[mid+1])){
                res = mid;
                break;
            }
            else if(mid-1>=left && nums[mid]<nums[mid-1]){//题目条件说nums[i] != nums[i + 1]，所以这里不需要考虑等号
                right = mid - 1;
                continue;
            }
            else{
                left = mid + 1;
                continue;
            }
        }

        return res;
    }
}
