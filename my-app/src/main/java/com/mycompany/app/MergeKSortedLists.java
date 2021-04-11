package com.mycompany.app;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/merge-k-sorted-lists/
 * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
 * Merge all the linked-lists into one sorted linked-list and return it.
 *
 * Example 1:
 * Input: lists = [[1,4,5],[1,3,4],[2,6]]
 * Output: [1,1,2,3,4,4,5,6]
 * Explanation: The linked-lists are:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * merging them into one sorted list:
 * 1->1->2->3->4->4->5->6
 *
 * Example 2:
 * Input: lists = []
 * Output: []
 *
 * Example 3:
 * Input: lists = [[]]
 * Output: []
 *
 * Constraints:
 *
 * k == lists.length
 * 0 <= k <= 10^4
 * 0 <= lists[i].length <= 500
 * -10^4 <= lists[i][j] <= 10^4
 * lists[i] is sorted in ascending order.
 * The sum of lists[i].length won't exceed 10^4.
 *
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

public class MergeKSortedLists{
    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    //Time O(M*logN), M是总的node个数，N是lists.length
    //Space O(N) because of the PriorityQueue
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> queue = new PriorityQueue<>(10, (a, b)->a.val-b.val);
        ListNode dummyHead = new ListNode();
        ListNode ptr = dummyHead;
        for(ListNode node : lists){
            if(node!=null){
                queue.add(node);
            }
        }
        while(queue.size()>1){//注意这里不是check empty,而是check size()>1
            ListNode cur = queue.poll();
            ptr.next = cur;
            ptr = ptr.next;
            if(cur.next!=null){
                queue.add(cur.next);
            }
        }
        //append the remaining one
        ptr.next = queue.poll();
        return dummyHead.next;
    }
}

/**
 * 这个中解法可以推广来处理 merge K sorted ArrayList
 * 比如:
 * public List<Integer> mergeKLists(List<List<Integer>> lists){
 *     //
 * }
 * 我需要构造一个Node type,来模仿上面ListNode的指针功能
 * class Node{
 *     int id;//用来标记是哪个list,使用其在lists中的index做id
 *     int offset;//用来标记某个list，当前读到了哪个位置
 *     int val;//当前位置的值
 * }
 * 那么整个流程，就跟上述merge linkedList的过程一样：
 * 使用一个PriorityQueue， 然后把每个list构造一个上述Node加到priorityQueue里面去
 * 然后没次从PriorityQueue取出一个值，都判断，当前的list，根据其offset，还有没有后续值
 * 如果没有后续值，就把当前list从上述priorityQueue中取出，如果还有就跟新当前node的offset and val,在add回priorityQueue
 * 最后再把priorityQueue里剩的最后一个list的entry append到结果中去
 */