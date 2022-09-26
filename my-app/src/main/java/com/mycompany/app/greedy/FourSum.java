package com.mycompany.app.greedy;
import java.util.*;

/**
 * https://leetcode.com/problems/4sum/
 * Given an array nums of n integers and an integer target,
 * are there elements a, b, c, and d in nums such that a + b + c + d = target?
 * Find all unique quadruplets in the array which gives the sum of target.
 * Notice that the solution set must not contain duplicate quadruplets.
 *
 * Example 1:
 * Input: nums = [1,0,-1,0,-2,2], target = 0
 * Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 *
 * Example 2:
 * Input: nums = [], target = 0
 * Output: []
 *
 *
 * Constraints:
 * 0 <= nums.length <= 200
 * -10^9 <= nums[i] <= 10^9
 * -10^9 <= target <= 10^9
 */
public class FourSum {
    /**
     * 下面这种是在之前的3Sum的基础上演化出来的可以解决K-sum问题的方法
     * 下面这种解法的时间复杂度是N^(k-1)，原因是从O(k) = n * O(k-1) = n * n * ...O(2)
     * 前面一共有(k-2)个n相乘，所以是n^(k-2)，而O(2) = n，所以总的复杂度是O(n^(k-1))
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        return kSum(nums, target, 4, 0);
    }

    //注意防止溢出，所以target跟sum都加强成long，因为题目里nums[i]的范围可以到10^9，就已经到了溢出的边缘
    private List<List<Integer>> kSum(int[] nums, long target, int k, int start){
        List<List<Integer>> res = new ArrayList<>();
        if(k==2){//termination case
            int left = start;
            int right = nums.length - 1;
            while(left<right){
                long sum = nums[left] + nums[right];
                if(sum==target){
                    List<Integer> buf = new ArrayList<>();
                    buf.add(nums[left]);
                    buf.add(nums[right]);
                    res.add(buf);
                    left++;
                    while(left<right && nums[left]==nums[left-1]){
                        left++;
                    }
                }
                else if(sum<target){
                    left++;
                }
                else{
                    right--;
                }
            }
        }
        else{//recursion case
            for(int i=start; i<nums.length; i++){
                if(i==start || nums[i]!=nums[i-1]){
                    List<List<Integer>> partialRes = kSum(nums, target-nums[i], k-1, i+1);
                    for(List<Integer> list : partialRes){
                        list.add(nums[i]);
                        res.add(list);
                    }
                }
            }
        }
        return res;
    }
}
