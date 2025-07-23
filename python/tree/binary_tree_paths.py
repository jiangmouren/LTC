"""
https://leetcode.com/problems/binary-tree-paths/
Given a binary tree, return all root-to-leaf paths.
For example, given the following binary tree:

   1
 /   \
2     3
 \
  5
All root-to-leaf paths are:

["1->2->5", "1->3"]
"""
from typing import Optional, List
class TreeNode:
    def __init__(self, val: int=0, left: Optional['TreeNode']=None, right: Optional['TreeNode']=None):
        self.val = val
        self.left = left
        self.right = right

class Solution:
    # key is not to rely on the current node as None to determine the end of the path.
    def _pre_order(self, root: Optional[TreeNode], path: List[int], res: List[List[int]]) -> None:
        if root:
            path.append(root.val)
            if root.left is None and root.right is None:
                res.append(path[:])# deep copy
            else:
                self._pre_order(root.left, path, res)
                self._pre_order(root.right, path, res)
            path.pop()

    def binary_tree_paths(self, root: Optional[TreeNode]) -> List[List[int]]:
        res = []
        path = []
        self._pre_order(root, path, res)
        return res

if __name__ == "__main__":
    sol = Solution()
    root = TreeNode(1)
    root.left = TreeNode(2)
    root.right = TreeNode(3)
    root.left.right = TreeNode(5)
    print(sol.binary_tree_paths(root))