package com.mycompany.app;

/**
 * https://leetcode.com/problems/unique-paths-iii/submissions/
 * On a 2-dimensional grid, there are 4 types of squares:
 * 1 represents the starting square.  There is exactly one starting square.
 * 2 represents the ending square.  There is exactly one ending square.
 * 0 represents empty squares we can walk over.
 * -1 represents obstacles that we cannot walk over.
 * Return the number of 4-directional walks from the starting square to the ending square, that walk over every non-obstacle square exactly once.
 *
 * Example 1:
 * Input: [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
 * Output: 2
 * Explanation: We have the following two paths:
 * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
 * 2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)
 *
 * Example 2:
 * Input: [[1,0,0,0],[0,0,0,0],[0,0,0,2]]
 * Output: 4
 * Explanation: We have the following four paths:
 * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2),(2,3)
 * 2. (0,0),(0,1),(1,1),(1,0),(2,0),(2,1),(2,2),(1,2),(0,2),(0,3),(1,3),(2,3)
 * 3. (0,0),(1,0),(2,0),(2,1),(2,2),(1,2),(1,1),(0,1),(0,2),(0,3),(1,3),(2,3)
 * 4. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2),(2,3)
 *
 * Example 3:
 * Input: [[0,1],[2,0]]
 * Output: 0
 * Explanation:
 * There is no path that walks over every empty square exactly once.
 * Note that the starting and ending square can be anywhere in the grid.
 *
 * Note:
 * 1 <= grid.length * grid[0].length <= 20
 */

/**
 * 这个跟之前两道UniquePath不同，主要是不在存在一个漂亮的sub-problem的结构，所以无法用DP.
 * 只能暴力搜索。
 * 对于这个问题的复杂度分析：https://leetcode.com/problems/unique-paths-iii/solution/
 * 我并不同意上面Link里说的O（3^N）的时间复杂度。我认为应该是O(N*3^N)。
 * 因为 时间复杂度 = Sum(length of each path from start to end/dead-end)
 *              = O(# of possible paths from start to end(or blocked dead-end) * O(length of path)
 *              = O(3^N) * O(N) = O(N*3^N)
 * 3^N comes from: total N cells, at each cell you have 3 directions (can't go back to parent, except for start),
 * so at most have 4*3^(N-1) --> O(3^N)
 * 空间复杂度就是O(N)
 */
public class UniquePathsIII {
    public int uniquePathsIII(int[][] grid) {
        boolean[][] visiting = new boolean[grid.length][grid[0].length];
        int[] cnt = {0};
        int[] start = findStart(grid);
        dfs(grid, visiting, cnt, start[0], start[1]);
        return cnt[0];
    }

    //won't call this with -1 positions
    private void dfs(int[][] grid, boolean[][] visiting, int[] cnt, int x, int y){
        //set current postion as visiting
        visiting[x][y] = true;

        //end condition
        if(grid[x][y]==2){
            if(check(grid, visiting)){
                //for(int i=0; i<grid.length; i++){
                //    for(int j=0; j<grid[0].length; j++){
                //        System.out.println(visiting[i][j]);
                //    }
                //}
                //System.out.println(cnt[0]);
                cnt[0]++;
            }
            //reset visiting
            visiting[x][y] = false;
            return;
        }

        //recursive condition
        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        for(int[] dir : dirs){
            int xNew = x + dir[0];
            int yNew = y + dir[1];
            if(xNew>=0 && xNew<grid.length && yNew>=0 && yNew<grid[0].length && !visiting[xNew][yNew] && grid[xNew][yNew]!=-1){
                dfs(grid, visiting, cnt, xNew, yNew);
            }
        }
        //reset visiting
        visiting[x][y] = false;
        return;
    }

    //return true if meets condition
    private boolean check(int[][] grid, boolean[][] visiting){
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]!=-1 && !visiting[i][j]){
                    return false;
                }
                if(grid[i][j]==-1 && visiting[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    //return starting position
    private int[] findStart(int[][] grid){
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]==1){
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }
}
