package com.mycompany.app.dp;

import java.util.*;

/**
 * https://leetcode.com/problems/cherry-pickup/
 * You are given an n x n grid representing a field of cherries, each cell is one of three possible integers.
 *
 * 0 means the cell is empty, so you can pass through,
 * 1 means the cell contains a cherry that you can pick up and pass through, or
 * -1 means the cell contains a thorn that blocks your way.
 * Return the maximum number of cherries you can collect by following the rules below:
 *
 * Starting at the position (0, 0) and reaching (n - 1, n - 1) by moving right or down through valid path cells (cells with value 0 or 1).
 * After reaching (n - 1, n - 1), returning to (0, 0) by moving left or up through valid path cells.
 * When passing through a path cell containing a cherry, you pick it up, and the cell becomes an empty cell 0.
 * If there is no valid path between (0, 0) and (n - 1, n - 1), then no cherries can be collected.
 *
 * Example 1:
 * Input: grid = [[0,1,-1],[1,0,-1],[1,1,1]]
 * Output: 5
 * Explanation: The player started at (0, 0) and went down, down, right right to reach (2, 2).
 * 4 cherries were picked up during this single trip, and the matrix becomes [[0,1,-1],[0,0,-1],[0,0,0]].
 * Then, the player went left, up, up, left to return home, picking up one more cherry.
 * The total number of cherries picked up is 5, and this is the maximum possible.
 *
 * Example 2:
 * Input: grid = [[1,1,-1],[1,-1,1],[-1,1,1]]
 * Output: 0
 *
 * Constraints:
 * n == grid.length
 * n == grid[i].length
 * 1 <= n <= 50
 * grid[i][j] is -1, 0, or 1.
 * grid[0][0] != -1
 * grid[n - 1][n - 1] != -1
 */

public class CherryPickup {
    /**
     * Intuition:
     * Instead of walking from end to beginning, let's reverse the second leg of the path,
     * so we are only considering two paths from the beginning to the end.
     * Notice after t steps, each position (r, c) we could be, is on the line r + c = t.
     * So if we have two people at positions (r1, c1) and (r2, c2), then r2 = r1 + c1 - c2.
     * That means the variables r1, c1, c2 uniquely determine 2 people who have walked the same r1 + c1 number of steps.
     * This sets us up for dynamic programming quite nicely.
     *
     * Algorithm
     * Let dp[r1][c1][c2] be the most number of cherries obtained by two people starting at (r1, c1) and (r2, c2)
     * and walking towards (N-1, N-1) picking up cherries, where r2 = r1+c1-c2.
     * If grid[r1][c1] and grid[r2][c2] are not thorns, then the value of dp[r1][c1][c2] is:
     * (grid[r1][c1] + grid[r2][c2]), plus the maximum of dp[r1+1][c1][c2], dp[r1][c1+1][c2], dp[r1+1][c1][c2+1], dp[r1][c1+1][c2+1] as appropriate.
     * We should also be careful to not double count in case (r1, c1) == (r2, c2).
     *
     * Why did we say it was the maximum of dp[r+1][c1][c2] etc.?
     * It corresponds to the 4 possibilities for person 1 and 2 moving down and right:
     * Person 1 down and person 2 down: dp[r1+1][c1][c2];
     * Person 1 right and person 2 down: dp[r1][c1+1][c2];
     * Person 1 down and person 2 right: dp[r1+1][c1][c2+1];
     * Person 1 right and person 2 right: dp[r1][c1+1][c2+1];
     */

    //题目的思路：
    //1. 首先不能用greedy的思路，先后采两边，每次都摘最多，因为这样两次的总数并不见得是最多的，例子：
    //     1 1 1 0
    //     0 0 1 1
    //     1 0 1 0
    //     0 0 1 1
    //2. 既然不能把分成两次用greedy解决，那就合并成一次解决，这样就可以同步优化，实现最优：
    //   把第二次的反向采摘，变成第一次一共有两个人同时出发采摘，这样跟原问题是等价的。然后整个变量空间就从一个人的(x, y)变成了(x1, y1) & (x2, y2)
    //   然后考虑这4个变量是不是完全独立的：x1 + y1 = x2 + y2，所以并不是完全独立的。 let t = x1 + y1 = x2 + y2 -> y1 = t - x1; y2 = t - x2
    //   所以只需要(t, x1, x2)这3个独立变量就可以了
    //
    //关于这里dp的定义，注意4点：
    //                        1. 表示的不是从当前位置到终点所能pick的数量，而是从起始点到当前点所能Pick的数量
    //                        2. dp[r1][r2]表示的不是一个位置，而是一对位置，其中(r1, r2)并不是(x, y)，而是(x1, x2)
    //                        3. 对于任意给定的t，dp[r1][r2]表达就是当前t下，所有不同的位置组合(r1, r2)所能取得的最大值
    //                        4. 对于每一个新的t,我们需要基于上一个t对应的dp[][]解空间，生成当前t的dp[][]解空间，而且当前t的解空间，只跟t-1的解空间相关，因为每次只能移动1步
    //At step t, let dp[r1][r2] be the most cherries that we can pick up for two people going from (0, 0) to (r1, c1) and (0, 0) to (r2, c2), where c1 = t-r1, c2 = t-r2.
    //Say r1 + c1 = t is the t-th layer. Since our recursion only references the next layer, we only need to keep two layers in memory at a time.

    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        int[][] dp = new int[n][n];
        //set up initial values
        //把全部初值set成 Min_Value,用来表示Unknown，因为0表示Nothing，-1表示Blocked，所以取这个值，方便后面 Math.max()
        for(int[] arr : dp){
            Arrays.fill(arr, Integer.MIN_VALUE);
        }
        //题目里已经说了grid[0][0]!=-1
        dp[0][0] = grid[0][0];

