package com.mycompany.app;
import java.util.*;

/**
 * Question: https://leetcode.com/problems/subsets/
 * Given an integer array nums of unique elements, return all possible subsets (the power set).
 * The solution set must not contain duplicate subsets. Return the solution in any order.
 *
 * Example 1:
 * Input: nums = [1,2,3]
 * Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 *
 * Example 2:
 * Input: nums = [0]
 * Output: [[],[0]]
 *
 * Constraints:
 * 1 <= nums.length <= 10
 * -10 <= nums[i] <= 10
 * All the numbers of nums are unique.
 */

/**
 * Analysis:
 * 跟permutation的不同就是不做swap，这样避免duplicate
 */
public class Subsets{
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        List<Integer> buf = new ArrayList<>();
        for(int i=1; i<=nums.length; i++){
            backtracking(res, buf, nums, 0, i);
        }
        return res;
    }

    private void backtracking(List<List<Integer>> res, List<Integer> buf, int[] nums, int start, int k){
        //termination
        if(buf.size()==k){
            List<Integer> temp = new ArrayList<>();
            temp.addAll(buf);
            res.add(temp);
            return;
        }

        for(int i=start; i<nums.length-k+1+buf.size(); i++){//这里i的上限，这么控制更优，直接写成i<nums.length也可以
            buf.add(nums[i]);
            backtracking(res, buf, nums, i+1, k);//注意这里一定是"i+1"，不是start+1，易犯错误
            buf.remove(buf.size()-1);
        }
    }
}
