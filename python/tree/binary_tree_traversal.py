"""
Question:
Given a binary tree, do all 3 types of traversal.
For example:
Given binary tree {1,#,2,3},
   1
    \
     2
    /
   3
return [1,2,3].
Note: Recursive solution is trivial, could you do it iteratively?
"""

"""
Analysis:
To do it iteratively, we need a stack.
The real problem is the flow control.
It is interesting to compare how we use stack in the in-order case vs this pre-order case.
Will put together 3 types of tree traversal together and do some comparison and analysis.
In the end, I only need to memorize the conclusion here. But it's good for the unerstanding to explain here.
"""
from typing import List, Optional


class TreeNode:
    def __init__(self, val: int = 0, left: Optional['TreeNode'] = None, right: Optional['TreeNode'] = None):
        self.val = val
        self.left = left
        self.right = right

class SolutionRecursive:
    def pre_order(self, root: TreeNode) -> List[int]:
        res = []
        if root:
            res.append(root.val)
        if root.left:
            res.extend(self.pre_order(root.left))
        if root.right:
            res.extend(self.pre_order(root.right))
        return res
    
    def in_order(self, root: TreeNode) -> List[TreeNode]:
        res = []
        if root:
            if root.left:
                res.extend(self.in_order(root.left))
            res.append(root.val)
            if root.right:
                res.extend(self.in_order(root.right))
        return res
    
    def post_order(self, root: TreeNode) -> List[TreeNode]:
        res = []
        if root:
            if root.left:
                res.extend(self.post_order(root.left))
            if root.right:
                res.extend(self.post_order(root.right))
            res.append(root.val)
        return res

from collections import deque
class SolutionIterative:
    def pre_order(self, root: TreeNode) -> List[int]:
        stack = deque()
        res = []
        if root:
            stack.append(root)
            while stack:
                node = stack.pop()
                res.append(node.val)
                if node.right:
                    stack.append(node.right)
                if node.left:
                    stack.append(node.left)
        return res

    def _push_stack(self, node: TreeNode, stack: deque) -> None:
        ptr = node
        while ptr:
            stack.append(ptr)
            ptr = ptr.left
    
    def in_order(self, root: TreeNode) -> List[int]:
        stack = deque()
        res = []
        self._push_stack(root, stack)
        while stack:
            node = stack.pop()
            res.append(node.val)
            self._push_stack(node.right, stack)
        return res

    """
    PostOrder iterative solution is the most fun part of all the 3 traversals: 
    1. Without an extened TreeNode, I cannot do a intuitive stack approach.
    2. The typical way for post-order is to use a reversed a pre-order.
    For pre-order, we have root-left-right, what we want for post-order is left-right-root. 
    If we get root-right-left first with a pre-order-variant traversal, 
    then all we need is just reverse the result from the above, we will have left-right-root.
    """

    def post_order(self, root: TreeNode) -> List[int]:
        stack = deque()
        res = []
        if root:
            stack.append(root)
            while stack:
                node = stack.pop()
                res.append(node.val)
                if node.left:
                    stack.append(node.left)
                if node.right:
                    stack.append(node.right)
            res.reverse()
        return res
    
if __name__ == "__main__":
    root = TreeNode(1)
    root.left = TreeNode(2)
    root.right = TreeNode(3)
    root.left.left = TreeNode(4)
    root.left.right = TreeNode(5)
    root.right.left = TreeNode(6)
    root.right.right = TreeNode(7)
    sol_1 = SolutionRecursive()
    print(sol_1.pre_order(root))
    print(sol_1.in_order(root))
    print(sol_1.post_order(root))
    sol_2 = SolutionRecursive()
    print(sol_2.pre_order(root))
    print(sol_2.in_order(root))
    print(sol_2.post_order(root))