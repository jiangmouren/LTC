package com.mycompany.app.linearStructure;

/**
 * Question:
 * You are given two non-empty linked lists representing two non-negative integers.
 * The most significant digit comes first and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * Follow up:
 * What if you cannot modify the input lists? In other words, reversing the lists is not allowed.
 * Example:
 * Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 8 -> 0 -> 7
 */

import java.util.*;

/**
 * Analysis:
 * 1. Reverse input lists
 * 2. Use stack to reverse
 * 3. Add 0 to line up and then use recursion.
 */
public class AddTwoNumbersII {
    public static class ListNode{
        int val;
        ListNode next;
        ListNode(int x){
            this.val = x;
        }
    }

    /**
     * Solution 1: Use stack to reverse
     * 最简单好写
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2){
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        enStack(l1, stack1);
        enStack(l2, stack2);
        int c = 0;
        ListNode ptr = null;
        while(!stack1.isEmpty() || !stack2.isEmpty()){
            if(!stack1.isEmpty()){
                c += stack1.pop();
            }
            if(!stack2.isEmpty()){
                c += stack2.pop();
            }
            ListNode cur;
            if(c>=10){
                cur = new ListNode(c-10);
                c = 1;
            }
            else{
                cur = new ListNode(c);
                c = 0;
            }
            cur.next = ptr;
            ptr = cur;
        }
        if(c==1){
            ListNode cur = new ListNode(1);
            cur.next = ptr;
            ptr = cur;
        }
        return ptr;
    }

    private void enStack(ListNode l1, Stack<Integer> stack1){
        for(ListNode ptr=l1; ptr!=null; ptr=ptr.next){
            stack1.push(ptr.val);
        }
    }

    /**
     * Solution 2: Add 0 and use recursion
     */
    public ListNode add2(ListNode l1, ListNode l2){
        int len1 = getLength(l1);
        int len2 = getLength(l2);
        int diff = Math.abs(len1-len2);
        if(len1<len2){
            return helper2(l1, l2, diff);
        }
        else{
            return helper2(l2, l1, diff);
        }
    }

    //Assume l1.length < l2.length
    private ListNode helper2(ListNode l1, ListNode l2, int diff){
        ListNode l1New = addZeros(l1, diff);
        Result res = helper(l1New, l2);
        if(res.carry==1){
            ListNode msb = new ListNode(1);
            msb.next = res.node;
            return msb;
        }
        return res.node;
    }

    private int getLength(ListNode l1){
        int count = 0;
        for(ListNode ptr=l1; ptr!=null; ptr=ptr.next){
            count++;
        }
        return count;
    }

    private ListNode addZeros(ListNode l1, int diff){
        ListNode dummyHead = new ListNode(0);
        ListNode ptr = dummyHead;
        for(int i=0; i<diff; i++){
            ptr.next = new ListNode(0);
            ptr = ptr.next;
        }
        ptr.next = l1;
        return dummyHead.next;
    }

    private Result helper(ListNode l1, ListNode l2){
        //base case
        if(l1==null){
            Result res = new Result(null, 0);
            return res;
        }

        //recursion
        Result preResult = helper(l1.next, l2.next);
        int tmp = preResult.carry;
        ListNode preNode = preResult.node;
        tmp += l1.val;
        tmp += l2.val;
        int carry = 0;
        if(tmp>9){
            tmp -= 10;
            carry = 1;
        }
        ListNode node = new ListNode(tmp);
        node.next = preNode;
        return new Result(node, carry);
    }

    private class Result{
        ListNode node;
        int carry;
        Result(ListNode node, int carry){
            this.node = node;
            this.carry = carry;
        }
    }

    /**
     * Solution3: Reverse the LinkedLists
     */
    public ListNode add3(ListNode l1, ListNode l2){
        ListNode l1R = reverseList(l1);
        ListNode l2R = reverseList(l2);
        return reverseList(addFwd(l1R, l2R));
    }

    private ListNode addFwd(ListNode l1, ListNode l2) {
        ListNode ptr1 = l1;
        ListNode ptr2 = l2;
        ListNode dummyHead = new ListNode(0);
        ListNode ptr = dummyHead;
        int c = 0;
        while(ptr1!=null || ptr2!=null){
            int tmp = c;
            if(ptr1!=null){
                tmp += ptr1.val;
                ptr1 = ptr1.next;
            }
            if(ptr2!=null){
                tmp += ptr2.val;
                ptr2 = ptr2.next;
            }
            //remember to clear carry
            c = 0;
            if(tmp>9){//maximum 18 for LSB and 19 for others
                tmp -= 10;
                c = 1;
            }
            ListNode node = new ListNode(tmp);
            ptr.next = node;
            ptr = ptr.next;
        }
        return dummyHead.next;
    }

    //Your need 3 pointers to reverse a LinkedList
    private ListNode reverseList(ListNode l1){
        ListNode ptr1 = null;
        ListNode ptr2 = l1;
        ListNode ptr3 = l1.next;

        while(ptr2!=null){
            ptr2.next = ptr1;
            ptr1 = ptr2;
            ptr2 = ptr3;
            if(ptr3!=null){
                ptr3 = ptr3.next;
            }
        }
        return ptr1;
    }
}
