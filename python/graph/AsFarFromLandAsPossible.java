package com.mycompany.app.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/as-far-from-land-as-possible/
 * Given an n x n grid containing only values 0 and 1, where 0 represents water and 1 represents land, find a water cell such that its distance to the nearest land cell is maximized, and return the distance. If no land or water exists in the grid, return -1.
 * The distance used in this problem is the Manhattan distance: the distance between two cells (x0, y0) and (x1, y1) is |x0 - x1| + |y0 - y1|.
 * Example 1:
 * Input: grid = [[1,0,1],[0,0,0],[1,0,1]]
 * Output: 2
 * Explanation: The cell (1, 1) is as far as possible from all the land with distance 2.
 * Example 2:
 * Input: grid = [[1,0,0],[0,0,0],[0,0,0]]
 * Output: 4
 * Explanation: The cell (2, 2) is as far as possible from all the land with distance 4.
 * Constraints:
 * n == grid.length
 * n == grid[i].length
 * 1 <= n <= 100
 * grid[i][j] is 0 or 1
 */

public class AsFarFromLandAsPossible {
    /**
     * Multi-Source BFS
     */
    public int maxDistance2(int[][] grid){
        //Pay attention, LinkedList can represent either a queue or a stack.
        //add() is adding to the end of the list like that of a "Queue".
        //push() is adding to the "Stack", or adding to the first of the list.
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]==1){
                    queue.add(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }

        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        int distance = 0;
        while(!queue.isEmpty()){
            int size = queue.size();//Must take this value before adding new ones into the queue
            //for(int i=0; i<size; i++){
            while(!queue.isEmpty()){
                int[] cur = queue.poll();
                int x = cur[0];
                int y = cur[1];
                for(int[] dir : dirs){
                    int xNew = x + dir[0];
                    int yNew = y + dir[1];
                    if(xNew>=0 && xNew<grid.length && yNew>=0 && yNew<grid[0].length && grid[xNew][yNew]==0 && !visited[xNew][yNew]){
                        queue.add(new int[]{xNew, yNew});
                        visited[xNew][yNew] = true;
                    }
                }
            }
            //注意通常increment batch的distance的时候，都需要做某种check某些条件
            distance = queue.size()>0 ? distance + 1: distance;
        }

        return distance==0 ? -1 : distance;
    }


    //BFS with cache WON'T work!
    //这题目我开始想用一个Cache记录已经做过BFS的位置所找到的距离，那么后面的位置再次搜多的这个点的时候，就可以直接在这个记录的距离上+1
    //这样子是错误的！这种思路来自于tree的某subtree的结果可以拿来用。在某些树Tree的问题里面可以这么做是因为subtree可以被看作“子问题”
    //而在这个问题里面(i,j)位置的搜索结果并不构成(l,k)位置的搜索问题的“子问题”！本质上说这是两个“平行”“独立”的问题。
    public int maxDistance(int[][] grid) {
        int result = Integer.MIN_VALUE;
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]==0){
                    int cur = findDistance(grid, i, j);
                    if(cur==-1){
                        return -1;
                    }
                    result = Math.max(result, cur);
                }
            }
        }

        //no 0 in grid when result==Integer.MAX_VALUE
        return result==Integer.MIN_VALUE ? -1 : result;
    }

    //already checked zero at (x,y) when calling
    //bfs
    private int findDistance(int[][] grid, int x, int y){
        LinkedList<Info> queue = new LinkedList<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        queue.add(new Info(x, y, 0));
        visited[x][y] = true;
        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        while(!queue.isEmpty()){
            Info curNode = queue.pop();
            for(int[] dir : dirs){
                int xNew = curNode.x + dir[0];
                int yNew = curNode.y + dir[1];
                if(xNew<0 || xNew==grid.length || yNew<0 || yNew==grid[0].length || visited[xNew][yNew]){
                    continue;
                }
                if(grid[xNew][yNew]==1){//found the nearest 1, ends here
                    return curNode.dist2Src + 1;
                }
                else{
                    queue.add(new Info(xNew, yNew, curNode.dist2Src + 1));
                    visited[xNew][yNew] = true;//每次往queque里push，都要记得把visited给set了，否则就会无限循环
                }
            }
        }
        return -1;//no 1 in grid
    }

    //bfs queue info
    class Info{
        int x;
        int y;
        int dist2Src;
        public Info(int x, int y, int dist2Src){
            this.x = x;
            this.y = y;
            this.dist2Src = dist2Src;
        }
    }
}
