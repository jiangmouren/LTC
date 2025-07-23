"""
TAG-LinkedList
https://leetcode.com/problems/palindrome-linked-list/description/

Given a singly linked list, determine if it is a palindrome.

Example 1:
Input: 1->2
Output: false

Example 2:
Input: 1->2->2->1
Output: true

Follow up:
Could you do it in O(n) time and O(1) space?
"""
from typing import Optional

class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next


class Solution:
    """
    if you want to do it in O(1) space, do chasing pointers, reverse the second half then compare
    """
    def is_palindrome(self, head: Optional[ListNode]) -> bool:
        ptr1, ptr2 = head, head
        while ptr2 is not None and ptr2.next is not None:
            ptr1, ptr2 = ptr1.next, ptr2.next.next if ptr2.next.next is not None else ptr2.next
        # if length is odd, ptr1 is in the middle, otherwise ptr1 is the first one of the seconde half
        pre, cur, nxt = None, ptr1, ptr1.next
        while cur is not None:
            cur.next = pre
            pre, cur, nxt = cur, nxt, nxt.next if nxt is not None else None
        left, right = head, ptr2
        while right is not None:
            if right.val!=left.val:
                return False
            right, left = right.next, left.next
        return True
    
if __name__ =="__main__":
    solution = Solution()
    node0 = ListNode(0)
    node1 = ListNode(1)
    node0.next = node1
    node2 = ListNode(2)
    node1.next = node2
    node3 = ListNode(3)
    node2.next = node3
    node4 = ListNode(2)
    node3.next = node4
    node5 = ListNode(1)
    node4.next = node5
    node6 = ListNode(0)
    node5.next = node6
    print(solution.is_palindrome(node0))

