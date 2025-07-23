"""
 Question: https://leetcode.com/problems/closest-binary-search-tree-value/
 Given the root of a binary search tree and a target value,
 return the value in the BST that is closest to the target.
 
 Example 1:
 Input: root = [4,2,5,1,3], target = 3.714286
 Output: 4
 
 Example 2:
 Input: root = [1], target = 4.428571
 Output: 1
 
 Constraints:
 The number of nodes in the tree is in the range [1, 104].
 0 <= Node.val <= 109
 -109 <= target <= 109
"""

"""
The key of BST search is moving towards the converence of the goal.
In this problem, root.val > target, move further to the right is on the divergence path.
So following the BST order is the still the right direction. 
But root.left isn't necessarily closer to the target than the root. 
We keep track of the closest node along the path.
"""
from typing import Optional
class TreeNode:
    def __init__(self, val: int=0, left: Optional['TreeNode']=None, right: Optional['TreeNode']=None):
        self.val = val
        self.left = left
        self.right = right

class Solution:
    @staticmethod
    def closest_value(root: TreeNode, target: float) -> int:
        ptr = root
        closest_val = root.val
        while ptr:
            closest_val = ptr.val if abs(ptr.val - target)<abs(closest_val - target) else closest_val
            if ptr.val == target:
                break
            elif ptr.val > target:
                ptr = ptr.left
            else: 
                ptr = ptr.right
        return closest_val

if __name__ == "__main__":
    root = TreeNode(4)
    root.left = TreeNode(2)
    root.right = TreeNode(5)
    root.left.left = TreeNode(1)
    root.left.right = TreeNode(3)
    print(Solution.closest_value(root, 3.714286))
    print(Solution.closest_value(root, 5.428571))