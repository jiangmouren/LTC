package com.mycompany.app;

/**
 * https://leetcode.com/problems/container-with-most-water/
 * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai).
 * n vertical lines are drawn such that the two endpoints of the line i is at (i, ai) and (i, 0).
 * Find two lines, which, together with the x-axis forms a container,
 * such that the container contains the most water.
 * Notice that you may not slant the container.
 *
 * Example 1:
 * Input: height = [1,8,6,2,5,4,8,3,7]
 * Output: 49
 * Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7].
 * In this case, the max area of water (blue section) the container can contain is 49.
 *
 * Example 2:
 * Input: height = [1,1]
 * Output: 1
 *
 * Example 3:
 * Input: height = [4,3,2,1,4]
 * Output: 16
 *
 * Example 4:
 * Input: height = [1,2,1]
 * Output: 2
 *
 * Constraints:
 *
 * n == height.length
 * 2 <= n <= 3 * 104
 * 0 <= height[i] <= 3 * 104
 */

public class ContainerWithMostWater{
    //整体思路跟"Sorted 2-Sum"有点像
    //本质上说，这个也也是已经sorted by distance
    //找到短的那根移动，才有可能找到跟大的。所有被抛在ptr0/1后面的，都不肯能以其为边找到更大的
    public int maxArea(int[] height) {
        int res = Integer.MIN_VALUE;
        int left = 0;
        int right = height.length - 1;
        while(left<right){
            int area = (right-left) * Math.min(height[left], height[right]);
            res = Math.max(res, area);
            if(height[left]<height[right]){
                int ptr = left;
                while(ptr<right && height[ptr]<=height[left]){
                    ptr++;
                }
                left = ptr;
            }
            else{
                int ptr = right;
                while(ptr>left && height[ptr]<=height[right]){
                    ptr--;
                }
                right = ptr;
            }
        }
        return res;
    }
}