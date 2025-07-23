"""
TAG-LinkedList
https://leetcode.com/problems/merge-two-sorted-lists/description/
Merge two sorted linked lists and return it as a sorted list.
The list should be made by splicing together the nodes of the first two lists.

Example 1:
Input: l1 = [1,2,4], l2 = [1,3,4]
Output: [1,1,2,3,4,4]

Example 2:
Input: l1 = [], l2 = []
Output: []

Example 3:
Input: l1 = [], l2 = [0]
Output: [0]

Constraints:
The number of nodes in both lists is in the range [0, 50].
-100 <= Node.val <= 100
Both l1 and l2 are sorted in non-decreasing order.
"""
from typing import Optional
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

class Solution:
    def merge_sorted_lists(self, l1: Optional[ListNode], l2: Optional[ListNode]) -> Optional[ListNode]:
        dummy_head = ListNode(0)
        ptr = dummy_head
        ptr1, ptr2 = l1, l2
        while ptr1 is not None or ptr2 is not None:
            if ptr1 is None:
                ptr.next = ptr2
                ptr2 = ptr2.next
            elif ptr2 is None:
                ptr.next = ptr1
                ptr1 = ptr1.next
            else:
                if ptr1.val <= ptr2.val:
                    ptr.next = ptr1
                    ptr1 = ptr1.next
                else:
                    ptr.next = ptr2
                    ptr2 = ptr2.next
            ptr = ptr.next
        return dummy_head.next

def list_to_linkedlist(list):
    if not list: 
        return None
    head = ListNode(list[0])
    ptr = head
    for val in list[1:]:
        ptr.next = ListNode(val)
        ptr = ptr.next
    return head

def linkedlist_to_list(head):
    result = []
    ptr = head
    while ptr:
        result.append(ptr.val)
        ptr = ptr.next
    return result

if __name__ == "__main__":
    sol = Solution()
    l1 = list_to_linkedlist([1, 2, 4])
    l2 = list_to_linkedlist([1, 3, 4])
    merged_list = sol.merge_sorted_lists(l1, l2)
    print(linkedlist_to_list(merged_list))
    l3 = list_to_linkedlist([])
    l4 = list_to_linkedlist([0])
    merged_list_2 = sol.merge_sorted_lists(l3, l4)
    print(linkedlist_to_list(merged_list_2))
    l5 = list_to_linkedlist([])
    l6 = list_to_linkedlist([])
    merged_list_3 = sol.merge_sorted_lists(l5, l6)
    print(linkedlist_to_list(merged_list_3))

