package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/longest-increasing-subsequence/
 * Given an integer array nums, return the length of the longest strictly increasing subsequence.
 * A subsequence is a sequence that can be derived from an array by deleting some or no elements
 * without changing the order of the remaining elements.
 * For example, [3,6,2,7] is a subsequence of the array [0,3,1,6,2,2,7].
 *
 * Example 1:
 * Input: nums = [10,9,2,5,3,7,101,18]
 * Output: 4
 * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
 *
 * Example 2:
 * Input: nums = [0,1,0,3,2,3]
 * Output: 4
 *
 * Example 3:
 * Input: nums = [7,7,7,7,7,7,7]
 * Output: 1
 *
 * Constraints:
 * 1 <= nums.length <= 2500
 * -104 <= nums[i] <= 104
 *
 * Follow up:
 * Could you come up with the O(n2) solution?
 * Could you improve it to O(n log(n)) time complexity?
 */

public class LongestIncreasingSubsequence{

    /**
     * 这个题目还有一种神奇的Binary Search的解法，是已知这个问题的最优解，这问题其实是一个经典问题，在一些算法书里都有提到过。
     * 详解见下图：
     * src\main\resources\LongestIncreasingSubsequence.jpg
     */
    public int lengthOfIncrSeqBs(int[] nums) {
        int[] buf = new int[nums.length];
        int len = 0;
        for (int num : nums) {
            //注意这里的"toIndex"用的是len,而且是exclusive的
            int i = Arrays.binarySearch(buf, 0, len, num);
            if (i < 0) {
                i = -(i + 1);
            }
            buf[i] = num;
            if (i == len) {
                len++;
            }
        }
        return len;
    }

    /**
     * DP解法，思路详见后面“错误解法”里面的对比分析。
     * dp[i]表达以nums[i]结尾的最长的递增sequence.
     * 所以新的迭代关系变成：dp[i] = 1 + max{dp[j]}, 0=<j<i && nums[j]<nums[i]
     * 所以整个求解dp的过程是O(n^2)，在求解的过程中keep track of max dp[i]，最后return max dp[i].
     */
    public int lengthOfIncrSeqDp(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = 1;
        //注意要用max keep track of最大值，因为这里dp表达的是以某个位置结尾(包含)的最大值，所以dp[n-1]并见得是全局最大值
        int max = 1;
        for(int i=1; i<n; i++){
            for(int j=0; j<i; j++){
                if(nums[j]<nums[i]){
                    dp[i] = Math.max(dp[i], dp[j]);
                }
            }
            dp[i]++;
            max = Math.max(max, dp[i]);
        }
        return max;
    }


    /**
     * 错误解法！！！
     * 这个题对我来说是易错题。我在数次不同时间做这个题的时候都会想到用jump pointer的解法，本质上说：
     * instead of using: dp[i] = 1 + max{dp[j]}, 0=<j<i && nums[j]<nums[i]
     * 我认为可以用: dp[i] = 1 + dp[f(i)];这里f(i)给出的是在nums当中左侧第一个小于nums[i]的位置。
     * 但问题是i左侧，最长的连续增长的sequence并不一定是截止在f(i)的位置.
     * 比如下面的例子：
     * [4,10,4,3,8,9]
     * 问题出在8的位置，最近的比8小的是3，但是8的左侧所能构成的最长递增sequence是[4, 10]，并不截止在3的位置！
     */
}