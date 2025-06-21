package com.mycompany.app.slidingWindow;
import java.util.*;
/**
 * https://leetcode.com/problems/contains-duplicate-iii/
 * You are given an integer array nums and two integers indexDiff and valueDiff.
 *
 * Find a pair of indices (i, j) such that:
 *
 * i != j,
 * abs(i - j) <= indexDiff.
 * abs(nums[i] - nums[j]) <= valueDiff, and
 * Return true if such pair exists or false otherwise.
 *
 * Example 1:
 * Input: nums = [1,2,3,1], indexDiff = 3, valueDiff = 0
 * Output: true
 * Explanation: We can choose (i, j) = (0, 3).
 * We satisfy the three conditions:
 * i != j --> 0 != 3
 * abs(i - j) <= indexDiff --> abs(0 - 3) <= 3
 * abs(nums[i] - nums[j]) <= valueDiff --> abs(1 - 1) <= 0
 *
 * Example 2:
 * Input: nums = [1,5,9,1,5,9], indexDiff = 2, valueDiff = 3
 * Output: false
 * Explanation: After trying all the possible pairs (i, j),
 * we cannot satisfy the three conditions, so we return false.
 *
 *
 * Constraints:
 * 2 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * 1 <= indexDiff <= nums.length
 * 0 <= valueDiff <= 10^9
 */

//sliding window + treemap
//其实题目如果该一下，改成abs(nums[i] - nums[j]) >= valueDiff，写法也是类似的，就是每次都first key跟last key比一下
//移除key的逻辑跟现在是一样的，如果cnt变成0了，就把key去掉。
public class ContainsDuplicateIII {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for(int i=0; i<nums.length; i++){
            if(i>indexDiff){//当ptr走到第一个window之外的位置才开始slide，这样写比较简洁
                int left = i-indexDiff-1;//注意这里还要多减一个1
                int cnt = map.get(nums[left]);
                cnt--;
                if(cnt==0){
                    map.remove(nums[left]);
                }
                else{
                    map.put(nums[left], cnt);
                }
            }
            Integer pre = map.floorKey(nums[i]);
            Integer nxt = map.ceilingKey(nums[i]);
            if(pre!=null && nums[i]-pre<=valueDiff){
                return true;
            }
            if(nxt!=null && nxt-nums[i]<=valueDiff){
                return true;
            }
            if(!map.containsKey(nums[i])){
                map.put(nums[i], 0);
            }
            int cnt = map.get(nums[i]);
            map.put(nums[i], ++cnt);
        }
        return false;
    }
}
