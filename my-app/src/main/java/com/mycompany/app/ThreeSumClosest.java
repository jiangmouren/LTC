package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/3sum-closest/
 * Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest to target.
 * Return the sum of the three integers. You may assume that each input would have exactly one solution.
 *
 * Example 1:
 * Input: nums = [-1,2,1,-4], target = 1
 * Output: 2
 * Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
 *
 * Constraints:
 * 3 <= nums.length <= 10^3
 * -10^3 <= nums[i] <= 10^3
 * -10^4 <= target <= 10^4
 */

//K-Sum问题的变化
public class ThreeSumClosest {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int ptr = 0;
        int min = Integer.MAX_VALUE;
        int res = 0;
        boolean exit = false;
        while(ptr<nums.length){
            if(ptr==0 || nums[ptr]!=nums[ptr-1]){
                int left = ptr + 1;
                int right = nums.length-1;
                while(left<right){
                    int sum = nums[left] + nums[right];
                    if(sum==target - nums[ptr]){
                        res = target;
                        exit = true;
                        break;
                    }
                    else{
                        if(Math.abs(sum+nums[ptr]-target)<min){
                            min = Math.abs(sum+nums[ptr]-target);
                            res = sum+nums[ptr];
                        }
                        if(sum+nums[ptr]<target){
                            left++;
                        }
                        else{
                            right--;
                        }
                    }
                }
            }
            if(exit){
                break;
            }
            ptr++;
        }
        return res;
    }
}
