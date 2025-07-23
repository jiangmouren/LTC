"""
Question: https://leetcode.com/problems/binary-tree-inorder-traversal/
Given a binary tree, return the inorder traversal of its nodes' values.
For example:
Given binary tree [1,null,2,3],
   1
    \
     2
    /
   3
return [1,3,2].
Note: Recursive solution is trivial, could you do it iteratively?
"""
from typing import Optional, List
class TreeNode:
    def __init__(self, val: int=0, left: Optional['TreeNode']=None, right: Optional['TreeNode']=None):
        self.val = val
        self.left = left
        self.right = right
from collections import deque
class Solution:
    def _push_stack(self, node: Optional[TreeNode], stack: deque) -> None:
        ptr = node
        while ptr:
            stack.append(ptr)
            ptr = ptr.left

    def inorder_traversal(self, root: Optional[TreeNode]) -> List[int]:
        stack = deque()
        res = []
        self._push_stack(root, stack)
        while stack:
            node = stack.pop()
            res.append(node.val)
            self._push_stack(node.right, stack)
        return res

if __name__ == "__main__":
    sol = Solution()
    root = TreeNode(1)
    root.left = TreeNode(2)
    root.right = TreeNode(3)
    root.left.left = TreeNode(4)
    root.left.right = TreeNode(5)
    root.right.left = TreeNode(6)
    root.right.right = TreeNode(7)
    print(sol.inorder_traversal(root))
