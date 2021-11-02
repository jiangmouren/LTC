package com.mycompany.app.priorityQueue;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/k-closest-points-to-origin/
 * We have a list of points on the plane.  Find the K closest points to the origin (0, 0).
 * (Here, the distance between two points on a plane is the Euclidean distance.)
 * You may return the answer in any order.  The answer is guaranteed to be unique (except for the order that it is in.)
 *
 * Example 1:
 * Input: points = [[1,3],[-2,2]], K = 1
 * Output: [[-2,2]]
 * Explanation:
 * The distance between (1, 3) and the origin is sqrt(10).
 * The distance between (-2, 2) and the origin is sqrt(8).
 * Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
 * We only want the closest K = 1 points from the origin, so the answer is just [[-2,2]].
 *
 * Example 2:
 * Input: points = [[3,3],[5,-1],[-2,4]], K = 2
 * Output: [[3,3],[-2,4]]
 * (The answer [[-2,4],[3,3]] would also be accepted.)
 *
 *
 * Note:
 * 1 <= K <= points.length <= 10000
 * -10000 < points[i][0] < 10000
 * -10000 < points[i][1] < 10000
 */
public class KClosestPointsToOrigin {
    /**
     * 这个问题可以直接sort，也可以用PriorityQueue解决，复杂度上一样的O(nlog(n))
     * Pay attention, we need to do the reverse order, in order to get a Max PriorityQueue
     * 很明显，在实践中，我会更prefer下面这种写法，简练的的多。
     */
    public int[][] kClosestSort(int[][] points, int K) {
        //这里刻意只有距离平方，不求真实距离，因为不想出现小数，出现小数，就不能简单的把"cmp" cast到int了，需要小心类似0.5被cast成0
        Arrays.sort(points, (a, b)->(int)(Math.pow(a[0],2)+Math.pow(a[1],2)-Math.pow(b[0],2)-Math.pow(b[1],2)));
        int[][]results = new int[K][points[0].length];
        for(int i=0; i<K; i++){
            results[i] = points[i];
        }
        return results;
    }

    /**
     * 这个问题可以直接sort，也可以用PriorityQueue解决，复杂度上一样的O(nlog(n))
     * Pay attention, we need to do the reverse order, in order to get a Max PriorityQueue
     */
    public int[][] kClosestHeap(int[][] points, int K) {
        PriorityQueue<int[]> queue = new PriorityQueue<>(K, (a, b)->(int)(Math.pow(b[0],2)+Math.pow(b[1],2)-Math.pow(a[0],2)-Math.pow(a[1],2)));
        for(int[] point : points){
            if(queue.size()<K){
                queue.add(point);
            }
            else{
                //Attention: '^' is bitwise xor not exponent!!!
                int curDis = (int)(Math.pow(point[0],2) + Math.pow(point[1],2));
                int[] cur = queue.peek();
                int curMax = (int)(Math.pow(cur[0],2) + Math.pow(cur[1],2));
                if(curMax>curDis){
                    queue.poll();
                    queue.add(point);
                }
            }
        }
        int[][] result = new int[queue.size()][points[0].length];
        return queue.toArray(result);
    }

    /**
     * 理论上这个题还可以用QuickSelect来实现，average complexity: O(n)
     */
    public int[][] kClosestQuickSelect(int[][] points, int K) {
        int left = 0;
        int right = points.length-1;
        int[][] res = new int[K][2];
        while(left<=right){
            int pos = partition(points, left, right);
            if(pos==K-1){
                for(int i=0; i<K; i++){
                    res[i] = points[i];
                }
                break;
            }
            else if(pos<K-1){
                left = pos + 1;
            }
            else{
                right = pos - 1;
            }
        }
        return res;
    }

    private int partition(int[][] points, int start, int end){
        int left = start + 1;
        int right = end;
        while(left<=right){
            //points[left]<=points[start]
            while(left<=right && getDistance(points[left])<=getDistance(points[start])){
                left++;
            }
            //points[right]>points[start]
            while(right>=left && getDistance(points[right])>getDistance(points[start])){
                right--;
            }
            if(left<right){
                swap(points, left, right);
            }
        }
        swap(points, start, right);
        return right;
    }

    private int getDistance(int[] point){
        return (int)(Math.pow(point[0], 2) + Math.pow(point[1], 2));
    }

    private void swap(int[][] points, int ptr0, int ptr1){
        int[] temp = points[ptr0];
        points[ptr0] = points[ptr1];
        points[ptr1] = temp;
    }
}
