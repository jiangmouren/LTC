package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/missing-ranges/
 * You are given an inclusive range [lower, upper] and a sorted unique integer array nums,
 * where all elements are in the inclusive range.
 * A number x is considered missing if x is in the range [lower, upper] and x is not in nums.
 * Return the smallest sorted list of ranges that cover every missing number exactly.
 * That is, no element of nums is in any of the ranges, and each missing number is in one of the ranges.
 * Each range [a,b] in the list should be output as:
 * "a->b" if a != b
 * "a" if a == b
 *
 * Example 1:
 * Input: nums = [0,1,3,50,75], lower = 0, upper = 99
 * Output: ["2","4->49","51->74","76->99"]
 * Explanation: The ranges are:
 * [2,2] --> "2"
 * [4,49] --> "4->49"
 * [51,74] --> "51->74"
 * [76,99] --> "76->99"
 *
 * Example 2:
 * Input: nums = [], lower = 1, upper = 1
 * Output: ["1"]
 * Explanation: The only missing range is [1,1], which becomes "1".
 *
 * Example 3:
 * Input: nums = [], lower = -3, upper = -1
 * Output: ["-3->-1"]
 * Explanation: The only missing range is [-3,-1], which becomes "-3->-1".
 *
 * Example 4:
 * Input: nums = [-1], lower = -1, upper = -1
 * Output: []
 * Explanation: There are no missing ranges since there are no missing numbers.
 *
 * Example 5:
 * Input: nums = [-1], lower = -2, upper = -1
 * Output: ["-2"]
 *
 * Constraints:
 * -109 <= lower <= upper <= 109
 * 0 <= nums.length <= 100
 * lower <= nums[i] <= upper
 * All the values of nums are unique.
 */

public class MissingRanges {
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        //其实最简单的是把lower & upper直接插到开始和末端，但让要跟开始跟末端的值比较决定插还是不插
        //但是这回造成额外的space and time complexity
        List<String> res = new ArrayList<>();
        if(nums.length==0){
            populate(res, lower, upper);
        }
        else{
            if(lower<nums[0]){
                int right = nums[0]-1;
                populate(res, lower, right);
            }
            int ptr0 = 0;
            int ptr1 = 1;
            while(ptr1<nums.length){
                if(nums[ptr1]-nums[ptr0]>1){
                    int left = nums[ptr0]+1;
                    int right = nums[ptr1]-1;
                    populate(res, left, right);
                }
                ptr0++;
                ptr1++;
            }
            if(upper>nums[nums.length-1]){
                populate(res, nums[nums.length-1]+1, upper);
            }
        }
        return res;
    }

    private void populate(List<String> res, int left, int right){
        if(left==right){
            res.add(""+left);
        }
        else{
            res.add(left + "->" + right);
        }
        return;
    }
}
