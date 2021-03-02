package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/minimum-knight-moves/
 * In an infinite chess board with coordinates from -infinity to +infinity, you have a knight at square [0, 0].
 * A knight has 8 possible moves it can make, as illustrated below.
 * Each move is two squares in a cardinal direction, then one square in an orthogonal direction.
 * Return the minimum number of steps needed to move the knight to the square [x, y].
 * It is guaranteed the answer exists.
 *
 * Example 1:
 * Input: x = 2, y = 1
 * Output: 1
 * Explanation: [0, 0] → [2, 1]
 *
 * Example 2:
 * Input: x = 5, y = 5
 * Output: 4
 * Explanation: [0, 0] → [2, 1] → [4, 2] → [3, 4] → [5, 5]
 *
 *
 * Constraints:
 * |x| + |y| <= 300
 */
public class MinimumKnightMoves {
    /**
     * 这个问题下面这也写，逻辑上是正确的的，但是会"TimelimitExceeded".
     */
    public int minKnightMoves(int x, int y) {
        int[][] dirs = {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};
        Set<List<Integer>> visited = new HashSet<>();
        Queue<List<Integer>> queue = new LinkedList<>();
        List<Integer> start = new ArrayList<>();
        start.add(0);
        start.add(0);
        queue.add(start);
        visited.add(start);
        int step = -1;
        boolean found = false;
        while(!found && !queue.isEmpty()){
            int cnt = queue.size();
            step++;
            while(cnt>0){
                List<Integer> pos0 = queue.poll();
                int x0 = pos0.get(0);
                int y0 = pos0.get(1);
                if(x0==x && y0==y){
                    found = true;
                    break;
                }
                else{
                    for(int[] dir : dirs){
                        int x1 = x0 + dir[0];
                        int y1 = y0 + dir[1];
                        List<Integer> pos1 = new ArrayList<>();
                        pos1.add(x1);
                        pos1.add(y1);
                        if(!visited.contains(pos1)){
                            visited.add(pos1);
                            queue.add(pos1);
                        }
                    }
                }
                cnt--;
            }
        }
        return step;
    }

    /**
     * 我的第一反应是“难道要写BiDirectionalBFS?”
     * 因为那样确实可以让搜索空间减半（因而时间也减半）
     * 但其实这道题的trick在于，4个象限的对称性！但其实感觉这种解法有点Hacky!尤其，允许x>=-1, y>=-1，只是为了(1, 1).
     * 但同时，并没有证明其它的点不需要绕道2/4象限，来实现最短的路径。总之就是记住就完了。
     * The key thing to note here is
     * x = Math.abs(x);
     * y = Math.abs(y);
     * Here we are forcing the original co-ordinates to be in 1st Quadrant only. ( since we can use symmetry )
     * you cannot reach 1,1 using only 1st quadrant. hence we allow x >=-1 y>=-1 instead of x>=0, y>=0 limit
     */
    public int minKnightMovesSln2(int x, int y) {
        int[][] DIRECTIONS = new int[][] {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};
        x = Math.abs(x);
        y = Math.abs(y);

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {0, 0});

        Set<String> visited = new HashSet<>();//这里避免使用List<Integer>做key，而是使用String是比较好的
        visited.add("0,0");

        int result = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cur = queue.remove();
                int curX = cur[0];
                int curY = cur[1];
                if (curX == x && curY == y) {
                    return result;
                }

                for (int[] d : DIRECTIONS) {
                    int newX = curX + d[0];
                    int newY = curY + d[1];
                    if (!visited.contains(newX + "," + newY) && newX >= -1 && newY >= -1) {
                        queue.add(new int[] {newX, newY});
                        visited.add(newX + "," + newY);
                    }
                }
            }
            result++;
        }
        return -1;
    }
}
