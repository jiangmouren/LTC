package com.mycompany.app.graph.dijkstra;
import java.util.*;
/**
 * https://leetcode.com/problems/find-the-kth-smallest-sum-of-a-matrix-with-sorted-rows/
 * You are given an m x n matrix mat that has its rows sorted in non-decreasing order and an integer k.
 * You are allowed to choose exactly one element from each row to form an array.
 * Return the kth smallest array sum among all possible arrays.
 *
 * Example 1:
 * Input: mat = [[1,3,11],[2,4,6]], k = 5
 * Output: 7
 * Explanation: Choosing one element from each row, the first k smallest sum are:
 * [1,2], [1,4], [3,2], [3,4], [1,6]. Where the 5th sum is 7.
 *
 * Example 2:
 * Input: mat = [[1,3,11],[2,4,6]], k = 9
 * Output: 17
 *
 * Example 3:
 * Input: mat = [[1,10,10],[1,4,5],[2,3,6]], k = 7
 * Output: 9
 * Explanation: Choosing one element from each row, the first k smallest sum are:
 * [1,1,2], [1,1,3], [1,4,2], [1,4,3], [1,1,6], [1,5,2], [1,5,3]. Where the 7th sum is 9.
 *
 *
 * Constraints:
 * m == mat.length
 * n == mat.length[i]
 * 1 <= m, n <= 40
 * 1 <= mat[i][j] <= 5000
 * 1 <= k <= min(200, nm)
 * mat[i] is a non-decreasing array.
 */
public class FindTheKthSmallestSumOfAMatrixWithSortedRows {
    public int kthSmallest(int[][] mat, int k) {
        //这个问题的本质类似于在graph里面找到K个距离source最近的点
        //然后这每个edge的cost还有不同，所以这就是一个隐藏的Dijkstra问题
        PriorityQueue<Node> queue = new PriorityQueue<>((a, b)->a.sum-b.sum);
        Set<String> visited = new HashSet<>();
        int[] indices = new int[mat.length];
        String key = getKey(indices);
        int sum = 0;
        for(int i=0; i<mat.length; i++){
            sum += mat[i][0];
        }
        Node node = new Node(indices, sum);
        visited.add(key);
        queue.add(node);
        int cnt = 0;
        int res = 0;
        while(!queue.isEmpty() && cnt<k){
            Node cur = queue.poll();
            //System.out.println("sum: "+cur.sum);
            cnt++;
            //System.out.println("cnt: "+cnt);
            if(k==cnt){
                res = cur.sum;
            }
            populateQueue(cur, queue, mat, visited);
        }
        return res;
    }

    class Node{
        int sum;
        int[] indices;
        public Node(int[] indices, int sum){
            this.indices = indices;
            this.sum = sum;
        }
    }

    private void populateQueue(Node cur, PriorityQueue queue, int[][] mat, Set<String> visited){
        int[] indices = cur.indices;
        for(int i=0; i<indices.length; i++){
            if(indices[i]+1<mat[0].length){
                int[] indicesNew = Arrays.copyOf(indices, indices.length);
                indicesNew[i]++;
                if(!visited.contains(getKey(indicesNew))){
                    int sum = cur.sum;
                    sum -= mat[i][indices[i]];
                    sum += mat[i][indices[i]+1];
                    //System.out.println("adding: "+sum+" at: "+indicesNew[0]+", "+indicesNew[1]);
                    Node node = new Node(indicesNew, sum);
                    queue.add(node);
                    visited.add(getKey(indicesNew));
                }
            }
        }
    }

    private String getKey(int[] indices){
        StringBuilder buf = new StringBuilder();
        for(int idx : indices){
            buf.append(idx);
            buf.append('-');
        }
        return buf.toString();
    }
}