        //注意t的最大值
        for(int t = 1; t<=2*(n-1); t++){
            //对于每个给定的t，需要重新定义一个dp2，存当前t的解空间，避免Overwrite之前t的dp
            int[][] dp2 = new int[n][n];
            for(int[] arr : dp2){
                Arrays.fill(arr, Integer.MIN_VALUE);
            }
            //注意这里的细节，i & j的最大最小值！
            for(int i = Math.max(0, t-(n-1)); i<=Math.min(n-1, t); i++){
                for(int j = Math.max(0, t-(n-1)); j<=Math.min(n-1, t); j++){
                    if(grid[i][t-i]==-1 || grid[j][t-j]==-1){
                        continue;
                    }
                    int val = grid[i][t-i];
                    //avoid double count, only double count case is when i==j, because t-i==t-j
                    if(i!=j){
                        val += grid[j][t-j];
                    }
                    //这里就是遍历4种情况，(right, right), (right, down), (down, right), (down, down)
                    for(int pi = i-1; pi<=i; pi++){
                        for(int pj = j-1; pj<=j; pj++){
                            if(pi>=0 && pj>=0){
                                dp2[i][j] = Math.max(dp2[i][j], dp[pi][pj] + val);
                            }
                        }
                    }
                }
            }
            //注意Update dp to latest dp2
            dp = dp2;
        }

        return Math.max(0, dp[n-1][n-1]);
    }

    /**
     * 错误解法：以下是基于greedy的基本思路，第一遍尽可能的拿多，然后第二遍再尽可能的拿多，
     * 但问题是这样整体并不见得是拿的最多的。
     * 例子：
     *     1 1 1 0
     *     0 0 1 1
     *     1 0 1 0
     *     0 0 1 1
     * 在上面的例子中两次最多可以把所有的1全部拿完，走法分别入下图 * & #:
     *     * * * 0   # 1 1 0
     *     0 0 * *   # 0 1 1
     *     1 0 1 *   # # # 0
     *     0 0 1 *   0 0 # #
     * 而如果是采用greedy的做法，无法拿完所有的1
     */
    //这个题应该算是"Unique Paths"跟"Matrix Rotation"合并出来的
    //基本思路就是按照Unique Paths的逻辑，从左上到右下，然后从右下到左上各走一遍
    //问题是，以上两遍的代码结构上是重复的，可以通过把grid旋转180度，然后就可以reuse从左上到右下的代码
    //额外要注意的就是如何在两次dp之间update grid里面的值，专门有一步dfs的步骤，来把最优路径上的cherry remove掉，从左上开始，永远往更大的方向走就可以了
    public int cherryPickupWrong(int[][] grid) {
        int n = grid.length;
        //int n = grid[0].length;
        int[][] dp = new int[n][n];
        int cnt = 0;
        pick(grid, dp);
        if(dp[0][0]==-1){
            return 0;
        }
        cnt += dp[0][0];
        update(grid, dp, 0, 0);
        rotate(grid);
        for(int[] arr : dp){
            Arrays.fill(arr, 0);
        }
        pick(grid, dp);
        cnt += dp[0][0];
        return cnt;
    }

    private void pick(int[][] grid, int[][] dp){
        //set up initial values
        int n = grid.length;
        dp[n-1][n-1] = grid[n-1][n-1];
        for(int i=n-2; i>=0; i--){
            if(grid[n-1][i]==-1 || dp[n-1][i+1]==-1){
                dp[n-1][i] = -1;
            }
            else{
                dp[n-1][i] = grid[n-1][i] + dp[n-1][i+1];
            }
            if(grid[i][n-1]==-1 || dp[i+1][n-1]==-1){
                dp[i][n-1] = -1;
            }
            else{
                dp[i][n-1] = grid[i][n-1] + dp[i+1][n-1];
            }
        }

        for(int i=n-2; i>=0; i--){
            for(int j=n-2; j>=0; j--){
                if(grid[i][j]==-1 || (dp[i][j+1]==-1 && dp[i+1][j]==-1)){
                    dp[i][j] = -1;
                }
                else{
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j+1]) + grid[i][j];
                }
            }
        }
    }

    private void rotate(int[][] grid){
        int n = grid.length;
        //swap by x
        for(int i=0; i<n; i++){
            int ptr0 = 0;
            int ptr1 = n - 1;
            while(ptr0<=ptr1){
                int temp = grid[ptr0][i];
                grid[ptr0][i] = grid[ptr1][i];
                grid[ptr1][i] = temp;
                ptr0++;
                ptr1--;
            }
        }
        //swap by y
        for(int i=0; i<n; i++){
            int ptr0 = 0;
            int ptr1 = n - 1;
            while(ptr0<=ptr1){
                int temp = grid[i][ptr0];
                grid[i][ptr0] = grid[i][ptr1];
                grid[i][ptr1] = temp;
                ptr0++;
                ptr1--;
            }
        }
    }

    private void update(int[][] grid, int[][] dp, int x, int y){
        int n = grid.length;
        grid[x][y] = 0;

        int x1 = x + 1;
        int y1 = y + 1;
        //(x, y1)
        if(y1<n && (x1>=n || dp[x][y1]>=dp[x1][y])){
            update(grid, dp, x, y1);
            return;
        }
        //(x1, y)
        if(x1<n && (y1>=n || dp[x1][y]>dp[x][y1])){
            update(grid, dp, x1, y);
            return;
        }
    }
}
