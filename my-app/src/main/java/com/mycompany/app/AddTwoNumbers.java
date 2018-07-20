package com.mycompany.app;
/**
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 */
public class AddTwoNumbers {
    public static class LinkNode{
        int num;
        LinkNode next;
        public LinkNode(int num){
            this.num = num;
        }
    }

    public LinkNode solution(LinkNode node1, LinkNode node2){
        LinkNode ptr1 = node1;
        LinkNode ptr2 = node2;
        LinkNode dummyHead = new LinkNode(0);
        LinkNode ptr = dummyHead;
        int carry = 0;
        while(ptr1!=null && ptr2!=null){
            int tmp = ptr1.num + ptr2.num + carry;
            carry = 0;
            if(tmp>9){
                carry = 1;
                tmp = tmp - 10;
            }
            ptr.next = new LinkNode(tmp);
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
            ptr = ptr.next;
        }
        if(ptr1==null){
            helper(ptr, ptr2, carry);
        }
        else{
            helper(ptr, ptr1, carry);
        }
        return dummyHead.next;
    }

    private void helper(LinkNode ptr, LinkNode ptr1, int carry){
        while(ptr1!=null){
            int tmp = ptr1.num + carry;
            carry = 0;
            if(tmp>9){
                carry = 1;
                tmp = tmp - 10;
            }
            ptr.next = new LinkNode(tmp);
            ptr1 = ptr1.next;
            ptr = ptr.next;
        }
        if(carry==1){
            ptr.next = new LinkNode(1);
        }
    }

}
