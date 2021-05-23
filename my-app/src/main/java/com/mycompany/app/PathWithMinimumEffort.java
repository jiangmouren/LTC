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
    class Cell{
        int x;
        int y;
        int diff;
        public Cell(int x, int y, int diff){
            this.x = x;
            this.y = y;
            this.diff = diff;
        }
    }

    public int minimumEffortPath(int[][] heights){
        int row = heights.length;
        int col = heights[0].length;
        int[][] diffMatrix = new int[row][col];
        for(int[] arr : diffMatrix){
            Arrays.fill(arr, Integer.MAX_VALUE);
        }
        diffMatrix[0][0] = 0;
        //这里通过visited[][] and diffMatrix[][]避免了使用Cell的Set，就免了写hashCode and equal function
        boolean[][] visited = new boolean[row][col];
        PriorityQueue<Cell> pq = new PriorityQueue<>((a, b)->a.diff-b.diff);
        pq.add(new Cell(0, 0, diffMatrix[0][0]));
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while(!pq.isEmpty()){
            Cell cur = pq.poll();
            visited[cur.x][cur.y] = true;
            if(cur.x==row-1 && cur.y==col-1){
                return cur.diff;
            }

            for(int[] dir : dirs){
                //注意这里会出现往回走向parent的问题，但是这种情况diff不会变，所以不会在PriorityQueue里因此再把parent加一遍
                int xNew = cur.x + dir[0];
                int yNew = cur.y + dir[1];
                if(xNew>=0 && xNew<row && yNew>=0 && yNew<col && !visited[xNew][yNew]){
                    //这里不要忘记取绝对值
                    int curDiff = Math.abs(heights[xNew][yNew] - heights[cur.x][cur.y]);
                    int maxDiff = Math.max(curDiff, diffMatrix[cur.x][cur.y]);
                    if(diffMatrix[xNew][yNew]>maxDiff){//relax when smaller diff found
                        //无法选择只在从priorityQueue抽出的时候update diffMatrix
                        //因为diffMatrix还要用来记录一些intermediate的值，因为java PriorityQueue不支持Decrease-Key for Min-Heap
                        diffMatrix[xNew][yNew] = maxDiff;
                        pq.add(new Cell(xNew, yNew, diffMatrix[xNew][yNew]));
                    }
                }
            }
        }
        return diffMatrix[row-1][col-1];
    }
}
