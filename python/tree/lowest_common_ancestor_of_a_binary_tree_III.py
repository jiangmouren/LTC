"""
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii/
Given two nodes of a binary tree p and q, return their lowest common ancestor (LCA).
Each node will have a reference to its parent node. The definition for Node is below:

class Node {
    public int val;
    public Node left;
    public Node right;
    public Node parent;
}
According to the definition of LCA on Wikipedia: "The lowest common ancestor of two nodes p and q in a tree T is the lowest node that has both p and q as descendants (where we allow a node to be a descendant of itself)."

Example 1:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.

Example 2:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5 since a node can be a descendant of itself according to the LCA definition.

Example 3:
Input: root = [1,2], p = 1, q = 2
Output: 1

Constraints:
The number of nodes in the tree is in the range [2, 105].
-109 <= Node.val <= 109
All Node.val are unique.
p != q
p and q exist in the tree.
"""

"""
Option 1: get the two paths from p and q to the root in list; traverse both lists from the root; the first time diverge, the node before that is the LCA.
Option 2: get the path from q to the root in a set; traverse the path from p to the root; the first node in the set is the LCA.
Both of the the above options are O(lgN) in time complexity.

If you do post order traversal, we need O(N) time complexity.
"""
from typing import Optional
class TreeNode:
    def __init__(self, val: int=0, left: Optional['TreeNode']=None, right: Optional['TreeNode']=None, parent: Optional['TreeNode']=None):
        self.val = val
        self.left = left
        self.right = right
        self.parent = parent
        
class Solution:
    def lowest_common_ancestor(self, p: TreeNode, q: TreeNode) -> TreeNode:
        path_p = set()
        res = q
        ptr = p
        while ptr:
            path_p.add(ptr)
            ptr = ptr.parent
        ptr = q
        while ptr:
            if ptr in path_p:
                res = ptr
                break
            ptr = ptr.parent
        return res

if __name__ == "__main__":
    sol = Solution()
    root = TreeNode(3)
    root.left = TreeNode(5)
    root.left.parent = root
    root.right = TreeNode(1)
    root.right.parent = root
    root.left.left = TreeNode(6)
    root.left.left.parent = root.left
    root.left.right = TreeNode(2)
    root.left.right.parent = root.left
    root.right.left = TreeNode(0)
    root.right.left.parent = root.right
    root.right.right = TreeNode(8)
    root.right.right.parent = root.right
    root.left.right.left = TreeNode(7)
    root.left.right.left.parent = root.left.right
    root.left.right.right = TreeNode(4)
    root.left.right.right.parent = root.left.right
    print(sol.lowest_common_ancestor(root.left.left, root.left.right.right).val)