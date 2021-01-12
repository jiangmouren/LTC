package com.mycompany.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 * 因为要么找不到，要么找到了一定跟nums[i]找到的一样。原因如下：
 * 假设nums[i]的值是A，因为nums[i+1]所寻找的区间，是nums[i]所寻找区间的一个子集，
 * 所以如果所找到的这3个数当中只含有一个A，那么nums[i+1]所找到的解也一定与nums[i]所找到的相同。
 * 如果含有2个A，那么从数学上，3个数的和确定，两个数确定了，第3个数一定也是定的，所以必定还是一样的。
 * 同理，如果含有3个A，必然也还是一样的。所以避免重复的方法就是，相同的数字，值查询一次。
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