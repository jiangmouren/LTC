package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/4sum/
 * Given an array nums of n integers and an integer target,
 * are there elements a, b, c, and d in nums such that a + b + c + d = target?
 * Find all unique quadruplets in the array which gives the sum of target.
 * Notice that the solution set must not contain duplicate quadruplets.
 *
 * Example 1:
 * Input: nums = [1,0,-1,0,-2,2], target = 0
 * Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 *
 * Example 2:
 * Input: nums = [], target = 0
 * Output: []
 *
 *
 * Constraints:
 * 0 <= nums.length <= 200
 * -109 <= nums[i] <= 109
 * -109 <= target <= 109
 */
public class FourSum {
    /**
     * 下面这种是我第一印象就会想到的backtracking解法，"Time Limit Exceeded"
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        //backtrack then sort each quadruplet before puting them to set
        //Also use a set to avoid duplicates,that's also the reason to sort before hand to match keys
        List<Integer> buf = new ArrayList<>();
        Set<List<Integer>> set = new HashSet<>();
        search(nums, 0, target, buf, set);
        List<List<Integer>> res = new ArrayList<>();
        res.addAll(set);
        return res;
    }

    private void search(int[] nums, int pos, int target, List<Integer> buf, Set<List<Integer>> res){
        //termination
        if(buf.size()>=4){
            if(sum(buf)==target){
                //一定要重建一个list接收buf里的东西，然后sort只能发生在这个新的token身上，而不是buf身上，否则责后面backtrack remove and swap会出错
                List<Integer> token = new ArrayList<>();
                token.addAll(buf);
                Collections.sort(token);
                res.add(token);
            }
            return;
        }

        for(int i=pos; i<nums.length; i++){
            swap(nums, pos, i);
            buf.add(nums[pos]);
            search(nums, pos+1, target, buf, res);
            buf.remove(buf.size()-1);
            swap(nums, pos, i);
        }
    }

    private void swap(int[] nums, int pos1, int pos2){
        int temp = nums[pos1];
        nums[pos1] = nums[pos2];
        nums[pos2] = temp;
    }

    private int sum(List<Integer> buf){
        int res = 0;
        for(int value : buf){
            res += value;
        }
        return res;
    }

    /**
     * 下面这种是在之前的3Sum的基础上演化出来的可以解决K-sum问题的方法
     */
    public List<List<Integer>> fourSumSln2(int[] nums, int target) {
        Arrays.sort(nums);
        return kSum(nums, target, 4, 0);
    }

    private List<List<Integer>> kSum(int[] nums, int target, int k, int start){
        List<List<Integer>> res = new ArrayList<>();
        if(k==2){
            int ptr0 = start;
            int ptr1 = nums.length-1;
            while(ptr0<ptr1){
                int sum = nums[ptr0]+nums[ptr1];
                if(sum==target){
                    List<Integer> temp = new ArrayList<>();
                    temp.add(nums[ptr0]);
                    temp.add(nums[ptr1]);
                    res.add(temp);
                    int ptr = ptr0;
                    while(ptr<=ptr1 && nums[ptr]==nums[ptr0]){
                        ptr++;
                    }
                    ptr0 = ptr;
                }
                else if(sum<target){
                    ptr0++;
                }
                else{
                    ptr1--;
                }
            }
        }
        else{
            for(int i=start; i<nums.length; i++){
                if(i==start || nums[i]!=nums[i-1]){
                    List<List<Integer>> resPartial = kSum(nums, target-nums[i], k-1, i+1);
                    for(List<Integer> list : resPartial){
                        list.add(nums[i]);
                    }
                    res.addAll(resPartial);
                }
            }
        }
        return res;
    }
}
