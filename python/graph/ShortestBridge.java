package com.mycompany.app.graph;
import java.util.*;
/**
 * https://leetcode.com/problems/shortest-bridge/
 * You are given an n x n binary matrix grid where 1 represents land and 0 represents water.
 *
 * An island is a 4-directionally connected group of 1's not connected to any other 1's.
 * There are exactly two islands in grid.
 *
 * You may change 0's to 1's to connect the two islands to form one island.
 *
 * Return the smallest number of 0's you must flip to connect the two islands.
 *
 * Example 1:
 * Input: grid = [[0,1],[1,0]]
 * Output: 1
 *
 * Example 2:
 * Input: grid = [[0,1,0],[0,0,0],[0,0,1]]
 * Output: 2
 *
 * Example 3:
 * Input: grid = [[1,1,1,1,1],[1,0,0,0,1],[1,0,1,0,1],[1,0,0,0,1],[1,1,1,1,1]]
 * Output: 1
 *
 * Constraints:
 * n == grid.length == grid[i].length
 * 2 <= n <= 100
 * grid[i][j] is either 0 or 1.
 * There are exactly two islands in grid.
 */
//没什么特别的，先用DFS做clustering，然后对一个cluster里面所有的点做multi-source BFS找到另外一个cluster的最短距离
public class ShortestBridge {
    public int shortestBridge(int[][] grid) {
        boolean[][] island = new boolean[grid.length][grid[0].length];
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for(int i=0; i<grid.length; i++){
            boolean terminate = false;
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]==1){
                    dfs(grid, island, i, j, dirs);
                    terminate = true;
                    break;
                }
            }
            if(terminate){
                break;
            }
        }
        Queue<int[]> queue = new LinkedList<>();
        int distance = 0;
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(island[i][j]){
                    int[] pos = {i, j};
                    queue.add(pos);
                    visited[i][j] = true;
                }
            }
        }
        boolean found = false;
        while(!queue.isEmpty()){
            int cnt = queue.size();
            while(cnt>0){
                int[] pos = queue.poll();
                if(grid[pos[0]][pos[1]]==1 && !island[pos[0]][pos[1]]){
                    found = true;
                    break;
                }
                cnt--;
                for(int[] dir : dirs){
                    int xNew = pos[0] + dir[0];
                    int yNew = pos[1] + dir[1];
                    if(xNew>=0 && yNew>=0 && xNew<grid.length && yNew<grid[0].length && !island[xNew][yNew] && !visited[xNew][yNew]){
                        visited[xNew][yNew] = true;
                        int[] posNew = {xNew, yNew};
                        queue.add(posNew);
                    }
                }
            }
            if(found){
                break;
            }
            distance++;
        }
        return distance-1;
    }

    private void dfs(int[][] grid, boolean[][] island, int x, int y, int[][] dirs){
        island[x][y] = true;
        for(int[] dir : dirs){
            int xNew = x + dir[0];
            int yNew = y + dir[1];
            if(xNew>=0 && yNew>=0 && xNew<grid.length && yNew<grid[0].length && !island[xNew][yNew] && grid[xNew][yNew]==1){
                dfs(grid, island, xNew, yNew, dirs);
            }
        }
    }
}
