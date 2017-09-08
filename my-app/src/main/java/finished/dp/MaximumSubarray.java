package finished.dp;

/**
 * Question:
 * Find the contiguous subarray within an array (containing at least one number) which has the largest sum.
 * For example, given the array [-2,1,-3,4,-1,2,1,-5,4],
 * the contiguous subarray [4,-1,2,1] has the largest sum = 6.
 */

/**
 * Analysis:
 * Classical DP problem. The key part to a DP problem is to define the sub-problem.
 * And to define a sub-problem, you can either reduce n to 1 and increment to 2, 3, ...
 * or just take the generic approach try to construct f(n) from f(n-1) or f(n/x), etc.
 * The goal is how to construct solution for the original problem from those of sub problems
 * and you must see this in a recursive point of view.
 * From sub problems to the original problem, there often exist gaps.
 * Now the approach should be:
 * 1. Identify and clarify the gaps by asking:
 * "Why I cannot construct the solution for the original problem, using the info available sub problems?"
 * "Give a concrete example, where you cannot do it and list all possible ways that you cannot do it"
 * "For all the possible ways that I cannot do it, what info do I need from sub problems inorder to make them doable?"
 * 2. Enrich and redesign the sub problems by adding info that was identified in step1.
 * 3. Now again check if you can find any gap.
 *
 *
 * For this problem:
 * Approach 1.
 * n==1. The max sum is a[0] itself;
 * n==2. Now you are adding a new number, how would this affect the problem generally?
 * The new max can either include a[1] or not. If not, then new max is just the previous max.
 * So you know at least you need previous max.
 * But if yes, then what? There are two possibilities:
 * 1. the new max is the max_pre extended to include a[1]; we already have max_pre
 * 2. the new max is a new sub-array + a[1];
 * Now we have a gap. Simply having max_pre is not enough, we need the max suffix of the previous array,
 * so we can decide the max sum containing a[1] for the current array.
 * Conclusion is: if we keep track of current max subarray and max suffix, we can construct next level solution.
 *
 * Approach 2.
 * Just split the problem by half and see what happens.
 * You have the max subarray of both of the two half arrays,
 * now how do I use them to construct the solution for the original array?
 * You will say: I cannot, because the max subarray for the whole array might span across both half.
 * To fill that gap, you need the prefix_max of the first half and the suffix_max of the second half.
 * And the current sub_max = max{prefix_max_1st_half+suffix_max_2nd_half, max_1st_half, max_2nd_half}
 * The next question is how to get prefix_max and suffix_max for the current array?
 * prefix_max = Max{prefix_max_1st_half, sum_1st_half+prefix_max_2nd_half}
 * So all the variables: {prefix_max, suffix_max, sum_all, sum_max} for each level.
 */
public class MaximumSubarray {
    public int maxmumSubarray(int[] nums){
        if(nums==null || nums.length==0) throw new IllegalArgumentException("Input cannot be null or empty");
        int sub_max=nums[0], suffix_max=nums[0];
        int sub_max_next, suffix_max_next;
        for(int i=1; i<nums.length; i++){
            suffix_max_next = Math.max(suffix_max+nums[i], nums[i]);
            sub_max_next = Math.max(sub_max, suffix_max_next);
            sub_max = sub_max_next;
            suffix_max = suffix_max_next;
        }
        return sub_max;
    }
}
