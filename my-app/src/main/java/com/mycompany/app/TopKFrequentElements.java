package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/top-k-frequent-elements/
 * Given a non-empty array of integers, return the k most frequent elements.
 *
 * Example 1:
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 *
 * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
 * Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 * It's guaranteed that the answer is unique, in other words the set of the top k frequent elements is unique.
 * You can return the answer in any order.
 */

public class TopKFrequentElements{
    public int[] topKFrequent(int[] nums, int k) {
        //先用HashMap统计出现次数O(n)，在用quickSelect找Kth largest O(n)
        Map<Integer, Integer> map = new HashMap<>();
        for(int num : nums){
            if(map.containsKey(num)){
                int cnt = map.get(num);
                map.put(num, ++cnt);
            }
            else{
                map.put(num, 1);
            }
        }
        Set<Map.Entry<Integer,Integer>> entrySet = map.entrySet();
        List<Map.Entry<Integer,Integer>> entryList = new ArrayList<>();
        entryList.addAll(entrySet);
        int l = entryList.size()-k+1;
        int idx = quickSelect(entryList, 0, entryList.size()-1, l);
        int[] res = new int[k];
        for(int i=idx; i<entryList.size(); i++){
            res[i-idx] = entryList.get(i).getKey();
        }
        return res;
    }

    //return the index of the kth largest
    private int quickSelect(List<Map.Entry<Integer, Integer>> entryList, int left, int right, int k){
        if(k==0 || k>entryList.size()){
            return -1;
        }
        //termination condition
        if(left==right){
            return left;
        }

        int idx = partition(entryList, left, right);
        if(idx==k-1){
            return idx;
        }
        else if(idx>k-1){
            return quickSelect(entryList, left, idx-1, k);
        }
        else{
            return quickSelect(entryList, idx+1, right, k);
        }
    }

    private int partition(List<Map.Entry<Integer,Integer>> entryList, int start, int end){
        int left = start + 1;
        int right = end;
        while(left<=right){
            while(right>=start && entryList.get(right).getValue()>entryList.get(start).getValue()){
                right--;
            }
            while(left<=end && entryList.get(left).getValue()<=entryList.get(start).getValue()){
                left++;
            }
            if(left<right){
                swap(entryList, left, right);
            }
        }
        swap(entryList, start, right);
        return right;
    }

    private void swap(List<Map.Entry<Integer, Integer>> list, int left, int right){
        Map.Entry<Integer, Integer> temp = list.get(left);
        list.set(left, list.get(right));
        list.set(right, temp);
    }
}