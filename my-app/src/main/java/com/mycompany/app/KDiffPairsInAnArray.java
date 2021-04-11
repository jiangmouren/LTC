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
        Set<Integer> set = new HashSet<>();
        //注意可以使用List<Integer>做hashKey，不能用int[]做hashkey,因为前者实际是根据内容求hashcode
        //后者看的就是reference地址。
        Set<List<Integer>> res = new HashSet<>();
        int cnt = 0;
        for(int num : nums){
            if(set.contains(num) && k!=0){
                continue;
            }
            int key1 = num+k;
            int key2 = num-k;
            if(set.contains(key1)){
                //System.out.println(key1);
                List<Integer> temp = new ArrayList<>();
                temp.add(num>key1 ? num : key1);
                temp.add(num>key1 ? key1: num);
                res.add(temp);
            }
            if(k!=0 && set.contains(key2)){
                //System.out.println(key2);
                List<Integer> temp = new ArrayList<>();
                temp.add(num>key2 ? num : key2);
                temp.add(num>key2 ? key2: num);
                res.add(temp);
            }
            set.add(num);
        }
        return res.size();
    }
}
