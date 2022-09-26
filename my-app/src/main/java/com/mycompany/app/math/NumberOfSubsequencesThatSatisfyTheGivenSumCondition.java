package com.mycompany.app.math;
import java.util.*;

/**
 * https://leetcode.com/problems/number-of-subsequences-that-satisfy-the-given-sum-condition/
 * You are given an array of integers nums and an integer target.
 *
 * Return the number of non-empty subsequences of nums such that the sum of the minimum and maximum element on it is less or equal to target.
 * Since the answer may be too large, return it modulo 109 + 7.
 *
 * Example 1:
 * Input: nums = [3,5,6,7], target = 9
 * Output: 4
 * Explanation: There are 4 subsequences that satisfy the condition.
 * [3] -> Min value + max value <= target (3 + 3 <= 9)
 * [3,5] -> (3 + 5 <= 9)
 * [3,5,6] -> (3 + 6 <= 9)
 * [3,6] -> (3 + 6 <= 9)
 *
 * Example 2:
 * Input: nums = [3,3,6,8], target = 10
 * Output: 6
 * Explanation: There are 6 subsequences that satisfy the condition. (nums can have repeated numbers).
 * [3] , [3] , [3,3], [3,6] , [3,6] , [3,3,6]
 *
 * Example 3:
 * Input: nums = [2,3,3,4,6,7], target = 12
 * Output: 61
 * Explanation: There are 63 non-empty subsequences, two of them do not satisfy the condition ([6,7], [7]).
 * Number of valid subsequences (63 - 2 = 61).
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^6
 * 1 <= target <= 10^6
 */
public class NumberOfSubsequencesThatSatisfyTheGivenSumCondition {
    public int numSubseq(int[] nums, int target) {
        //Sum(全部0->n的全部组合)是2^n，然后从1->n的全部组合的和就是(2^n - 1)
        //数学公式参见：https://stats.stackexchange.com/questions/27266/simplify-sum-of-combinations-with-same-n-all-possible-values-of-k
        //这个题的第一个要点是注意到，虽然我们要找的是subsequence，但是我们要的是个数，所以这个subsequence里面数字实际的顺序是不重要的，本质上我们要找的是有多少非空组合可以满足条件
        //这种情况下很自然的就想到我们可以把nums sort一下，然后对于一个给定的min, 找到右侧最大的能满足条件的数（可以从最右侧逐个排除，或者做binary search）
        //进而知道给定的min，右侧有多少个数需要被求他们的全部可能组合数，2^n
        //这里要注意要特别注意“给定min”这个条件，比如[3, 5, 6], target为9的情况下，给定3为min，5,6才可以被考虑进去，没有3的情况下5, 6是不能被考虑的
        //查找的复杂度是O(n)，不用每次都从头查，那么整个问题的复杂度就是O(nlogn)

        //因为中间的值是在太大了，Math.pow(2, delta)虽然是double，也会溢出，所以就提前用MOD方式算出来
        long[] powers = new long[100002];
        long MOD = (long)(Math.pow(10, 9) + 7);
        powers[0] = 1L;
        for(int i=1;i<=100001;i++){
            powers[i] = (powers[i-1]*2)%MOD;
        }
        Arrays.sort(nums);

        long cnt = 0;//注意这个cnt一定要用long，因为中间会溢出
        int ptr = nums.length - 1;
        for(int i=0; i<nums.length && 2*nums[i]<=target; i++){
            //System.out.println("min: "+nums[i]);
            while(ptr>=i && nums[i]+nums[ptr]>target){
                ptr--;
            }
            //System.out.println("max: "+nums[ptr]);
            int delta = ptr - i;
            //System.out.println("delta: "+delta);
            cnt = (powers[delta] + cnt) % MOD;
            //System.out.println("cnt: "+cnt);
        }

        return (int)(cnt % MOD);
    }
}
