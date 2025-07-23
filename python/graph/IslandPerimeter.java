package com.mycompany.app.graph;

/**
 * https://leetcode.com/problems/island-perimeter/
 * You are given row x col grid representing a map where grid[i][j] = 1 represents land and grid[i][j] = 0 represents water.
 * Grid cells are connected horizontally/vertically (not diagonally).
 * The grid is completely surrounded by water, and there is exactly one island (i.e., one or more connected land cells).
 * The island doesn't have "lakes", meaning the water inside isn't connected to the water around the island.
 * One cell is a square with side length 1. The grid is rectangular, width and height don't exceed 100.
 * Determine the perimeter of the island.
 *
 * Example 1:
 * Input: grid = [[0,1,0,0],[1,1,1,0],[0,1,0,0],[1,1,0,0]]
 * Output: 16
 * Explanation: The perimeter is the 16 yellow stripes in the image above.
 *
 * Example 2:
 * Input: grid = [[1]]
 * Output: 4
 *
 * Example 3:
 * Input: grid = [[1,0]]
 * Output: 4
 *
 * Constraints:
 * row == grid.length
 * col == grid[i].length
 * 1 <= row, col <= 100
 * grid[i][j] is 0 or 1.
 * There is exactly one island in grid.
 */
public class IslandPerimeter {
    //首先perimeter是“周长”的意思
    //dfs, 在每个cell往4个方向看，判断要不要增加周长
    public int islandPerimeter(int[][] grid) {
        int width = grid[0].length;
        int height = grid.length;
        //二位数组第一个index表示第几行；第二index表示第几列
        boolean[][] visited = new boolean[height][width];//default to false
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int res = 0;
        boolean found = false;
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                if(grid[i][j]==1){//becasue there is exactly one island
                    res = dfs(i, j, grid, visited, dirs);
                    found = true;
                    break;
                }
            }
            if(found){
                break;
            }
        }
        return res;
    }

    private int dfs(int x, int y, int[][] grid, boolean[][] visited,int[][] dirs){
        int res = 0;
        visited[x][y] = true;

        for(int[] dir : dirs){
            int x1 = x + dir[0];
            int y1 = y + dir[1];
            if(x1<0 || x1>=grid.length || y1<0 || y1>=grid[0].length || grid[x1][y1]==0){
                res ++;
            }
            else{
                if(!visited[x1][y1]){
                    res += dfs(x1, y1, grid, visited, dirs);
                }
            }
        }
        return res;
    }
}
