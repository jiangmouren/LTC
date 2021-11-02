package com.mycompany.app.linearStructure;

/**
 * https://leetcode.com/problems/linked-list-cycle-ii/
 * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
 * There is a cycle in a linked list if there is some node in the list
 * that can be reached again by continuously following the next pointer.
 * Internally, pos is used to denote the index of the node that tail's next pointer is connected to.
 * Note that pos is not passed as a parameter.
 * Notice that you should not modify the linked list.
 *
 * Example 1:
 * Input: head = [3,2,0,-4], pos = 1
 * Output: tail connects to node index 1
 * Explanation: There is a cycle in the linked list, where tail connects to the second node.
 *
 * Example 2:
 * Input: head = [1,2], pos = 0
 * Output: tail connects to node index 0
 * Explanation: There is a cycle in the linked list, where tail connects to the first node.
 *
 * Example 3:
 * Input: head = [1], pos = -1
 * Output: no cycle
 * Explanation: There is no cycle in the linked list.
 *
 * Constraints:
 * The number of the nodes in the list is in the range [0, 104].
 * -105 <= Node.val <= 105
 * pos is -1 or a valid index in the linked-list.
 *
 * Follow up: Can you solve it using O(1) (i.e. constant) memory?
 */

/**
 * 关于这个题Floyd's Algo解法的分析：
 * src\main\resources\LinkedListCycleII.jpg
 */
public class LinkedListCycleII {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
    //这个题目正常的思路就是graph里面detect cycle的解法，用个HashSet就可以了
    //但是这个题主要是为了一种叫Floyd's Algorithm的神奇解法。
    public ListNode detectCycle(ListNode head) {
        if(head==null){
            return null;
        }
        ListNode ptr0 = head;
        ListNode ptr1 = head;
        //注意这里最好就是用do-while，如果用while，也不能用ptr0=head来给第一次放行
        //因为有可能loop的起始点就在head,那么后面就反复放行了,可以用个fistTime Flag
        do{
            ptr0 = ptr0.next;
            ptr1 = ptr1.next;
            ptr1 = ptr1==null ? null : ptr1.next;
        }while(ptr0!=ptr1 && ptr1!=null); //注意这里要加ptr1==null的判断
        if(ptr1==null){//注意这里判断的是ptr1，因为它会先撞线
            return null;
        }
        ptr1 = head;
        while(ptr0!=ptr1){
            ptr0 = ptr0.next;
            ptr1 = ptr1.next;
        }
        return ptr0;
    }
}
