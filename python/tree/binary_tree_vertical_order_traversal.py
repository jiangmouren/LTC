"""
https://leetcode.com/problems/binary-tree-vertical-order-traversal/description/
Question:
Given a binary tree, return the vertical order traversal of its nodes' values.
(ie, from top to bottom, column by column).
If two nodes are in the same row and column, the order should be from left to right.

Examples:
Given binary tree [3,9,20,null,null,15,7],
   3
  /\
 /  \
 9  20
    /\
   /  \
  15   7
return its vertical order traversal as:
[
  [9],
  [3,15],
  [20],
  [7]
]
Given binary tree [3,9,8,4,0,1,7],
     3
    /\
   /  \
   9   8
  /\  /\
 /  \/  \
 4  01   7
return its vertical order traversal as:
[
  [4],
  [9],
  [3,0,1],
  [8],
  [7]
]
Given binary tree [3,9,8,4,0,1,7,null,null,null,2,5] (0's right child is 2 and 1's left child is 5),
     3
    /\
   /  \
   9   8
  /\  /\
 /  \/  \
 4  01   7
    /\
   /  \
   5   2
return its vertical order traversal as:
[
  [4],
  [9,5],
  [3,0,1],
  [8,2],
  [7]
]
"""

"""
Analysis:
If root is 0, then move one step to the left you -1 and one step to the right you do +1.
That way while you traverse the tree and you can then put the current node into a Map<Integer, List<Integer>> 

The next problem you need to  think about is how to guarantee the order within the same group.
Better we maintain the order while injecting into the list: top --> down && left --> right.
Basically we do a level-order traversal / BFS.

Attention:
BFS is the only right way to traverse this. A pre-order will not work: You cannot guarantee the top-down order.
            0
           / \
          1   2
           \  
            3
             \
              4
In the above example, node 2 and node 4 are in the same column, but if you do pre-order, you will encounter node 4 first.

In the end we don't need to sort the bucket, as long as we keep track of the MIN and MAX,
We can iterate through the bucket from MIN to MAX to get the result.
"""
from typing import Optional, List
from collections import deque
class TreeNode:
    def __init__(self, val: int=0, left: Optional['TreeNode']=None, right: Optional['TreeNode']=None):
        self.val = val
        self.left = left
        self.right = right

class Solution:
    min_col, max_col = 0, 0
    def vertical_order(self, root: Optional[TreeNode]) -> List[List[int]]:
        res = []
        if root:
            dict = {}
            queue = deque()
            queue.append((root, 0))
            while queue:
                node, col = queue.popleft()
                self.min_col = min(self.min_col, col)
                self.max_col = max(self.max_col, col)
                if col not in dict:
                    dict[col] = []
                dict[col].append(node.val)
                if node.left:
                    queue.append((node.left, col-1))
                if node.right:
                    queue.append((node.right, col+1))
            for col in range(self.min_col, self.max_col+1):
                res.append(dict[col])
        return res
    
if __name__ == "__main__":
    sol = Solution()
    root = TreeNode(3)
    root.left = TreeNode(9)
    root.right = TreeNode(8)
    root.left.left = TreeNode(4)
    root.left.right = TreeNode(0)
    root.right.left = TreeNode(1)
    root.right.right = TreeNode(7)
    root.left.right.right = TreeNode(2)
    root.right.left.left = TreeNode(5)
    print(sol.vertical_order(root))