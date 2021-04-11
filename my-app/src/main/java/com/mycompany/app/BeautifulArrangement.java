package com.mycompany.app;

/**
 * https://leetcode.com/problems/beautiful-arrangement/
 * Suppose you have n integers labeled 1 through n.
 * A permutation of those n integers perm (1-indexed) is considered a beautiful arrangement if for every i (1 <= i <= n),
 * either of the following is true:
 * perm[i] is divisible by i.
 * i is divisible by perm[i].
 * Given an integer n, return the number of the beautiful arrangements that you can construct.
 *
 * Example 1:
 * Input: n = 2
 * Output: 2
 * Explanation:
 * The first beautiful arrangement is [1,2]:
 *     - perm[1] = 1 is divisible by i = 1
 *     - perm[2] = 2 is divisible by i = 2
 * The second beautiful arrangement is [2,1]:
 *     - perm[1] = 2 is divisible by i = 1
 *     - i = 2 is divisible by perm[2] = 1
 *
 * Example 2:
 * Input: n = 1
 * Output: 1
 *
 *
 * Constraints:
 * 1 <= n <= 15
 */

//此题只是做了一个简单的backtracking
//没有子问题可以构建dp，因为假设有[k, n-1]对应一组数的满足条件解的个数，但是不能用来构建[0, n-1]的解
//因为[0, k-1]部分与[k, n-1]部分的数字互相shuffle的情况，用上述子问题解决不了
public class BeautifulArrangement {
    public int countArrangement(int n) {
        int[] nums = new int[n];
        for(int i=0; i<n; i++){
            nums[i] = i+1;//注意是1-indexed
        }
        int[] cnt = new int[1];
        backtracking(nums, 0, cnt);
        return cnt[0];
    }

    private void backtracking(int[] nums, int start, int cnt[]){
        //termination
        if(start>=nums.length){
            cnt[0]++;
            return;
        }

        for(int i=start; i<nums.length; i++){
            //if(nums[i]%(i+1)==0 || (i+1)%nums[i]==0){ //注意这里求mode用start，而不是i，容易犯错
            if(nums[i]%(start+1)==0 || (start+1)%nums[i]==0){//注意是1-indexed, start+1
                swap(nums, start, i);
                backtracking(nums, start+1, cnt);
                swap(nums, start, i);
            }
        }
    }

    private void swap(int[] nums, int idx1, int idx2){
        int temp = nums[idx1];
        nums[idx1] = nums[idx2];
        nums[idx2] = temp;
    }
}
