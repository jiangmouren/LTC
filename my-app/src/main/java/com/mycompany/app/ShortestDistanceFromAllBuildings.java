package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/shortest-distance-from-all-buildings/
 * You want to build a house on an empty land which reaches all buildings in the shortest amount of distance.
 * You can only move up, down, left and right. You are given a 2D grid of values 0, 1 or 2, where:
 * Each 0 marks an empty land which you can pass by freely.
 * Each 1 marks a building which you cannot pass through.
 * Each 2 marks an obstacle which you cannot pass through.
 *
 * Example:
 * Input: [[1,0,2,0,1],[0,0,0,0,0],[0,0,1,0,0]]
 * 1 - 0 - 2 - 0 - 1
 * |   |   |   |   |
 * 0 - 0 - 0 - 0 - 0
 * |   |   |   |   |
 * 0 - 0 - 1 - 0 - 0
 *
 * Output: 7
 * Explanation: Given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2),
 *              the point (1,2) is an ideal empty land to build a house, as the total
 *              travel distance of 3+3+1=7 is minimal. So return 7.
 *
 * Example 2:
 * Input: grid = [[1,0]]
 * Output: 1
 *
 * Example 3:
 * Input: grid = [[1]]
 * Output: -1
 *
 * Constraints:
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 100
 * grid[i][j] is either 0, 1, or 2.
 * There will be at least one building in the grid.
 */

class Solution {
    //因为Obstacle的出现，就无法把两dimension当成独立的，无法像BestMeetingPoint那样分开处理
    //只能采取"Brutal Force"解法，从每个1出发用BFS计算每个0到自己的距离，然后记录下来，同时要标记每个0被几个1reach
    //然后就有了每个0到所有reachable的1的距离，然后再过一遍，所有那些能reach所有1的0，找到最小的距离
    public int shortestDistance(int[][] grid) {
        int cnt = 0;
        int m = grid.length;
        int n = grid[0].length;
        int[][] distance = new int[m][n];
        int[][] reach = new int[m][n];
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(grid[i][j]==1){
                    boolean[][] visit = new boolean[m][n];
                    cnt++;
                    bfs(grid, i, j, visit, distance, reach, dirs);
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(reach[i][j]==cnt){
                    min = Math.min(min, distance[i][j]);
                }
            }
        }
        return min==Integer.MAX_VALUE ? -1 : min;
    }

    private void bfs(int[][] grid, int x, int y, boolean[][] visit, int[][] distance, int[][] reach, int[][] dirs){
        Queue<int[]> q = new LinkedList<>();
        //注意这里不是从1开始，而是从1的所有nbor开始，保持assumption的连贯性
        addNbors(x, y, dirs, grid, visit, q);
        int step = 1;

        while(!q.isEmpty()){
            int cnt = q.size();
            while(cnt>0){
                int[] cur = q.poll();
                cnt--;
                distance[cur[0]][cur[1]] += step;
                reach[cur[0]][cur[1]] += 1;
                addNbors(cur[0], cur[1], dirs, grid, visit, q);
            }
            step++;
        }
    }

    private boolean check(int x, int y, int[][] grid, boolean[][] visit){
        int m = grid.length;
        int n = grid[0].length;

        if(x>=0 && x<m && y>=0 && y<n && !visit[x][y] && grid[x][y]==0){
            return true;
        }
        return false;
    }

    private void addNbors(int x, int y, int[][] dirs, int[][] grid, boolean[][] visit, Queue<int[]> q){
        for(int[] dir : dirs){
            int xNew = x + dir[0];
            int yNew = y + dir[1];
            if(check(xNew, yNew, grid, visit)){
                q.add(new int[]{xNew, yNew});
                visit[xNew][yNew] = true;
            }
        }
    }
}
