package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/k-diff-pairs-in-an-array/
 * Given an array of integers nums and an integer k, return the number of unique k-diff pairs in the array.
 * A k-diff pair is an integer pair (nums[i], nums[j]), where the following are true:
 * 0 <= i, j < nums.length
 * i != j
 * |nums[i] - nums[j]| == k
 * Notice that |val| denotes the absolute value of val.
 *
 * Example 1:
 * Input: nums = [3,1,4,1,5], k = 2
 * Output: 2
 * Explanation: There are two 2-diff pairs in the array, (1, 3) and (3, 5).
 * Although we have two 1s in the input, we should only return the number of unique pairs.
 *
 * Example 2:
 * Input: nums = [1,2,3,4,5], k = 1
 * Output: 4
 * Explanation: There are four 1-diff pairs in the array, (1, 2), (2, 3), (3, 4) and (4, 5).
 *
 * Example 3:
 * Input: nums = [1,3,1,5,4], k = 0
 * Output: 1
 * Explanation: There is one 0-diff pair in the array, (1, 1).
 *
 * Example 4:
 * Input: nums = [1,2,4,4,3,3,0,9,2,3], k = 3
 * Output: 2
 *
 * Example 5:
 * Input: nums = [-1,-2,-3], k = 1
 * Output: 2
 *
 * Constraints:
 * 1 <= nums.length <= 104
 * -107 <= nums[i] <= 107
 * 0 <= k <= 107
 */

public class KDiffPairsInAnArray{
    //主要的注意k==0的情况下出现重复数字的问题，这也是为什么要用多一个set存结果的原因
    //比如：nums={1, 1, 1, 1, 1}, k=0
    public int findPairs(int[] nums, int k) {
        //这里用map而不用set，主要是为了处理nums={1, 1, 1, 1, 1}, k=0 的情况
        Map<Integer, Boolean> map = new HashMap<>();
        int cnt = 0;
        for(int num : nums){
            if(map.containsKey(num)){
                if(k==0 && map.get(num)){
                    cnt++;
                    map.put(num, false);
                }
                continue;
            }

            int key1 = num - k;
            int key2 = num + k;
            if(map.containsKey(key1)){
                cnt++;
            }
            //不用担心k==0的时候重复算两次，如果k==0 && map.containsKey(),在前面已经就被continue了
            if(map.containsKey(key2)){
                cnt++;
            }
            map.put(num, true);
        }
        return cnt;
    }
}
