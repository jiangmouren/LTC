package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/making-a-large-island/
 * You are given an n x n binary matrix grid. You are allowed to change at most one 0 to be 1.
 * Return the size of the largest island in grid after applying this operation.
 * An island is a 4-directionally connected group of 1s.
 *
 * Example 1:
 * Input: grid = [[1,0],[0,1]]
 * Output: 3
 * Explanation: Change one 0 to 1 and connect two 1s, then we get an island with area = 3.
 *
 * Example 2:
 * Input: grid = [[1,1],[1,0]]
 * Output: 4
 * Explanation: Change the 0 to 1 and make the island bigger, only one island with area = 4.
 *
 * Example 3:
 * Input: grid = [[1,1],[1,1]]
 * Output: 4
 * Explanation: Can't change any 0 to 1, only one island with area = 4.
 *
 * Constraints:
 * n == grid.length
 * n == grid[i].length
 * 1 <= n <= 500
 * grid[i][j] is either 0 or 1.
 */

public class MakingALargeIsland {
    //既可以用Union-Find也可以dfs
    //基本思路就是先dfs求出每个group的size，同时对每个1位置标记其属于哪个group,这部分的复杂度是O(n*n)
    //然后再把matrix过一遍，对每个0的位置，求其连接的部分的group size的加和(注意neighbors may belong to he same group)，keep track of max_sum，这部分的复杂度也是O(n*n)
    //类似也可以用Union-Find实现相同的逻辑和步骤
    public int largestIsland(int[][] grid) {
        int n = grid.length;
        Map<Integer, Integer> map = new HashMap<>();
        int id = 1;
        int[][] group = new int[n][n];
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int maxGroup = 0;

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(grid[i][j]==1 && group[i][j]==0){
                    int cnt = dfs(grid, group, dirs, i, j, id);
                    map.put(id, cnt);
                    maxGroup = Math.max(maxGroup, cnt);
                    id++;
                }
            }
        }

        //注意初值一定要是maxGroup，不能是1，因为有可能grid里面没有0，那么max最后就会保留其初值
        int max = maxGroup;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(grid[i][j]==0){
                    //用个set避免重复计算同一group的neighbors
                    Set<Integer> set = new HashSet<>();
                    int cnt = 1;
                    for(int[] dir : dirs){
                        int xNew = i + dir[0];
                        int yNew = j + dir[1];
                        if(xNew>=0 && xNew<n && yNew>=0 && yNew<n && grid[xNew][yNew]==1){
                            int groupId = group[xNew][yNew];
                            if(!set.contains(groupId)){
                                cnt += map.get(groupId);
                                set.add(groupId);
                            }
                        }
                    }
                    max = Math.max(max, cnt);
                }
            }
        }

        return max;
    }

    //用group matrix同时起到visit matrix的作用，保证从1开始标记，0表示Not visited
    private int dfs(int[][] grid, int[][] group, int[][] dirs, int x, int y, int id){
        int n = grid.length;
        group[x][y] = id;
        //visit[x][y] = true;
        int res = 1;
        for(int[] dir : dirs){
            int xNew = x + dir[0];
            int yNew = y + dir[1];
            if(xNew>=0 && xNew<n && yNew>=0 && yNew<n && grid[xNew][yNew]==1 && group[xNew][yNew]==0){
                res += dfs(grid, group, dirs, xNew, yNew, id);
            }
        }
        return res;
    }
}
