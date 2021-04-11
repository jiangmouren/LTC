package com.mycompany.app;

/**
 * https://leetcode.com/problems/trapping-rain-water/description/
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.
 *
 * Example 1:
 * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 * Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.
 *
 * Example 2:
 * Input: height = [4,2,0,3,2,5]
 * Output: 9
 *
 * Constraints:
 * n == height.length
 * 0 <= n <= 3 * 104
 * 0 <= height[i] <= 105
 */

/**
 * Analysis:
 * 此题关键是想清楚思路，实现很容易。
 * 想清思路的核心是回归本源。就是一个地方可以装水，必须是“凹地”。
 * “凹地”的定义就是往左往右都能找到比它高的地方。
 * “凹地”所能装的最高水位，就是取左侧最高点与右侧最高点之中的较小值。
 * 所以问题就转化成了：分别找到每个位置的左侧与右侧最高点，然后就可以确定某个位置是否可以装水，以及可以装多少。
 */

class TrappingRainWater {
      public int trap(int[] height) {
          int[] leftMax = new int[height.length];
          int curMax = 0;
          for(int i=0; i<height.length; i++){
              curMax = Math.max(curMax, height[i]);
              leftMax[i] = curMax;
          }
          int[] rightMax = new int[height.length];
          curMax = 0;
          for(int i=height.length-1; i>=0; i--){
              curMax = Math.max(curMax, height[i]);
              rightMax[i] = curMax;
          }
          int result = 0;
          for(int i=1; i<height.length-1; i++){
              int waterHeight = Math.min(leftMax[i], rightMax[i]);
              int waterAmnt = waterHeight-height[i];
              //因为之前的leftMax & rightMax都是包含当下位置的，
              //所以这里担心的当下位置高于prefix & suffix最大值得情况不会出现,最多就是相等
              //int temp = (waterAmnt)>0 ? waterAmnt : 0;
              result += waterAmnt;
          }
          return result;
      }
}
