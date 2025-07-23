"""
https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/
Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
Definition for a binary tree node.
"""
"""
[1, 2, 3, 4]
     2
    / \
   1   3
        \
         4
This can be done in O(n) time and O(lgn) space
You can either do this recursively or iteratively with a stack (store left and right).
In either case, the space complexity is O(lgn)
Or you can do it with BFS, the time complexity remains the same, but the space complexity is O(2^lgn) (the number of leaf nodes)
"""
from typing import Optional, List
from collections import deque
class TreeNode: 
    def __init__(self, val: int=0, left: Optional['TreeNode']=None, right: Optional['TreeNode']=None):
        self.val = val
        self.left = left
        self.right = right

class Solution:
    def _construct_bst(self, nums: List[int], left: int, right: int) -> Optional[TreeNode]:
        if left>right:
            return None
        mid = (left+right)//2
        root = TreeNode(nums[mid])
        root.left = self._construct_bst(nums, left, mid-1)
        root.right = self._construct_bst(nums, mid+1, right)
        return root

    def sorted_array_to_bst(self, nums: List[int]) -> Optional[TreeNode]:
        return self._construct_bst(nums, 0, len(nums)-1)

def _print_tree(root: Optional[TreeNode]) -> None:
    res = []
    if root:
        queue = deque()
        queue.append(None)
        queue.append(root)
        while queue:
            node = queue.popleft()
            # we saw a None, means we are at the end of a level:
            # add a new list to res; we have also queued all the children of the current level, so add a None to the queue
            if node is None:
                if queue:
                    # if queue is empty, we have finished the last level
                    queue.append(None)
                    res.append([])
            else:
                res[-1].append(node.val)
                if node.left:
                    queue.append(node.left)
                if node.right: 
                    queue.append(node.right)
    print(res)


if __name__ == "__main__":
    sol = Solution()
    nums = []
    root = sol.sorted_array_to_bst(nums)
    print(root)
    nums = [1, 2, 3, 4]
    root = sol.sorted_array_to_bst(nums)
    _print_tree(root)