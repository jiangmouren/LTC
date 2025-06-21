package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/path-with-minimum-effort/
 * You are a hiker preparing for an upcoming hike.
 * You are given heights, a 2D array of size rows x columns,
 * where heights[row][col] represents the height of cell (row, col).
 * You are situated in the top-left cell, (0, 0), and you hope to travel to the bottom-right cell, (rows-1, columns-1) (i.e., 0-indexed).
 * You can move up, down, left, or right, and you wish to find a route that requires the minimum effort.
 * A route's effort is the maximum absolute difference in heights between two consecutive cells of the route.
 * Return the minimum effort required to travel from the top-left cell to the bottom-right cell.
 *
 * Example 1:
 * Input: heights = [[1,2,2],[3,8,2],[5,3,5]]
 * Output: 2
 * Explanation: The route of [1,3,5,3,5] has a maximum absolute difference of 2 in consecutive cells.
 * This is better than the route of [1,2,2,2,5], where the maximum absolute difference is 3.
 *
 * Example 2:
 * Input: heights = [[1,2,3],[3,8,4],[5,3,5]]
 * Output: 1
 * Explanation: The route of [1,2,3,4,5] has a maximum absolute difference of 1 in consecutive cells, which is better than route [1,3,5,3,5].
 *
 * Example 3:
 * Input: heights = [[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]
 * Output: 0
 * Explanation: This route does not require any effort.
 *
 * Constraints:
 * rows == heights.length
 * columns == heights[i].length
 * 1 <= rows, columns <= 100
 * 1 <= heights[i][j] <= 106
 */
//首先判断这个题可以使用Dijkstra: 在一条路径上前进，其cost不会变小，对应的就是weight non-negtive的要求
//具体到这个题目来看，一条path上出现的maxDiff，as you moving forward,要么不变，要么变大，不会变小，所以满足条件
public class PathWithMinimumEffort {
    public int minimumEffortPath(int[][] heights) {
        //往前找的过程edgeCost是非负数，到每个点的cost就是从parent继承来的cost跟当下edge取最大
        int[][] distances = new int[heights.length][heights[0].length];
        for(int[] row : distances){
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        distances[0][0] = 0;
        Queue<int[]> queue = new PriorityQueue<>((a, b)->a[0]-b[0]);
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        queue.add(new int[]{distances[0][0], 0, 0});
        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            int cost = cur[0];
            int x = cur[1];
            int y = cur[2];
            if(x==heights.length-1 && y==heights[0].length-1){
                return cost;
            }
            for(int[] dir : dirs){
                int xNew = x + dir[0];
                int yNew = y + dir[1];
                if(xNew>=0 && yNew>=0 && xNew<heights.length && yNew<heights[0].length){
                    int edgeCost = Math.abs(heights[xNew][yNew]-heights[x][y]);
                    edgeCost = Math.max(edgeCost, cost);
                    if(edgeCost<distances[xNew][yNew]){
                        queue.add(new int[]{edgeCost, xNew, yNew});
                        distances[xNew][yNew] = edgeCost;
                    }
                }
            }
        }
        return -1;
    }
}
