package com.mycompany.app.graph;

/**
 * https://leetcode.com/problems/number-of-islands/
 * Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
 * You may assume all four edges of the grid are all surrounded by water.
 *
 * Example 1:
 * Input: grid = [
 *   ["1","1","1","1","0"],
 *   ["1","1","0","1","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","0","0","0"]
 * ]
 * Output: 1
 *
 * Example 2:
 * Input: grid = [
 *   ["1","1","0","0","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","1","0","0"],
 *   ["0","0","0","1","1"]
 * ]
 * Output: 3
 *
 * Constraints:
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 300
 * grid[i][j] is '0' or '1'.
 */

public class NumberOfIslands {
    public int numIslands(char[][] grid) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int cnt = 0;
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]=='1' && !visited[i][j]){
                    cnt++;
                    dfs(grid, visited, i, j);
                }
            }
        }
        return cnt;
    }

    //all reachable 1s from (x, y) will be marked as visited.
    public void dfs(char[][] grid, boolean[][] visited, int x, int y){
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        visited[x][y] = true;
        for(int[] dir : dirs){
            int xNew = x + dir[0];
            int yNew = y + dir[1];
            if(xNew>=0 && xNew<grid.length && yNew>=0 && yNew<grid[0].length && grid[xNew][yNew]=='1' && !visited[xNew][yNew]){
                dfs(grid, visited, xNew, yNew);
            }
        }
    }
}
