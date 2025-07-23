package com.mycompany.app.graph;
import java.util.*;
/**
 * https://leetcode.com/problems/walls-and-gates/
 * You are given an m x n grid rooms initialized with these three possible values.
 *
 * -1 A wall or an obstacle.
 * 0 A gate.
 * INF Infinity means an empty room.
 * We use the value 2^31 - 1 = 2147483647 to represent INF as you may assume that
 * the distance to a gate is less than 2147483647.
 * Fill each empty room with the distance to its nearest gate.
 * If it is impossible to reach a gate, it should be filled with INF.
 *
 * Example 1:
 * Input: rooms = [[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]
 * Output: [[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]
 *
 * Example 2:
 * Input: rooms = [[-1]]
 * Output: [[-1]]
 *
 * Constraints:
 * m == rooms.length
 * n == rooms[i].length
 * 1 <= m, n <= 250
 * rooms[i][j] is -1, 0, or 2^31 - 1.
 */

public class WallsAndGates{
    public void wallsAndGates(int[][] rooms) {
        Queue<int[]> queue = new LinkedList<>();
        //boolean[][] visited = new boolean[rooms.length][rooms[0].length];
        for(int i=0; i<rooms.length; i++){
            for(int j=0; j<rooms[0].length; j++){
                if(rooms[i][j]==0){
                    int[] pos = {i, j};
                    queue.add(pos);
                }
            }
        }
        int distance = 1;
        int cnt = queue.size();
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        while(!queue.isEmpty()){
            while(cnt>0){
                int[] pos = queue.poll();
                cnt--;
                //rooms[pos[0]][pos[1]] = distance;
                for(int[] dir : dirs){
                    int xNew = pos[0] + dir[0];
                    int yNew = pos[1] + dir[1];
                    if(xNew>=0 && yNew>=0 && xNew<rooms.length && yNew<rooms[0].length && rooms[xNew][yNew]==Integer.MAX_VALUE){//2147483647
                        //System.out.println("here");
                        int[] posNew = {xNew, yNew};
                        queue.add(posNew);
                        rooms[xNew][yNew] = distance;//我们把set distance，当做set visited来用
                    }
                }
            }
            cnt = queue.size();
            distance++;
        }
    }
}