package Finished;
/**
 * Find the kth largest element in an unsorted array.
 * Note that it is the kth largest element in the sorted order, not the kth distinct element.
 * For example,
 * Given [3,2,1,5,6,4] and k = 2, return 5.
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ array's length.
*/

import java.util.*;

/**
 * Analysis:
 * Typical PriorityQueue problem.
 * Use a minHeap, and maintain the size to be k, then peek will be the Kth largest.
 * Will implement both kth largest and kth smallest.
 */
public class KthLargestElementInAnArray{
    public int kthLargest(int k, int[] array){
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
        for(int tmp : array){
            if(minHeap.size()<k){
                minHeap.add(tmp);
            }
            else{
                if(minHeap.peek()<tmp){
                    minHeap.poll();
                    minHeap.add(tmp);
                }
            }
        }
        return minHeap.peek();
    }

    public int kthSmallest(int k, int[] array){
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for(int tmp : array){
            if(maxHeap.size()<k){
                maxHeap.add(tmp);
            }
            else{
                if(maxHeap.peek()>tmp){
                    maxHeap.poll();
                    maxHeap.add(tmp);
                }
            }
        }
        return maxHeap.peek();
    }
}
