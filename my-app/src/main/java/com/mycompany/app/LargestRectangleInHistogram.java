package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/largest-rectangle-in-histogram/
 * Given n non-negative integers representing the histogram's bar height where the width of each bar is 1,
 * find the area of largest rectangle in the histogram.
 * Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].
 * The largest rectangle is shown in the shaded area, which has area = 10 unit.
 *
 * Example:
 * Input: [2,1,5,6,2,3]
 * Output: 10
 *
 * Example 1:
 * Input: heights = [2,1,5,6,2,3]
 * Output: 10
 * Explanation: The above is a histogram where width of each bar is 1.
 * The largest rectangle is shown in the red area, which has an area = 10 units.
 *
 * Example 2:
 * Input: heights = [2,4]
 * Output: 4
 *
 * Constraints:
 * 1 <= heights.length <= 105
 * 0 <= heights[i] <= 104
 */

/**
 * 这个题目其实应该跟“Trapping Water”放一起看。
 * 首先求解总思路如下：
 * 在每一个位置求最大高度的矩形的最大面积，每一个位置都做一遍，全局的最大矩形，就在这些矩形当中。
 * 在每一个位置的最大高度就是Histogram的高度，是已经给定的。
 * 需要求的就是在这个高度上，往左往右，能extend多远？
 * 往左找到最近的一个比i矮的位置j，往右找到最近的一个比i高的位置k，那么在(j,k)的区间内就是能以heights[i]为高画出的最大矩形。
 * 这也就是这道题目跟"Trapping Water"相似的地方。
 * "Trapping Water"是要找到最侧和右侧分别的最大值。
 * 这里是要找到左侧和右侧距离最近的比自己短的位置。
 * 具体实现上既可以用Array也可以用Stack，具体分析:
 * 1. 用Array
 * 如果heights[i]>heights[i-1](注意只能是大于，不能是大于等于)，那么left[i]=i-1.
 * 如果heights[i]<=heights[i-1]，那么回头找第一个比heights[i]小的位置。
 * 从比heights[i-1]小的位置看起，因为heights[i-1]前面比Heights[i-1]大的位置没有必要看，
 * 因为heights[i]已经小于heights[i-1]，然后依次跳跃着往回找。
 * 直到找到第一个比heights[i]小的位置，或者一直找到左边尽头，就是heights[0]也比heights[i]大，那么left[i]=-1
 * 这里面有个担心，就是“回头找”会不会导致O(n^2)的复杂度，答案是：不会！
 * 因为每个点最多只会被回头访问1次。
 * 比如下图中：src\main\resources\LargestRectangleInHistogram.PNG
 * 在“4”的位置需要回头找，会找到“0”。在“7”的位置挨个找到“4”，然后直接跳到“0”，然后发现“0”的位置也比“7”的位置大。
 * 这里面7在往回找的时候，会访问“6，5,4”，但是在4的时候，会直接跳到比“4”小的位置，不会重复访问“3，2，1”。
 * 同理，可以取求解right[].
 * 2. 用Stack
 * 一样的思路。每一个进入Stack的位置，在Stack当中，前面一个存的都是起对应的left[i].
 * 而每次遇到比Stack.peek()小的位置k的时候，就是找到了stack.peek()位置所对应的right[i]了。
 * 这个时候，从stack当中往外pop，只要stack.peek()位置高度大于当前heights[k],
 * 那么可就是当下stack.peek()位置所对应的right bound，其left bound就在stack里面下一个entry.
 * 最后，会有余在stack当中的，便是以heights.length为right bound的bar.
 * 留在stack当中的倒数第二个，“-1”前面最后一个，就是整个Histogram里面最短的一个bar.
 */
public class LargestRectangleInHistogram{

    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        //离自己最近的左侧比自己小的位置
        int[] left = new int[n];
        //离自己最近的右侧比自己小的位置
        int[] right = new int[n];
        int maxArea = 0;
        left[0] = -1;
        for(int i=1; i<n; i++){
            if(heights[i]>heights[i-1]){
                left[i] = i-1;
            }
            else{
                int ptr = left[i-1];
                while(ptr>=0 && heights[ptr]>=heights[i]){
                    ptr = left[ptr];
                }
                left[i] = ptr;
            }
        }
        right[n-1] = n;
        for(int i=n-2; i>=0; i--){
            if(heights[i]>heights[i+1]){
                right[i] = i+1;
            }
            else{
                int ptr = right[i+1];
                while(ptr<n&&heights[ptr]>=heights[i]){
                    ptr = right[ptr];
                }
                right[i] = ptr;
            }
        }
        for(int i=0; i<n; i++){
            maxArea = Math.max(maxArea, heights[i]*(right[i]-left[i]-1));
        }
        return maxArea;
    }

    public int largestRectangleAreaSln2(int[] heights){
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        int length = heights.length;
        int maxArea = 0;
        for(int i=0; i<length; i++){
            while(stack.peek()!=-1 && heights[stack.peek()]>=heights[i]){
                int curHeight = heights[stack.pop()];
                int curWidth = i - stack.peek() - 1;//减1是因为当前i所指向的Bar不算进去
                maxArea = Math.max(maxArea, curHeight*curWidth);
            }
            stack.push(i);
        }
        //处理余下的bar
        while(stack.peek()!=-1){
            int curHeight = heights[stack.pop()];
            int curWidth = length - stack.peek() - 1;
            maxArea = Math.max(maxArea, curHeight*curWidth);
        }
        return maxArea;
    }

}