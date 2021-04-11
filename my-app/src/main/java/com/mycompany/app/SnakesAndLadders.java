package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/snakes-and-ladders/
 * On an N x N board, the numbers from 1 to N*N are written boustrophedonically starting from the bottom left of the board,
 * and alternating direction each row.  For example, for a 6 x 6 board, the numbers are written as follows:
 *
 * You start on square 1 of the board (which is always in the last row and first column).
 * Each move, starting from square x, consists of the following:
 *
 * You choose a destination square S with number x+1, x+2, x+3, x+4, x+5, or x+6, provided this number is <= N*N.
 * (This choice simulates the result of a standard 6-sided die roll: ie., there are always at most 6 destinations,
 * regardless of the size of the board.)
 * If S has a snake or ladder, you move to the destination of that snake or ladder.  Otherwise, you move to S.
 * A board square on row r and column c has a "snake or ladder" if board[r][c] != -1.
 * The destination of that snake or ladder is board[r][c].
 * Note that you only take a snake or ladder at most once per move:
 * if the destination to a snake or ladder is the start of another snake or ladder, you do not continue moving.
 * (For example, if the board is `[[4,-1],[-1,3]]`, and on the first move your destination square is `2`,
 * then you finish your first move at `3`, because you do not continue moving to `4`.)
 * Return the least number of moves required to reach square N*N.  If it is not possible, return -1.
 *
 * Example 1:
 * Input: [
 * [-1,-1,-1,-1,-1,-1],
 * [-1,-1,-1,-1,-1,-1],
 * [-1,-1,-1,-1,-1,-1],
 * [-1,35,-1,-1,13,-1],
 * [-1,-1,-1,-1,-1,-1],
 * [-1,15,-1,-1,-1,-1]]
 * Output: 4
 * Explanation:
 * At the beginning, you start at square 1 [at row 5, column 0].
 * You decide to move to square 2, and must take the ladder to square 15.
 * You then decide to move to square 17 (row 3, column 5), and must take the snake to square 13.
 * You then decide to move to square 14, and must take the ladder to square 35.
 * You then decide to move to square 36, ending the game.
 * It can be shown that you need at least 4 moves to reach the N*N-th square, so the answer is 4.
 *
 * Note:
 * 2 <= board.length = board[0].length <= 20
 * board[i][j] is between 1 and N*N or is equal to -1.
 * The board square with number 1 has no snake or ladder.
 * The board square with number N*N has no snake or ladder.
 */
public class SnakesAndLadders {
    public int snakesAndLadders(int[][] board) {
        //这就是一个包装的有点复杂的bfs题目,甚至这个matrix都是障眼法，因为并不能自由的访问neighbors
        //而只能访问x+(1-6),但是因为位这个snake跟ladder的存在，所以我们需要换算出x+(1-6)对应的matrix的index
        Queue<Integer> queue = new LinkedList<>();
        int n = board.length;
        int res = -1;
        boolean found = false;
        int steps = 0;
        queue.add(1);
        boolean[] visited = new boolean[n*n];
        visited[0] = true;
        int cnt = queue.size();
        while(!queue.isEmpty()){
            while(cnt>0){
                int x = queue.poll();
                if(x==n*n){
                    found = true;
                    res = steps;
                    break;
                }
                cnt--;
                for(int i=1; i<=6; i++){
                    if(x+i<=n*n){
                        int[] idx = getIndex(n, x+i);
                        int val = board[idx[0]][idx[1]];
                        if(val!=-1){
                            //注意这个visited的check是在里面做，而不是在外面做，原因是从一个点“出发”过，并不阻止你“途径”那个点，这两种情况是不一样的，所以不算loop back
                            if(!visited[val-1]){
                                queue.add(val);
                                visited[val-1] = true;
                            }
                        }
                        else{
                            if(!visited[x+i-1]){
                                queue.add(x+i);
                                visited[x+i-1] = true;
                            }
                        }
                    }
                }
            }
            if(found){
                break;
            }
            steps++;
            cnt = queue.size();
        }
        return res;
    }

    private int[] getIndex(int n, int x){
        int row = n-1-(x-1)/n;
        int col;
        if(((x-1)/n)%2==0){//->
            col = (x-1)%n;
        }
        else{
            col = n-1-(x-1)%n;
        }
        int[] idx = {row, col};
        return idx;
    }
}
