package com.mycompany.app.graph;

/**
 * https://leetcode.com/problems/max-area-of-island/
 * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land)
 * connected 4-directionally (horizontal or vertical.)
 * You may assume all four edges of the grid are surrounded by water.
 * Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)
 *
 * Example 1:
 * [[0,0,1,0,0,0,0,1,0,0,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,1,1,0,1,0,0,0,0,0,0,0,0],
 *  [0,1,0,0,1,1,0,0,1,0,1,0,0],
 *  [0,1,0,0,1,1,0,0,1,1,1,0,0],
 *  [0,0,0,0,0,0,0,0,0,0,1,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 * Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.
 *
 * Example 2:
 * [[0,0,0,0,0,0,0,0]]
 * Given the above grid, return 0.
 * Note: The length of each dimension in the given grid does not exceed 50.
 */
public class MaxAreaOfIsland {
    //这题目既可以用UnionFind，也可以用DFS，UnionFind写起来比较麻烦，还要写id映射
    public int maxAreaOfIsland(int[][] grid) {
        int max = Integer.MIN_VALUE;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]==1){
                    max = Math.max(max, dfs(grid, i, j, dirs));
                }
            }
        }
        return max==Integer.MIN_VALUE ? 0 : max;
    }

    private int dfs(int[][] grid, int x, int y, int[][] dirs){
        grid[x][y] = 0;
        int cnt = 1;
        for(int[] dir : dirs){
            int xNew = x + dir[0];
            int yNew = y + dir[1];
            if(xNew>=0 && yNew>=0 && xNew<grid.length && yNew<grid[0].length && grid[xNew][yNew]==1){
                cnt += dfs(grid, xNew, yNew, dirs);
            }
        }
        return cnt;
    }
}
