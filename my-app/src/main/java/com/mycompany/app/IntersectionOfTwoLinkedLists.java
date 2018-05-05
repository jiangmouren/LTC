package com.mycompany.app;
/**
 * Question:
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
    public static class Node{
        int val;
        Node next;
        Node(int x){
            this.val = x;
        }
    }

    public Node find(Node root1, Node root2){
        Node ptr1 = root1;
        Node ptr2 = root2;
        int loop = 0;
        while(ptr1!=ptr2 && loop<2){
            if(ptr1.next==null){
                loop++;
                ptr1 = root2;
            }
            else ptr1 = ptr1.next;

            if(ptr2.next==null){
                ptr2 = root1;
            }
            else ptr2 = ptr2.next;
        }
        if(ptr1==ptr2) return ptr1;
        else return null;
    }
}
