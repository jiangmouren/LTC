package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/
 * Given an n x n matrix where each of the rows and columns are sorted in ascending order,
 * return the kth smallest element in the matrix.
 * Note that it is the kth smallest element in the sorted order, not the kth distinct element.
 *
 * Example 1:
 * Input: matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
 * Output: 13
 * Explanation: The elements in the matrix are [1,5,9,10,11,12,13,13,15], and the 8th smallest number is 13
 *
 * Example 2:
 * Input: matrix = [[-5]], k = 1
 * Output: -5
 *
 * Constraints:
 * n == matrix.length
 * n == matrix[i].length
 * 1 <= n <= 300
 * -109 <= matrix[i][j] <= 109
 * All the rows and columns of matrix are guaranteed to be sorted in non-decreasing order.
 * 1 <= k <= n^2
 */

/**
 * 这个题比较直观的解题思路，就是merge k sorted lists，就是下面的解法。
 * Time Complexity: \text{let X=} \text{min}(K, N); X + K \log(X)let X=min(K,N);X+Klog(X)
 *
 * Well the heap construction takes O(X)O(X) time.
 * After that, we perform KK iterations and each iteration has two operations. We extract the minimum element from a heap containing XX elements. Then we add a new element to this heap. Both the operations will take O(\log(X))O(log(X)) time.
 * Thus, the total time complexity for this algorithm comes down to be O(X + K\log(X))O(X+Klog(X)) where XX is \text{min}(K, N)min(K,N).
 * Space Complexity: O(X)O(X) which is occupied by the heap.
 */
public class KthSmallestElementInASortedMatrix{
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>(k, (a, b)->a[0]-b[0]);
        for(int i=0; i<n && i<k; i++){
            pq.add(new int[]{matrix[i][0], i, 0});
        }
        int res = 0;
        for(int i=0; i<k; i++){
            int[] cur = pq.poll();
            res = cur[0];
            if(cur[2]<n-1){
                pq.add(new int[]{matrix[cur[1]][cur[2]+1], cur[1], cur[2]+1});
            }
        }
        return res;
    }

    //还有一种基于binary search的解法，套路有点偏，详见：
    //https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/solution/
}