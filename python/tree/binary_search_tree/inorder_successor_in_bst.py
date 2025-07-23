"""
Question: https://leetcode.com/problems/inorder-successor-in-bst/
Given a binary search tree and a node in it, find the in-order successor of that node in the BST.
The successor of a node p is the node with the smallest key greater than p.val.

Example 1:
Input: root = [2,1,3], p = 1
Output: 2
Explanation: 1's in-order successor node is 2. Note that both p and the return value is of TreeNode type.

Example 2:
Input: root = [5,3,6,2,4,null,null,1], p = 6
Output: null
Explanation: There is no in-order successor of the current node, so the answer is null.

Note:
If the given node has no in-order successor in the tree, return null.
It's guaranteed that the values of the tree are unique.
"""
from typing import Optional, List

class TreeNode:
    def __init__(self, val: int=0, left: Optional['TreeNode']=None, right: Optional['TreeNode']=None):
        self.val = val
        self.left = left
        self.right = right
    
class Solution:
    @staticmethod
    def _find_min(node: TreeNode) -> TreeNode:
        ptr = node
        while ptr.left:
            ptr = ptr.left
        return ptr

    @staticmethod
    def _find_lowest_right_ancestor(root: Optional[TreeNode], node: TreeNode, lrc: List[Optional[TreeNode]]) -> None:
        ptr = root
        while ptr and ptr is not node:
            if ptr.val > node.val:
                lrc[0] = ptr
                ptr = ptr.left
            else:
                ptr = ptr.right
        
    @staticmethod
    def in_order_successor(root: Optional[TreeNode], node: TreeNode) -> Optional[TreeNode]:
        if node.right:
            return Solution._find_min(node.right)
        else:
            lrc: List[Optional[TreeNode]] = [None]
            Solution._find_lowest_right_ancestor(root, node, lrc)
            return lrc[0]
    
if __name__ == "__main__":
    root = TreeNode(5)
    root.left = TreeNode(3)
    root.left.left = TreeNode(2)
    root.left.right = TreeNode(4)
    suc = Solution.in_order_successor(root, root.left.left)
    assert suc is not None
    print(suc.val)
    suc = Solution.in_order_successor(root, root)
    print(suc)
