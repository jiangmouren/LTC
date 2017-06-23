package Finished;

/**
 * Question:
 * Given a collection of distinct numbers, return all possible permutations.
 * For example,
 * [1,2,3] have the following permutations:
 * [
 *   [1,2,3],
 *   [1,3,2],
 *   [2,1,3],
 *   [2,3,1],
 *   [3,1,2],
 *   [3,2,1]
 * ]
 */

/**
 * Permutation is classical backtracking problem.
 */
import java.util.*;

public class Permutations{
    public List<List<Integer>> permutation(int[] nums){
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> buf = new ArrayList<>();
        helper(nums, 0, result, buf);
        return result;
    }

    private void helper(int[] nums, int start, List<List<Integer>> result, List<Integer> buf){
        //Termination Cases
        if(start==nums.length-1){
            buf.add(nums[start]);
            List<Integer> tmp = new ArrayList<Integer>();//Must create a new object
            tmp.addAll(buf);
            result.add(tmp);
            buf.remove(start);
            return;
        }

        for(int i=start; i<nums.length; i++){
            swap(nums, start, i);
            buf.add(nums[start]);
            helper(nums, start+1, result, buf);
            buf.remove(start);
            swap(nums, start, i);
        }
    }

    private void swap(int[] nums, int ptr1, int ptr2){
        int tmp = nums[ptr1];
        nums[ptr1] = nums[ptr2];
        nums[ptr2] = tmp;
    }

}
