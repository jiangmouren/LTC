package finished.array_and_string;
/**
 * Question:
 * Given an array of n integers nums and a target,
 * find the number of index triplets i, j, k with 0 <= i < j < k < n that satisfy the condition
 * nums[i] + nums[j] + nums[k] < target.
 *
 * For example, given nums = [-2, 0, 1, 3], and target = 2.
 * Return 2. Because there are two triplets which sums are less than 2:
 * [-2, 0, 1]
 * [-2, 0, 3]
 * Follow up:
 * Could you solve it in O(n2) runtime?
 */

/**
 * Analysis:
 * This one is a variation of the TwoSumSorted problem.
 * For [-2, 0, 1, 3], we first get the whole array sorted with nlgn.
 * Then we loop through the array, and within each loop we try to find a pair.
 * In the outer loop, any entries passed, we won't need it in later iterations.
 * Within the loop, we leverage the sorted property, so we can find a pair in O(n), Otherwise will need O(n^2).
 * Let's look at [A0, ..., An], if A0+An<target, than for A0, all the way A1:An will work for it.
 * On the other side, if A0+An>target, then nothing will work for An.
 * In this way, we can use 3 pointers moving from each end toward the middle and exploit all possibilities in one round.
 *
 */

import java.util.*;
public class ThreeSumSmaller {
    public int ThreeSumSmaller(int[] nums, int target) {
        if(nums==null || nums.length<2) return 0;
        int result = 0;
        Arrays.sort(nums);
        for(int i=0; i<nums.length; i++){
            int left = i+1, right = nums.length-1;
            while(left<right){
                if(nums[left]+nums[right]<target-nums[i]){
                    result+=right-left;
                    left++;
                }
                else right--;
            }
        }
        return result;
    }
}



