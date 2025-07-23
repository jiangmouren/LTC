package com.mycompany.app.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode.com/problems/longest-consecutive-sequence/
 * Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
 *
 * Example 1:
 * Input: nums = [100,4,200,1,3,2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
 *
 * Example 2:
 * Input: nums = [0,3,7,2,5,8,4,6,0,1]
 * Output: 9
 *
 * Constraints:
 * 0 <= nums.length <= 104
 * -109 <= nums[i] <= 109
 *
 * Follow up: Could you implement the O(n) solution?
 */

/**
 * Analysis:
 * 这题目最直观的想法就是sort，然后两个pointer找最长的连续值，复杂度达不到要求。
 * 其实这类Disjoint-set grouping的问题都可以用Union-Find方法在O(n)的复杂度下解决。
 * 构造Union-Find的复杂度是O(n)，每次union(x, y) or find(x)的复杂度都是O(1).
 * 具体详见：https://www.youtube.com/watch?v=VJnUwsE4fWA
 * src\main\resources\UnionFind.PNG
 */
public class LongestConsecutiveSequence {
    public static void main(String[] args){
        LongestConsecutiveSequence instance = new LongestConsecutiveSequence();
        int[] nums = new int[]{100, 4, 7, 200, 1, 6, 3, 2, 8, 9, 10};
        System.out.println(instance.longestConsecutive(nums));
    }
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int num : nums){
            set.add(num);
        }
        Set<Integer> visited = new HashSet<>();
        int max = 0;
        for(int num : nums){
            if(!visited.contains(num)){
                int cnt0 = visited.size();
                dfs(set, num, visited);
                int cnt1 = visited.size();
                max = Math.max(max, cnt1-cnt0);
            }
        }
        return max;
    }

    private void dfs(Set<Integer> set, int root, Set<Integer> visited){
        //System.out.println(root);
        visited.add(root);
        if(set.contains(root-1) && !visited.contains(root-1)){
            dfs(set, root-1, visited);
        }
        if(set.contains(root+1) && !visited.contains(root+1)){
            dfs(set, root+1, visited);
        }
    }
}