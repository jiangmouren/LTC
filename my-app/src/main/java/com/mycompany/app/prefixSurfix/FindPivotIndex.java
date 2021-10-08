package com.mycompany.app.prefixSurfix;

/**
 * https://leetcode.com/problems/find-pivot-index/
 * Given an array of integers nums, calculate the pivot index of this array.
 * The pivot index is the index where the sum of all the numbers strictly to the left of the index
 * is equal to the sum of all the numbers strictly to the index's right.
 *
 * If the index is on the left edge of the array, then the left sum is 0 because there are no elements to the left.
 * This also applies to the right edge of the array.
 *
 * Return the leftmost pivot index. If no such index exists, return -1.
 *
 * Example 1:
 * Input: nums = [1,7,3,6,5,6]
 * Output: 3
 * Explanation:
 * The pivot index is 3.
 * Left sum = nums[0] + nums[1] + nums[2] = 1 + 7 + 3 = 11
 * Right sum = nums[4] + nums[5] = 5 + 6 = 11
 *
 * Example 2:
 * Input: nums = [1,2,3]
 * Output: -1
 * Explanation:
 * There is no index that satisfies the conditions in the problem statement.
 *
 * Example 3:
 * Input: nums = [2,1,-1]
 * Output: 0
 * Explanation:
 * The pivot index is 0.
 * Left sum = 0 (no elements to the left of index 0)
 * Right sum = nums[1] + nums[2] = 1 + -1 = 0
 *
 * Constraints:
 * 1 <= nums.length <= 104
 * -1000 <= nums[i] <= 1000
 */
public class FindPivotIndex {
    public int pivotIndex(int[] nums) {
        //分别用一个Prefix Running Sum && Suffix Running Sum，然后找里面最左边第一个相等的位置
        int n = nums.length;
        int[] prefix = new int[n];
        int[] suffix = new int[n];
        for(int i=0; i<n; i++){
            if(i==0){
                prefix[i] = nums[i];
            }
            else{
                prefix[i] = nums[i] + prefix[i-1];
            }
        }
        for(int i=n-1; i>=0; i--){
            if(i==n-1){
                suffix[i] = nums[i];
            }
            else{
                suffix[i] = nums[i] + suffix[i+1];
            }
        }
        int idx = -1;
        for(int i=0; i<n; i++){
            if(prefix[i]==suffix[i]){
                idx = i;
                break;
            }
        }
        return idx;
    }
}
