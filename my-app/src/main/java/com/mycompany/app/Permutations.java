package com.mycompany.app;

/**
 * Question: https://leetcode.com/problems/permutations/
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
    public List<List<Integer>> permute(int[] nums) {
        List<Integer> buf = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        permute(nums, 0, buf, res);
        return res;
    }

    private void permute(int[] nums, int pos, List<Integer> buf, List<List<Integer>> res){
        //termination condition
        if(pos>=nums.length){
            List<Integer> temp = new ArrayList<>();
            temp.addAll(buf);
            res.add(temp);
            return;
        }

        for(int i=pos; i<nums.length; i++){
            swap(nums, pos, i);
            buf.add(nums[pos]);
            permute(nums, pos+1, buf, res);
            buf.remove(buf.size()-1);
            swap(nums, pos, i);
        }
    }

    private void swap(int[] nums, int pos1, int pos2){
        int temp = nums[pos1];
        nums[pos1] = nums[pos2];
        nums[pos2] = temp;
    }
}
