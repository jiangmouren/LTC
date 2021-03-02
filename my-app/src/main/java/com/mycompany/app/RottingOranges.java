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
        //这就是一个multi-source的BFS
        //跟https://leetcode.com/problems/as-far-from-land-as-possible/ 一样
        //先把source都找出来
        Queue<List<Integer>> queue = new LinkedList<>();
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]==2){
                    List<Integer> pos = new ArrayList<>();
                    pos.add(i);
                    pos.add(j);
                    queue.add(pos);
                }
            }
        }
        int time = 0;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        while(!queue.isEmpty()){
            int cnt = queue.size();
            while(cnt>0){
                List<Integer> cur = queue.poll();
                for(int[] dir : dirs){
                    int xNew = cur.get(0) + dir[0];
                    int yNew = cur.get(1) + dir[1];
                    if(xNew>=0 && yNew>=0 && xNew<grid.length && yNew<grid[0].length && grid[xNew][yNew]==1){
                        List<Integer> temp = new ArrayList<>();
                        temp.add(xNew);
                        temp.add(yNew);
                        grid[xNew][yNew] = 2;
                        queue.add(temp);
                    }
                }
                cnt--;
            }
            time++;
        }
        time = time>0 ? time -1 : 0;//这种情况是为了处理grid里压根没有2的情况

        boolean clear = true;
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]==1){
                    clear = false;
                }
            }
        }
        return clear ? time : -1;
    }
}
