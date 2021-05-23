package com.mycompany.app;
import java.util.*;

public class BestMeetingPoint {
    //Notice that the Manhattan distance is the sum of two independent variables.
    //Therefore, once we solve the 1D case, we can solve the 2D case as two independent 1D problems.
    //核心思路在于把问题分成两个dimention上分别处理
    //在x & y维度上，统计1的位置，然后在两个维度上分别求minDistance，方法就是把点放在中间
    public int minTotalDistance(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        List<Integer> xList = new ArrayList<>(m);
        List<Integer> yList = new ArrayList<>(n);
        //i从小到大存一遍
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    xList.add(i);
                }
            }
        }
        //j从小到大存一遍
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i ++) {
                if (grid[i][j] == 1) {
                    yList.add(j);
                }
            }
        }

        return getMin(xList) + getMin(yList);
    }

    //return distance in 1-D
    private int getMin(List<Integer> list){
        int res = 0;

        int i = 0;
        int j = list.size() - 1;
        while(i < j){
            res += list.get(j--) - list.get(i++);
        }

        return res;
    }
}
