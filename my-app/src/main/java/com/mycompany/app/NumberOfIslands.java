package com.mycompany.app;

/**
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

Example 1:

11110
11010
11000
00000
Answer: 1

Example 2:

11000
11000
00100
00011
Answer: 3
 */ 

public class NumberOfIslands {
    public int numIslands(char[][] grid) {
        int[][] visited = new int[grid.length][grid[0].length];
        int cnt = 0;
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]=='1' && visited[i][j]==0){
                    cnt++;
                    dfs(grid, visited, i, j);
                }
            }
        }
        return cnt;
    }

    //0: not visited; 1: visiting; 2: visited
    //all reachable 1s from (x, y) will be marked as visited.
    public void dfs(char[][] grid, int[][] visited, int x, int y){
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        visited[x][y] = 1;
        for(int[] dir : dirs){
            int xNew = x + dir[0];
            int yNew = y + dir[1];
            if(xNew>=0 && xNew<grid.length && yNew>=0 && yNew<grid[0].length && grid[xNew][yNew]=='1' && visited[xNew][yNew]==0){
                dfs(grid, visited, xNew, yNew);
            }
        }
        visited[x][y] = 2;
    }
}
