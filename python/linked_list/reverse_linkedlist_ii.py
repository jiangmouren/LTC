"""
TAG-LinkedList
https://leetcode.com/problems/reverse-linked-list-ii/description/
Given the head of a singly linked list and two integers left and right where left <= right,
reverse the nodes of the list from position left to position right, and return the reversed list.

Example 1:
Input: head = [1,2,3,4,5], left = 2, right = 4
Output: [1,4,3,2,5]

Example 2:
Input: head = [5], left = 1, right = 1
Output: [5]

Constraints:
The number of nodes in the list is n.
1 <= n <= 500
-500 <= Node.val <= 500
1 <= left <= right <= n

Follow up: Could you do it in one pass?
"""
from typing import Optional

class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

class Solution:
    def reverseBetween(self, head: Optional[ListNode], left: int, right: int) -> Optional[ListNode]:
        if head is None or left==right:
            return head
        i = 1
        dummyHead = ListNode(0, head)
        ptr_0, ptr = dummyHead, head
        while i<right:
            if i == left - 1:
                ptr_0 = ptr
            i += 1
            ptr = ptr.next
        ptr_1 = ptr
        # save this one for later, because once reversed, the ptr_1.next will be different
        suffix_head = ptr_1.next
        tail = ptr_0.next
        ptr_0.next = self._reverse_list(tail, suffix_head)
        tail.next = suffix_head
        return dummyHead.next
    
    def _reverse_list(self, head, end):
        ptr0, ptr1, ptr2 = None, head, head.next
        while ptr1 is not end:
            ptr1.next = ptr0
            ptr0, ptr1, ptr2 = ptr1, ptr2, ptr2.next if ptr2 is not None else None
        return ptr0


def list_to_linkedlist(lst):
    if not lst:
        return None
    head = ListNode(lst[0])
    current = head
    for val in lst[1:]:
        current.next = ListNode(val)
        current = current.next
    return head

def linkedlist_to_list(head):
    result = []
    while head:
        result.append(head.val)
        head = head.next
    return result

if __name__ == "__main__":
    sol = Solution()
    # Test 1: Example 1
    head = list_to_linkedlist([1,2,3,4,5])
    new_head = sol.reverseBetween(head, 2, 4)
    print("Test 1:", linkedlist_to_list(new_head))  # [1,4,3,2,5]

    # Test 2: Example 2
    head = list_to_linkedlist([5])
    new_head = sol.reverseBetween(head, 1, 1)
    print("Test 2:", linkedlist_to_list(new_head))  # [5]

    # Test 3: Reverse entire list
    head = list_to_linkedlist([1,2,3,4,5])
    new_head = sol.reverseBetween(head, 1, 5)
    print("Test 3:", linkedlist_to_list(new_head))  # [5,4,3,2,1]

    # Test 4: left == right (no change)
    head = list_to_linkedlist([1,2,3])
    new_head = sol.reverseBetween(head, 2, 2)
    print("Test 4:", linkedlist_to_list(new_head))  # [1,2,3]

    # Test 5: Two elements, reverse both
    head = list_to_linkedlist([1,2])
    new_head = sol.reverseBetween(head, 1, 2)
    print("Test 5:", linkedlist_to_list(new_head))  # [2,1]

    # Test 6: Empty list
    head = list_to_linkedlist([])
    new_head = sol.reverseBetween(head, 1, 1)
    print("Test 6:", linkedlist_to_list(new_head))  # []



