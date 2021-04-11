package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/subarray-sums-divisible-by-k/
 * Given an array A of integers, return the number of (contiguous, non-empty) subarrays that have a sum divisible by K.
 *
 * Example 1:
 * Input: A = [4,5,0,-2,-3,1], K = 5
 * Output: 7
 * Explanation: There are 7 subarrays with a sum divisible by K = 5:
 * [4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
 *
 * Note:
 * 1 <= A.length <= 30000
 * -10000 <= A[i] <= 10000
 * 2 <= K <= 10000
 */

//这题目算是"SubarraySumEqualsK"的一个variation
public class SubarraySumsDivisibleByK {
    public int subarraysDivByK(int[] A, int K) {
        //我自己想到的是O(n^2)的解法，也是用prefixSum，但是要判断以每个点为结尾的subArray是否能够被k整除
        //我需要把前面的prefixSum全都过一遍，没有想到其实我只需要存"%5"的结果做Key存在hashMap里就可以O(1)的look up了

        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        int cnt = 0;
        for(int a : A){
            sum += a;
            int key = sum%K;
            //注意这里要用处理sum为负数的情况，因为直接算出来的余数也是负的，利用周期律加到正数区间去
            key = key<0 ? key + K : key;
            //System.out.println("sum: "+sum+" key: "+key);
            if(key==0){//不要忘记处理key自己为0的情况
                cnt++;
            }
            if(map.containsKey(key)){
                int val = map.get(key);
                cnt += val;
                map.put(key, ++val);
            }
            else{
                map.put(key, 1);
            }
        }
        return cnt;
    }
}
