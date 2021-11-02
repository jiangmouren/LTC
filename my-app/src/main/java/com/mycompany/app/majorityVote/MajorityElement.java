package com.mycompany.app.majorityVote;

/**
 * Question: https://leetcode.com/problems/majority-element/
 * Given an array nums of size n, return the majority element.
 * The majority element is the element that appears more than ⌊n / 2⌋ times.
 * You may assume that the majority element always exists in the array.
 *
 * Example 1:
 * Input: nums = [3,2,3]
 * Output: 3
 *
 * Example 2:
 * Input: nums = [2,2,1,1,1,2,2]
 * Output: 2
 *
 * Constraints:
 * n == nums.length
 * 1 <= n <= 5 * 104
 * -231 <= nums[i] <= 231 - 1
 *
 * Follow-up: Could you solve the problem in linear time and in O(1) space?
 */

/**
 * Analysis:
 * This is one of its kind.
 * Most intuitive way would be using Map or Sort.
 * This one can by called "Majority Algorithm", "Vote Algorithm" or "Survivor Algorithm".
 */
public class MajorityElement {
    public int majorityElement(int[] nums) {
        int cnt = 0;
        int res = 0;
        for(int num : nums){
            if(cnt==0){
                res = num;
                cnt++;
            }
            else if(res==num){
                cnt++;
            }
            else{
                cnt--;
            }
        }
        return res;
    }

    /**
     * 下面这个解法是我自己想到的满足要求的解法。
     * 想用O(n)time, 还要O(1)space，只能做quickSelect,而且还不能写recursion
     * 找第n/2个数
     */
    public int majorityElementSln2(int[] nums) {
        int k = nums.length/2 + 1;//用这个来表达过半
        return search(nums, k);
    }

    //quick select
    private int search(int[] nums, int k){
        int left = 0;
        int right = nums.length-1;
        int res = 0;
        while(left<=right){
            int idx = pivot(nums, left, right);
            if(idx==k-1){
                res = nums[idx];
                break;
            }
            else if(idx<k-1){
                left = idx+1;
                continue;
            }
            else{
                right = idx-1;
                continue;
            }
        }
        return res;
    }

    private int pivot(int[] nums, int start, int end){
        int left = start + 1;
        int right = end;
        while(left<=right){
            while(right>=start && nums[right]>nums[start]){
                right--;
            }
            while(left<=end && nums[left]<=nums[start]){
                left++;
            }
            if(left<right){
                swap(nums, left, right);
            }
        }
        swap(nums, start, right);
        return right;
    }

    private void swap(int[] nums, int left, int right){
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
}
