package com.mycompany.app.sort;

/**
 * Created by jiangmouren on 6/17/17.
 */

/**
 * Question:
 * Implement MergeSort both recursively and iteratively.
 */

/**
 * Analysis:
 * The property of Merge sort:
 * 1. Worst, Average, Best all have the same run time complexity: NlgN.(Or say NlgN guaranteed.)
 * 2. Stable.
 * 3. Not in place. O(N) space complexity.
 *
 * In common comparison sort, this is the only NlgN guaranteed algorithm.
 * Plus it is also stable, the other stable sort is insertion sort.
 * Only drawback is the extra array needed.
 */
public class MergeSort {
    public int[] mergeSort1(int[] nums){
        if(nums==null) throw new IllegalArgumentException("input cannot be null");
        if(nums.length==0) return nums;
        int[] helper = new int[nums.length];
        mergeSortHelper(nums, helper, 0, nums.length-1);
        return nums;
    }

    private void mergeSortHelper(int[] nums, int[] helper, int start, int end){
        //Termination Cases
        if(start>=end) return;

        //Recursive Cases
        int mid = (start + end)/2;
        mergeSortHelper(nums, helper, start, mid);
        mergeSortHelper(nums, helper, mid+1, end);
        merge(nums, helper, start, mid, end);
    }

    private void merge(int[] nums, int[] helper, int start, int mid, int end){
        //early termination for mid>=end
        if(mid>=end) return;
        //Copy everything from nums into helper, then merge back
        for(int i=start; i<=end; i++){
            helper[i] = nums[i];
        }

        int ptr = start, ptr1 = start, ptr2 = mid+1;
        while(ptr1<=mid && ptr2<=end){
            if(helper[ptr1]<=helper[ptr2]){
                nums[ptr] = helper[ptr1];
                ptr1++;
            }
            else{//Must use "else" instead of another "if", you want to recheck loop condition after every step
                nums[ptr] = helper[ptr2];
                ptr2++;
            }
            ptr++;
        }

        //if remainder is from left part, then we need to copy them;
        //if remainder is from right part, then they are already in nums.
        //Left and Right part won't terminate at the same time
        if(ptr1<=mid){
            for(int i=ptr1; i<=mid; i++){
                nums[end-mid+i] = helper[i];
            }
        }
    }

    /**
     * @param nums
     * @return
     *
     * Originally I tried to use Stack, not easy to do.
     * But whether or not we need Stack, depends on the underlying Data Structure being used.
     * Say, when doing DFS traversal a tree iteratively, we need a stack to keep track of the path, because there is
     * no children to parent link. On the other side, when doing Bottom-Up DP, we do not need a stack.
     * Because the Bottom-Up topological order is already clear without a stack.
     */
    public int[] mergeSort2(int[] nums){
        if(nums==null) throw new IllegalArgumentException("input cannot be null");
        if(nums.length==0) return nums;

        int[] helper = new int[nums.length];
        //"step<nums.length" is tricky. The merge order is different from the recursion approach
        //for {0, 1, 2, 3, 4} the merge order is {[(0, 1), (2, 3)], (4)}
        //And this is not only for odd case, with 10 entries the last merge will be:
        //{(0, 1, 2, 3, 4, 5, 6, 7), (8, 9)}
        for(int step=1; step<nums.length; step+=step){//step control
            for(int start=0; start<nums.length; start+=2*step){
                //For the right most corner case, if start+step-1>=nums.length-1, subarray sorted, nothing to merge
                //Need to add early termination in merge() function
                int mid=Math.min(start+step-1, nums.length-1), end=Math.min(start+2*step-1, nums.length-1);
                merge(nums, helper, start, mid, end);
            }
        }
        return nums;
    }
}
