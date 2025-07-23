"""
Question: https://leetcode.com/problems/binary-search-tree-iterator/
Implement an iterator over a binary search tree (BST).
Your iterator will be initialized with the root node of a BST.
Calling next() will return the next smallest number in the BST.
Note: next() and hasNext() should run in average O(1) time and uses O(h) memory, where h is the height of the tree.
"""

"""
Analysis:
Essentially what we need is a "Iterative In-Order Traversal".
"""
from typing import Optional
from collections import deque
class TreeNode:
    def __init__(self, val: int=0, left: Optional['TreeNode']=None, right: Optional['TreeNode']=None):
        self.val = val
        self.left = left
        self.right = right

class BSTIterator:
    def __init__(self, root: Optional[TreeNode]):
        self.stack = deque()
        self._push_stack(root)
    
    def _push_stack(self, node: Optional[TreeNode]) -> None:
        ptr = node
        while ptr:
            self.stack.append(ptr)
            ptr = ptr.left
    
    def has_next(self) -> bool:
        return len(self.stack) > 0
    
    def next(self) -> int:
        if self.has_next():
            current = self.stack.pop()
            # Push all left children of the right subtree
            self._push_stack(current.right)
            return current.val
        else:
            raise StopIteration("No more elements")

if __name__ == "__main__":
    root = TreeNode(7)
    root.left = TreeNode(3)
    root.right = TreeNode(15)
    root.left.left = TreeNode(1)
    root.right.left = TreeNode(9)
    root.right.right = TreeNode(20)
    iterator = BSTIterator(root)
    while iterator.has_next():
        print(iterator.next())