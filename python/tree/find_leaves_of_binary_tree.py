"""
https://leetcode.com/problems/find-leaves-of-binary-tree/
Given the root of a binary tree, collect a tree's nodes as if you were doing this:

Collect all the leaf nodes.
Remove all the leaf nodes.
Repeat until the tree is empty.

Example 1:
Input: root = [1,2,3,4,5]
Output: [[4,5,3],[2],[1]]
Explanation:
[[3,5,4],[2],[1]] and [[3,4,5],[2],[1]] are also considered correct answers since per each level it does not matter the order on which elements are returned.

Example 2:
Input: root = [1]
Output: [[1]]

Constraints:
The number of nodes in the tree is in the range [1, 100].
-100 <= Node.val <= 100
"""

"""
Analysis:
本质上是把相同height的node放在一起
具体应用上，可以把management chain里面IC和不同level的manager各自进行grouping
Do a post order traversal. You need to know height of each subtree to decide height of the current node.
"""
from typing import Optional, List
class TreeNode:
    def __init__(self, val: int=0, left: Optional['TreeNode']=None, right: Optional['TreeNode']=None):
        self.val = val
        self.left = left
        self.right = right
    
class Solution:
    def _post_order(self, root: Optional[TreeNode], dict: dict) -> int:
        # termination case
        if root is None:
            return -1
        left_height = self._post_order(root.left, dict)
        right_height = self._post_order(root.right, dict)
        height = max(left_height, right_height) + 1
        if height not in dict:
            dict[height] = []
        dict[height].append(root.val)
        return height

    def find_leaves(self, root: Optional[TreeNode]) -> List[List[int]]:
        dict = {}
        self._post_order(root, dict)
        return list(dict.values())

if __name__ == "__main__":
    sol = Solution()
    root = TreeNode(1)
    root.left = TreeNode(2)
    root.right = TreeNode(3)
    root.left.left = TreeNode(4)
    root.left.right = TreeNode(5)
    print(sol.find_leaves(root))