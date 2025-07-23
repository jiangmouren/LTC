"""
TAG-LinkedList
TAG-dfs
TAG-recursion
https://leetcode.com/problems/reverse-linked-list/description/
# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, val=0, next=None):
#         self.val = val
#         self.next = next
class Solution:
    def reverseList(self, head: Optional[ListNode]) -> Optional[ListNode]:

Constraints:

The number of nodes in the list is the range [0, 5000].
-5000 <= Node.val <= 5000
 
Follow up: A linked list can be reversed either iteratively or recursively. Could you implement both?
"""

# I just need 3 pointers start from None, head and head.nxt
# Traverse along the way while reverting the links. You need 3 pointers because anything less than that, you won't be able to traverse after reversed.
# The moment ptr1==None, just return ptr0.
from typing import Optional


class ListNode:
    def __init__(self, val=0, nxt=None):
        self.val = val
        self.nxt = nxt

class Solution:
    def reverse_list(self, head: Optional[ListNode]) -> Optional[ListNode]:
        if not head:
            return head
        ptr0, ptr1, ptr2 = None, head, head.nxt
        while ptr1!=None:
            ptr1.nxt = ptr0
            ptr0, ptr1, ptr2 = ptr1, ptr2, None if ptr2==None else ptr2.nxt
        return ptr0

    # The recursion solution is like the simplified dfs
    def reverse_list_recursion(self, head):
        if head.nxt == None:
            return head
        sub_head = self.reverse_list_recursion(head.nxt)
        head.nxt.nxt = head
        # Done forget to break the link from the parent to the sublink, otherwise creating circles
        head.nxt = None
        return sub_head

if __name__ == "__main__":
    node0 = ListNode(0)
    node1 = ListNode(1)
    node2 = ListNode(2)
    node0.nxt, node1.nxt = node1, node2
    #node0.nxt = node1
    solution = Solution()
    reversed_head = solution.reverse_list_recursion(node0)
    ptr = reversed_head
    while ptr!=None:
        print("value: ", ptr.val, " ")
        ptr = ptr.nxt