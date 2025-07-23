"""
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/description/
Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

According to the definition of LCA on Wikipedia: 
“The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants 
(where we allow a node to be a descendant of itself).”

Example 1:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.

Example 2:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.

Example 3:
Input: root = [1,2], p = 1, q = 2
Output: 1

Constraints:
The number of nodes in the tree is in the range [2, 105].
-109 <= Node.val <= 109
All Node.val are unique.
p != q
p and q will exist in the tree.
"""

"""
1. botoom up, do a post order traversal, return a tuple of (find_p, find_q, node).
   find_p and find_q are boolean, node is the LCA. Only when both p and q are found, the node is set.
2. top down, do a pre order traversal, keep track of the path from the root to the current node.
   When encounter p, save path_p, when encounter q, save path_q.
   When compare both paths, will find the LCA.

Implement the bottom up approach.
"""
from typing import Optional, Tuple
class TreeNode:
    def __init__(self, val: int=0, left: Optional['TreeNode']=None, right: Optional['TreeNode']=None):
        self.val = val
        self.left = left
        self.right = right

class Solution:
    def _post_order_search(self, root: Optional[TreeNode], p: TreeNode, q: TreeNode) -> Tuple[bool, bool, Optional[TreeNode]]:
        if root is None:
            return (False, False, None)
        left_res = self._post_order_search(root.left, p, q)
        if left_res[0] and left_res[1]:
            return left_res
        right_res = self._post_order_search(root.right, p, q)
        if right_res[0] and right_res[1]:
            return right_res
        find_p = left_res[0] or right_res[0] or root is p
        find_q = left_res[1] or right_res[1] or root is q
        node = root if find_p and find_q else None
        return (find_p, find_q, node)

    def lowest_common_ancestor(self, root: TreeNode, p: TreeNode, q: TreeNode) -> TreeNode:
        res = self._post_order_search(root, p, q)[2]
        assert res is not None, "p and q should exist in the tree"
        return res

if __name__ == "__main__":
    sol = Solution()
    root = TreeNode(3)
    root.left = TreeNode(5)
    root.right = TreeNode(1)
    root.left.left = TreeNode(6)
    root.left.right = TreeNode(2)
    root.right.left = TreeNode(0)
    root.right.right = TreeNode(8)
    root.left.right.left = TreeNode(7)
    root.left.right.right = TreeNode(4)
    print(sol.lowest_common_ancestor(root, root.right.right, root.right.left).val)