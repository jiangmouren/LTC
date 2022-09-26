package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/3sum/
 * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.
 * Notice that the solution set must not contain duplicate triplets.
 *
 * Example 1:
 * Input: nums = [-1,0,1,2,-1,-4]
 * Output: [[-1,-1,2],[-1,0,1]]
 *
 * Example 2:
 * Input: nums = []
 * Output: []
 *
 * Example 3:
 * Input: nums = [0]
 * Output: []
 *
 * Constraints:
 * 0 <= nums.length <= 3000
 * -105 <= nums[i] <= 105
 */

/**
 * Analysis:
 * 依然是一个O(n^2)的解法，但要点在于如何避免使用HashSet来避免重复的问题
 * sort了之后，如果nums[i]==nums[i+1]，那么给nums[i]找过之后，就没必要再给nums[i+1]找了，
 * 因为nums[i]==nums[i+1],而nums[i+1]的搜寻区间是nums[i]的一个子集，所以所能找的所有的解，也是nums[i]所能找到解的一个子集
 * 第二个问题就是，对于sorted list的Two Sum问题从两头往中间找就可以了，不需要HashMap.
 */

public class ThreeSum{
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for(int i=0; i<nums.length; i++){
            if(i==0 || nums[i]!=nums[i-1]){
                int ptr1 = i+1;
                int ptr2 = nums.length-1;
                int target = 0-nums[i];
                while(ptr1<ptr2){
                    if(nums[ptr1]+nums[ptr2]==target){
                        List<Integer> temp = new ArrayList<>();
                        temp.add(nums[i]);
                        temp.add(nums[ptr1]);
                        temp.add(nums[ptr2]);
                        res.add(temp);
                        int ptr3 = ptr1+1;
                        while(ptr3<=ptr2 && nums[ptr3]==nums[ptr1]){
                            ptr3++;
                        }
                        ptr1 = ptr3;
                    }
                    else if(nums[ptr1]+nums[ptr2]<target){
                        ptr1++;
                    }
                    else{
                        ptr2--;
                    }
                }
            }
        }
        return res;
    }
}