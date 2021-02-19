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
    public int maxArea(int[] height) {
        //整体思路跟"Sorted 2-Sum"有点像
        //本质上说，这个也也是已经sorted by distance
        int res = Integer.MIN_VALUE;
        int ptr0 = 0;
        int ptr1 = height.length - 1;
        while(ptr0<ptr1){
            int area = (ptr1-ptr0) * Math.min(height[ptr0], height[ptr1]);
            res = Math.max(res, area);
            if(height[ptr0]<height[ptr1]){//找到短的那根移动，才有可能找到跟大的。所有被抛在ptr0/1后面的，都不肯能以其为边找到更大的
                int ptr = ptr0+1;
                while(ptr<ptr1){
                    if(height[ptr]<=height[ptr0]){
                        ptr++;
                    }
                    else{
                        break;
                    }
                }
                ptr0 = ptr;
            }
            else{
                int ptr = ptr1-1;
                while(ptr>ptr0){
                    if(height[ptr]<=height[ptr1]){
                        ptr--;
                    }
                    else{
                        break;
                    }
                }
                ptr1 = ptr;
            }
        }
        return res;
    }
}