package Finished;
/**
 * Question:
 * Given two 1d vectors, implement an iterator to return their elements alternately.
 * For example, given two 1d vectors:
 * v1 = [1, 2]
 * v2 = [3, 4, 5, 6]
 * By calling next repeatedly until hasNext returns false,
 * the order of elements returned by next should be: [1, 3, 2, 4, 5, 6].
 * Follow up: What if you are given k 1d vectors? How well can your code be extended to such cases?
 * Clarification for the follow up question - Update (2015-09-18):
 * The "Zigzag" order is not clearly defined and is ambiguous for k > 2 cases.
 * If "Zigzag" does not look right to you, replace "Zigzag" with "Cyclic".
 * For example, given the following input:
 * [1,2,3]
 * [4,5,6,7]
 * [8,9]
 * It should return [1,4,8,2,5,9,3,6,7].
 */
import java.util.*;
public class ZigZagIterator {
    List<int[]> list;
    Node dummyHead = new Node(null);
    Node listPtr = dummyHead;
    Node ptrPre;

    public ZigZagIterator(List<int[]> list){
        this.list = list;
        for(int i=0; i<list.size(); i++){
            Node tmp = new Node(new Lable(i, 0));
            this.listPtr.next = tmp;
            this.listPtr = this.listPtr.next;
        }
        this.ptrPre = this.listPtr;
        this.listPtr.next = dummyHead.next;
        this.listPtr = this.listPtr.next;
    }
    public boolean hasNext(){
        return this.listPtr != null;
    }

    public int next(){
        int[] current = this.list.get(this.listPtr.lable.listIdx);
        int res = current[this.listPtr.lable.ptr];
        this.listPtr.lable.ptr++;
        if(this.listPtr.lable.ptr>=list.get(this.listPtr.lable.listIdx).length){
            if(this.ptrPre==this.listPtr){
                ptrPre = null;
                listPtr = null;
            }
            else{
                this.ptrPre.next = this.listPtr.next;
                this.listPtr = this.listPtr.next;
            }

        }
        else{
            this.ptrPre = this.ptrPre.next;
            this.listPtr = this.listPtr.next;
        }

        return res;
    }

    private class Lable{
        int listIdx;
        int ptr;
        Lable(int idx, int ptr){
            this.listIdx = idx;
            this.ptr = ptr;
        }
    }

    private class Node{
        Lable lable;
        Node next;
        Node(Lable lable){
            this.lable = lable;
        }
    }

}
