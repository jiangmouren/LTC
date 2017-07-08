package Finished.dp;

/**
 * Created by jiangmouren on 6/4/17.
 */

/**
 * Question:
 * There are a row of n houses, each house can be painted with one of the three colors: red, blue or green.
 * The cost of painting each house with a certain color is different.
 * You have to paint all the houses such that no two adjacent houses have the same color.
 * The cost of painting each house with a certain color is represented by a n x 3 cost matrix.
 * For example, costs[0][0] is the cost of painting house 0 with color red;
 * costs[1][2] is the cost of painting house 1 with color green, and so on...
 * Find the minimum cost to paint all houses.
 * Note:
 * All costs are positive integers.
 */

/**
 * Analysis:
 * Target = Min{R(n), B(n), G(n)}
 * R(n): the Min cost with the nth house painted as Red, B(n) --> Blue, G(n) --> Green.
 * R(n) = Min{B(n-1), G(n-1)} + cost[n][0]
 * B(n) = Min{R(n-1), G(n-1)} + cost[n][1]
 * G(n) = Min{B(n-1), R(n-1)} + cost[n][2]
 * R(0) = cost[0][0], B(0) = cost[0][1], G(0) = cost[0][2]
 */
public class PaintHouse {
    public int minCost(int[][] costs) {
        if(costs==null) throw new IllegalArgumentException("Inputs cannot be null");
        if(costs.length==0) throw new IllegalArgumentException("Inputs cannot be empty");

        int r=costs[0][0], b=costs[0][1], g=costs[0][2];
        int r_nxt, b_nxt, g_nxt;
        for(int i=1; i<costs.length; i++){
            r_nxt = Math.min(b, g) + costs[i][0];
            b_nxt = Math.min(r, g) + costs[i][1];
            g_nxt = Math.min(r, b) + costs[i][2];
            r = r_nxt;
            b = b_nxt;
            g = g_nxt;
        }
        return Math.min(r, Math.min(b, g));
    }
}
