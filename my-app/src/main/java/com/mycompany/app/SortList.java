package com.mycompany.app;
/**
 * https://leetcode.com/problems/sort-list/
 * Given the head of a linked list, return the list after sorting it in ascending order.
 * Follow up: Can you sort the linked list in O(n logn) time and O(1) memory (i.e. constant space)?
 *
 * Example 1:
 * Input: head = [4,2,1,3]
 * Output: [1,2,3,4]
 *
 * Example 2:
 * Input: head = [-1,5,3,4,0]
 * Output: [-1,0,3,4,5]
 *
 * Example 3:
 * Input: head = []
 * Output: []
 *
 * Constraints:
 * The number of nodes in the list is in the range [0, 5 * 104].
 * -105 <= Node.val <= 105
 */

/**
 * Analysis:
 * 应该也是比较容易想到用Merge Sort，只不过实现O(1)，思路上直观，但是实现上会比较麻烦。
 * 在时间比较紧张的情况下，优先还是选择recursive的解法，放弃O(1) space的要求。
 */

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class SortList {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 以下是比较好写的recursive解法
     */
    public ListNode sortListRecur(ListNode head) {
        if(head==null || head.next==null){
            return head;
        }
        ListNode mid = getMid(head);
        ListNode left = sortListRecur(head);
        ListNode right = sortListRecur(mid);
        return mergeRecur(left, right);
    }

    private ListNode getMid(ListNode head){
        //这里有个细节要注意：ptr0具体是不是在刚好的中点并没有特别重要，但是要注意在终止的时候ptr0一定不能在最后一个Node
        //否则相当于subproblem没有变小，会出现无限循环的情况，这里把ptr1从head.next开始，以及对ptr1边界条件的check就是为了防止只有两个Node的时候，ptr0一步干到最后一个
        ListNode ptr0 = head;
        ListNode ptr1 = head.next;
        while(ptr1!=null && ptr1.next!=null){
            ptr0 = ptr0.next;
            ptr1 = ptr1.next.next;
        }
        ListNode mid = ptr0.next;
        ptr0.next = null;
        return mid;
    }

    private ListNode mergeRecur(ListNode left, ListNode right){
        ListNode dummyHead = new ListNode();
        ListNode tail = dummyHead;
        while(left!=null && right!=null){
            if(left.val<=right.val){
                tail.next = left;
                left = left.next;
            }
            else{
                tail.next = right;
                right = right.next;
            }
            tail = tail.next;
        }
        tail.next = left!=null ? left : right;
        return dummyHead.next;
    }

    /**
     * 以下是iterative解法
     */
    ListNode tail = new ListNode();
    ListNode nextSubList = new ListNode();

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        int n = getCount(head);
        ListNode start = head;
        ListNode dummyHead = new ListNode();
        for (int size = 1; size < n; size = size * 2) {
            tail = dummyHead;
            while (start != null) {
                if (start.next == null) {
                    tail.next = start;
                    break;
                }
                ListNode mid = split(start, size);
                merge(start, mid);
                start = nextSubList;
            }
            start = dummyHead.next;
        }
        return dummyHead.next;
    }

    ListNode split(ListNode start, int size) {
        ListNode midPrev = start;
        //至于说end为什么从start.next开始而不是从start开始因为，我们需要midPrev包含的node数量是end包含的node数量的一半:
        //midPre node数量：1+x; end node数量：2+2x=2(1+x)
        //而如果关心的不是Node的数量，而是距离成两倍关系，那么都应该从start开始：1+x vs 1+2x，所对应的距离本别是x & 2x
        ListNode end = start.next;
        //use fast and slow approach to find middle and end of second linked list
        for (int index = 1; index < size && (midPrev.next != null || end.next != null); index++) {
            if (end.next != null) {
                end = (end.next.next != null) ? end.next.next : end.next;
            }
            if (midPrev.next != null) {
                midPrev = midPrev.next;
            }
        }
        //不能像下面这样end走到底，就停，而是要考虑如果最后一段比较短，那么end到底的时候，mid还没有到它该到的位置
        //for (int index = 1; index < size && end!=null; index++) {
        //    end = end.next!=null ? end.next.next : end.next;
        //    midPrev = midPrev.next;
        //}
        ListNode mid = midPrev.next;
        midPrev.next = null;
        nextSubList = end.next;
        end.next = null;
        // return the start of second linked list
        return mid;
    }

    void merge(ListNode list1, ListNode list2) {
        ListNode dummyHead = new ListNode();
        ListNode newTail = dummyHead;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                newTail.next = list1;
                list1 = list1.next;
                newTail = newTail.next;
            } else {
                newTail.next = list2;
                list2 = list2.next;
                newTail = newTail.next;
            }
        }
        newTail.next = (list1 != null) ? list1 : list2;
        // traverse till the end of merged list to get the newTail
        while (newTail.next != null) {
            newTail = newTail.next;
        }
        // link the old tail with the head of merged list
        tail.next = dummyHead.next;
        // update the old tail to the new tail of merged list
        tail = newTail;
    }

    int getCount(ListNode head) {
        int cnt = 0;
        ListNode ptr = head;
        while (ptr != null) {
            ptr = ptr.next;
            cnt++;
        }
        return cnt;
    }
}
