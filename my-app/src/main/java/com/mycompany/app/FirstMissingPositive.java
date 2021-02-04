package com.mycompany.app;

/**
 * https://leetcode.com/problems/first-missing-positive/
 * Given an unsorted integer array nums, find the smallest missing positive integer.
 *
 * Example 1:
 * Input: nums = [1,2,0]
 * Output: 3
 *
 * Example 2:
 * Input: nums = [3,4,-1,1]
 * Output: 2
 *
 * Example 3:
 * Input: nums = [7,8,9,11,12]
 * Output: 1
 *
 * Constraints:
 * 0 <= nums.length <= 300
 * -231 <= nums[i] <= 231 - 1
 *
 * Follow up: Could you implement an algorithm that runs in O(n) time and uses constant extra space?
 */

/**
 * 最简单的解法就是把nums sort一下，然后从小到大过一遍，自然就找到出现的最小的positive number，也就知道first missing positive number
 * 但是这个题因为有0 <= nums.length <= 300，所以我直接就想到最大的可能的first missing positive number是301.
 * 因为所谓"first missing"，也就是说比其小的positive number都出现了，而nums最大也就300个entry，所以对于任何大于301的数来说，
 * 不可能把比其小的正数都放进去。这种情况下我们只需要用boolean[] flag = new boolean[301]，把nums过一遍就搞定了
 * Time complexity: O(n), Space complexity: O(1)
 */
public class FirstMissingPositive{
    public int firstMissingPositive(int[] nums) {
        boolean[] flag = new boolean[301];
        for(int num : nums){
            if(num>0&&num<=301){
                flag[num-1] = true;
            }
        }
        int res = 1;
        for(int i=0; i<301; i++){
            if(!flag[i]){
                res = i+1;
                break;
            }
        }
        return res;
    }
}