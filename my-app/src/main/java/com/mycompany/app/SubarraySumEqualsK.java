package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/subarray-sum-equals-k/
 * Given an array of integers nums and an integer k, return the total number of continuous subarrays whose sum equals to k.
 *
 * Example 1:
 * Input: nums = [1,1,1], k = 2
 * Output: 2
 *
 * Example 2:
 * Input: nums = [1,2,3], k = 3
 * Output: 2
 *
 * Constraints:
 * 1 <= nums.length <= 2 * 104
 * -1000 <= nums[i] <= 1000
 * -107 <= k <= 107
 */

public class SubarraySumEqualsK {
    public int subarraySum(int[] nums, int k) {
        //这个题可以看成是Two-Sum的变种
        //Instead of cache nums[i] in hashmap, cache sum[i], where sum[i] is the prefix sum of nums[0:1]
        //因为sum[i:j] = sum[j]-sum[i]，而sum[i]又可以被constructed as you go，所以整个就可以用O(n)的complexity解决

        int sum = 0;
        int cnt = 0;
        //key: sum; value: # of prefixes with given sum
        Map<Integer, Integer> map = new HashMap<>();
        //记得把左侧的边界情况处理了
        map.put(0, 1);
        for(int num : nums){
            sum += num;
            int target = sum - k;
            if(map.containsKey(target)){
                int val = map.get(target);
                cnt += val;
            }
            if(map.containsKey(sum)){
                int val = map.get(sum);
                map.put(sum, ++val);
            }
            else{
                map.put(sum, 1);
            }
        }
        return cnt;
    }
}
