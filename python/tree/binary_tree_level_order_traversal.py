"""
Question:
Given a binary tree, return the level order traversal of its nodes' values.
(ie, from left to right, level by level).
For example:
Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
    /  \
   15   7
return its level order traversal as:
[
  [3],
  [9,20],
  [15,7]
]
"""

"""
Analysis:
The only catch in this problem is how to split the level.
1. One way is to insert a null after the last node in every level in side the queue.
This way once we encounter a null, we know we have finished a level, we also know that the next level's nodes are fully
inside the queue. So we can add another null to the queue to indicate the end of the next level.
2. Another way is to insert (node, level) pair into the queue. 
When we insert the next level's node, we check if the next level's list exists. If not create one. 
"""
from typing import Optional, List
class TreeNode:
    def __init__(self, val: int=0, left: Optional['TreeNode']=None, right: Optional['TreeNode']=None):
        self.val = val
        self.left = left
        self.right = right

from collections import deque    
class Solution:
    def level_order(self, root: Optional[TreeNode]) -> List[List[int]]:
        res = []
        queue = deque()
        if root:
            queue.append(None)
            queue.append(root)
            while queue:
                node = queue.popleft()
                # we have finished a level: create a new list; next level's nodes are fully enqueued, so append a None
                if node is None: 
                    # if queue is empty, we have finished the last level
                    if queue:
                        res.append([])
                        queue.append(None)
                else:
                    res[-1].append(node.val)
                    if node.left:
                        queue.append(node.left)
                    if node.right:
                        queue.append(node.right)
        return res
    
if __name__ == "__main__":
    sol = Solution()
    root = TreeNode(0)
    root.left = TreeNode(1)
    root.right = TreeNode(2)
    root.left.left = TreeNode(3)
    root.left.right = TreeNode(4)
    for list in sol.level_order(root):
        print(list)

