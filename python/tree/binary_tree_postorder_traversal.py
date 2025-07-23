"""
https://leetcode.com/problems/binary-tree-postorder-traversal/description/
Given a binary tree, return the postorder traversal of its nodes' values.

For example:
Given binary tree {1,#,2,3},
   1
    \
     2
    /
   3
return [3,2,1].

Note: Recursive solution is trivial, could you do it iteratively?
"""

"""
Do a fun iterative solution.
Post order: left-right-root
1. Get root-right-left first with a pre-order variant.
2. Reverse the result above
"""
from typing import Optional, List
from collections import deque
class TreeNode:
   def __init__(self, val: int=0, left: Optional['TreeNode']=None, right: Optional['TreeNode']=None):
      self.val = val
      self.left = left
      self.right = right

class Solution:
   def post_order(self, root: Optional[TreeNode]) -> List[int]:
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
   sol = Solution()
   root = TreeNode(1)
   root.left = TreeNode(2)
   root.right = TreeNode(3)
   root.right.left = TreeNode(4)
   print(sol.post_order(root))


