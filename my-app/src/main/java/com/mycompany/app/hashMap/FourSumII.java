package com.mycompany.app.hashMap;

import java.util.*;

/**
 * https://leetcode.com/problems/4sum-ii/
 * Given four lists A, B, C, D of integer values, compute how many tuples (i, j, k, l) there are such that A[i] + B[j] + C[k] + D[l] is zero.
 *
 * To make problem a bit easier, all A, B, C, D have same length of N where 0 ≤ N ≤ 500.
 * All integers are in the range of -228 to 228 - 1 and the result is guaranteed to be at most 231 - 1.
 *
 * Example:
 * Input:
 * A = [ 1, 2]
 * B = [-2,-1]
 * C = [-1, 2]
 * D = [ 0, 2]
 * Output:
 * 2
 * Explanation:
 * The two tuples are:
 * 1. (0, 0, 0, 1) -> A[0] + B[0] + C[0] + D[1] = 1 + (-2) + (-1) + 2 = 0
 * 2. (1, 1, 0, 0) -> A[1] + B[1] + C[0] + D[0] = 2 + (-1) + (-1) + 0 = 0
 */
public class FourSumII {
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        Map<Integer, Integer> map = new HashMap<>();
        populateMap(map, A, B);
        int res = 0;
        for(int c: C){
            for(int d: D){
                int temp = c + d;
                if(map.containsKey(0-temp)){
                    res += map.get(0-temp);
                }
            }
        }
        return res;
    }

    private void populateMap(Map<Integer, Integer> map, int[] A, int[] B){
        for(int a : A){
            for(int b : B){
                int temp = a + b;
                if(!map.containsKey(temp)){
                    map.put(temp, 1);
                }
                else{
                    int cnt = map.get(temp);
                    map.put(temp, ++cnt);
                }
            }
        }
    }
}
