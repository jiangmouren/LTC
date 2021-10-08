package com.mycompany.app.logic;
/**
 * Question: https://leetcode.com/problems/intersection-of-two-linked-lists/
 * Write a program to find the node at which the intersection of two singly linked lists begins.
 * For example, the following two linked lists:
 *
 * A:          a1 → a2
 *                    \
 *                      c1 → c2 → c3
 *                    /
 * B:     b1 → b2 → b3
 * begin to intersect at node c1.
 * Notes:
 * If the two linked lists have no intersection at all, return null.
 * The linked lists must retain their original structure after the function returns.
 * You may assume there are no cycles anywhere in the entire linked structure.
 * Your code should preferably run in O(n) time and use only O(1) memory.
 */

/**
 * Analysis:
 * This is more like a brain teasing problem.
 * The trick is: if start from a1, then from c3 move to b1; if start from b1 then move from c3 to a1.
 * In this way, both are running the same loop, and they should meet at C1.
 * If no intersection, they will never meet. Avoid infinite loop.
 *
 * Another way is to loop through two lists first get their lengths and compare their tails(reference not content),
 * if they have the same tail then, they intersected. Then we advance the pointer in the longer list first, by the
 * length difference, then move both pointers, they will meet at the intersection.
 */
public class IntersectionOfTwoLinkedLists{
    public static class ListNode{
        int val;
        ListNode next;
        ListNode(int x){
            this.val = x;
        }
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        //不考虑O(1)的memory要求有很多种解法，比如把两个list补成一样长，然后同步开始走，找交叉
        //或者把一个list过一遍把每个node的reference都存到map里，然后走第二个list，找第一个在map里Hit的Node
        //要O(1)的memory，就让他们绕圈，然后相会在相交点
        ListNode ptrA = headA;
        ListNode ptrB = headB;
        int loop = 0;
        while(ptrA!=ptrB){
            if(ptrA==null){
                ptrA = headB;
                loop++;
            }
            else{
                ptrA = ptrA.next;
            }
            if(ptrB==null){
                ptrB = headA;
            }
            else{
                ptrB = ptrB.next;
            }
        }
        return loop<2 ? ptrA : null;
    }

}
