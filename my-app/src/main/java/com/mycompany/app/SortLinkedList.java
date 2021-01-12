package com.mycompany.app;

//An implementation of Quick Sort for LinkedList.
public class SortLinkedList {

    //Node definition: because we use this as return type, so we either define it as a none-nested class or we need to make it static.
    //Because it would make no sense from the caller's point of view the returned type is an instance type.
    public static class LinkedNode{
        int data;
        LinkedNode next;
        public LinkedNode(int x){
            this.data = x;
        }
    }

    public LinkedNode sort(LinkedNode head){
        if(head == null || head.next == null){
            return head;
        }
        LinkedNode[] leftHead = new LinkedNode[1];
        partition(head, leftHead);
        //we cut the original LinkedList after the pivot point.
        //So we must guarantee that if there are duplicates to the value of the pivot point, it must went to the right of the pivot point.
        //Otherwise,we will get into infinite recursion:
        // For example, at some point, we are looking at: head(2)-->head(1)-->head(0)
        //It will then become head(0)-->head(1)-->head(2), then head(2)-->head(1)-->head(0), keep repeating...
        //By forcing duplicates to the right of where we cut, we guarantee the problem size is going down.
        LinkedNode rightHead = head.next;
        head.next = null;
        LinkedNode leftSorted = sort(leftHead[0]);
        LinkedNode rightSorted = sort(rightHead);
        //The way we break the LinkedList will guarantee head to be the tail of the right side list
        head.next = rightSorted;
        return leftSorted;
    }

    private void partition(LinkedNode head, LinkedNode[] leftHead){
        LinkedNode headPtr = head;
        LinkedNode tailPtr = head;
        int x = head.data;
        head = head.next;
        while(head != null){
            LinkedNode next = head.next;
            if(head.data<x){
                head.next = headPtr;
                headPtr = head;
            }
            else{//any duplicates should go to right side
                tailPtr.next = head;
                tailPtr = head;
            }
            head = next;
        }
        tailPtr.next = null;
        leftHead[0] = headPtr;
    }
}
