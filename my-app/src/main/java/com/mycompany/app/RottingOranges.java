package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/rotting-oranges/
 * You are given an m x n grid where each cell can have one of three values:
 *
 * 0 representing an empty cell,
 * 1 representing a fresh orange, or
 * 2 representing a rotten orange.
 * Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.
 * Return the minimum number of minutes that must elapse until no cell has a fresh orange.
 * If this is impossible, return -1.
 *
 * Example 1:
 * Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
 * Output: 4
 *
 * Example 2:
 * Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
 * Output: -1
 * Explanation: The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
 *
 * Example 3:
 * Input: grid = [[0,2]]
 * Output: 0
 * Explanation: Since there are already no fresh oranges at minute 0, the answer is just 0.
 *
 *
 * Constraints:
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 10
 * grid[i][j] is 0, 1, or 2.
 */
public class RottingOranges {
    public int orangesRotting(int[][] grid) {
        //find all the sources
        Queue<int[]> queue = new LinkedList<>();
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]==2){
                    int[] pos = {i, j};
                    queue.add(pos);
                }
            }
        }
        int time = 0;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        while(!queue.isEmpty()){
            int cnt = queue.size();
            boolean updated = false;
            while(cnt>0){
                int[] pos = queue.poll();
                cnt--;
                for(int[] dir : dirs){
                    int x = pos[0] + dir[0];
                    int y = pos[1] + dir[1];
                    if(x>=0 && y>=0 && x<grid.length && y<grid[0].length && grid[x][y]==1){
                        //you can set to 2 instead of 3, becasue we don't need to worry about consusing others
                        grid[x][y] = 2;
                        updated = true;
                        int[] posNew = {x, y};
                        queue.add(posNew);
                    }
                }
            }
            if(updated){
                time++;
            }
        }
        //check if there is still fresh
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]==1){
                    return -1;
                }
            }
        }
        return time;
    }

    //以下是类似game of life的一种写法，这种写法不是最优的，但实际在leetcode上跑出来的结果还不错，进了100%
    public int orangesRottingUpdate(int[][] grid) {
        boolean update = true;
        int cnt = 0;
        while(update){
            update = update(grid);
            if(update){
                cnt++;
            }
        }

        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]==1){
                    return -1;
                }
            }
        }

        return cnt;
    }

    private boolean update(int[][] grid){
        int[][] dirs = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        boolean update = false;
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]==1){
                    for(int[] dir : dirs){
                        int x = i + dir[0];
                        int y = j + dir[1];
                        if(x>=0 && y>=0 && x<grid.length && y<grid[0].length && grid[x][y]==2){
                            grid[i][j] = 3;
                            update = true;
                            break;
                        }
                    }
                }
            }
        }
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]==3){
                    grid[i][j] = 2;
                }
            }
        }
        return update;
    }
}
