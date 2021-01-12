package com.mycompany.app;

import java.util.HashSet;
import java.util.Set;

/**
 * In a 1 million by 1 million grid, the coordinates of each grid square are (x, y).
 *
 * We start at the source square and want to reach the target square.  Each move, we can walk to a 4-directionally adjacent square in the grid that isn't in the given list of blocked squares.
 *
 * Return true if and only if it is possible to reach the target square through a sequence of moves.
 *
 *
 *
 * Example 1:
 *
 * Input: blocked = [[0,1],[1,0]], source = [0,0], target = [0,2]
 * Output: false
 * Explanation: The target square is inaccessible starting from the source square, because we can't walk outside the grid.
 * Example 2:
 *
 * Input: blocked = [], source = [0,0], target = [999999,999999]
 * Output: true
 * Explanation: Because there are no blocked cells, it's possible to reach the target square.
 *
 *
 * Constraints:
 *
 * 0 <= blocked.length <= 200
 * blocked[i].length == 2
 * 0 <= blocked[i][j] < 10^6
 * source.length == target.length == 2
 * 0 <= source[i][j], target[i][j] < 10^6
 * source != target
 */

/**
 * Analysis:
 * 详细的解题思路https://xingxingpark.com/Leetcode-1036-Escape-a-Large-Maze/
 * https://www.youtube.com/watch?v=rvHYB6HOmxw&feature=youtu.be
 * 解题的核心是发现这个一个"Bounded Search".
 * 长度为n的block最大能圈住的区间就是跟Maze的两个边共同构成的等边直角三角形.
 * 其被围住的方格数目为：(n-1) + (n-2) + ... +1 = (n-1+1)(n-1)/2 = n(n-1)/2
 */
public class EscapeLargeMaze {
    //Must use Long type instead of Int type
    //Because the largest hash key in this design is (10^6-1)*10^6 + (10^6-1) = 10^12-1
    //While the largest signed int value in java is 2^31-1 = 2147283647 ~= 2.15*10^9
    Long M = 1000000L;
    //Good Trick. Use this make code cleaner.
    int[][] dirs = {{1,0}, {-1,0}, {0,-1},{0,1}};

    public boolean isEscapePossible(int[][] blocked, int[] source, int[] target) {

        Set<Long> b = new HashSet<>(); //blocked square
        //If M is not long, will see overflow here.
        for (int[] n: blocked) b.add(n[0]*M + n[1]);

        //Must check both because there are 2 possibilities when "check" returns "true":
        //1. Actually found T from S; 2. Guaranteed S is not been contained, but did not found T yet.
        //2. Because of the above second case, we need to check both to make sure both S and T are not contained.
        //One optimization here is to return both "found" & "contained" separately instead of just a boolean.
        //This one, you only run the T to S search when the S to T search returned "S not contained".
        return check(b, source, target, source, new HashSet<>())
                && check(b, target, source, target, new HashSet<>());
    }

    /**
     * @param b: blocked squares (x, y) -> x*M + y
     *           the key is guaranteed to be unique, because both x and y is less than M-1
     *           So for (x1, y1) to collides with (x2, y2), we must have x1*M+y1 == x2*M+y2
     *           (x1-x2)*M + (y1-y2) == 0, because both x and y are int within [0, M-1],
     *           So the only case for the above equation to be true is when x1==x2 && y1==y2
     *           This is similar to the case of decimal numbers for d1d2d3 == d4d5d6,
     *           where d here is a decimal digit. For the above to be true, must have d1==d4 && d2==d5 && d3==d6.
     *           So similarly for a 3-Dimension (x, y, z) point, as long as we know the max value of x, y and z as M,
     *           we can design a non-colliding hash key as (x*M^2 + y*M + z).
     * @param s: source square
     * @param t: target square
     * @param cur: current square
     * @param v: visited squares (x, y) -> x*M + y
     */
    //注意我们这里做的并不是原分析中所说的BFS，而是一个DFS
    boolean check(Set<Long> b, int[] s, int[] t, int[] cur, Set<Long> v) {
        /**
         * Only when source and target are separated completely by blocked squares
         * will it be impossible to reach target from source.
         * we can stop the search when cur[i][j] is beyond the
         * largest possible enclosed area formed by blocked squares.
         * 这里选定的是一个400*400的以S为中心的方形区域
         * 原则上选定的"bounded search area"只要比n(n-1)/2大就可以
         */
        if (Math.abs(cur[0] - s[0]) == 200
                || Math.abs(cur[1] - s[1]) == 200
                // found the target
                || v.size() > 0 && cur[0] == t[0] && cur[1]==t[1])
            return true;

        v.add(cur[0]*M + cur[1]);

        for (int[] dir : dirs) {
            int x = cur[0] + dir[0];
            int y = cur[1] + dir[1];
            if (x < 0 || x == M || y < 0 || y == M
                    || v.contains(x*M+y) || b.contains(x*M+y))
                continue;
            if (check(b, s, t, new int[]{x,y}, v)) return true;
        }
        return false;
    }
}
