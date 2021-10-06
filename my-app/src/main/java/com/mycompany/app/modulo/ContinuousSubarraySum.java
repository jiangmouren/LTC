package com.mycompany.app.modulo;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/continuous-subarray-sum/
 * Given an integer array nums and an integer k, return true if nums has a continuous subarray of size at least two whose elements sum up to a multiple of k, or false otherwise.
 * An integer x is a multiple of k if there exists an integer n such that x = n * k. 0 is always a multiple of k.
 *
 * Example 1:
 * Input: nums = [23,2,4,6,7], k = 6
 * Output: true
 * Explanation: [2, 4] is a continuous subarray of size 2 whose elements sum up to 6.
 *
 * Example 2:
 * Input: nums = [23,2,6,4,7], k = 6
 * Output: true
 * Explanation: [23, 2, 6, 4, 7] is an continuous subarray of size 5 whose elements sum up to 42.
 * 42 is a multiple of 6 because 42 = 7 * 6 and 7 is an integer.
 *
 * Example 3:
 * Input: nums = [23,2,6,4,7], k = 13
 * Output: false
 *
 * Constraints:
 * 1 <= nums.length <= 105
 * 0 <= nums[i] <= 109
 * 0 <= sum(nums[i]) <= 231 - 1
 * 1 <= k <= 231 - 1
 */
public class ContinuousSubarraySum {
    //算是two-sum的一个变种,跟另外一道差值整除题的混合，用到余数Map
    public boolean checkSubarraySum(int[] nums, int k) {
        if(nums.length<2){
            return false;
        }

        int[] prefix = new int[nums.length];
        int temp = 0;
        for(int i=0; i<nums.length; i++){
            temp += nums[i];
            prefix[i] = temp % k;
        }

        Set<Integer> set = new HashSet<>();
        for(int i=1; i<nums.length; i++){
            if(i-2>=0){//注意这个边界条件
                set.add(prefix[i-2]);
            }
            if(prefix[i]==0 || set.contains(prefix[i])){//注意这里check prefix[i]本身为0的情况
                return true;
            }
        }
        return false;
    }
}
