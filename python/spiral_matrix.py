"""
https://leetcode.com/problems/spiral-matrix/
Given an m x n matrix, return all elements of the matrix in spiral order.

Example 1:
Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,3,6,9,8,7,4,5]

Example 2:
Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]
 
Constraints:
m == matrix.length
n == matrix[i].length
1 <= m, n <= 10
-100 <= matrix[i][j] <= 100
"""

from typing import List

class SpiralMatrix:
    def getDir(self, cnt: int) -> List[int]:
        dirs = [[0, 1], [1, 0], [0, -1], [-1, 0]]
        idx = cnt % 4
        return dirs[idx]
    
    def spiralOrder(self, matrix: List[List[int]]) -> List[int]:
        res = []
        w = len(matrix[0])
        h = len(matrix)
        l = w
        pos = [0, 0]
        cnt = 0
        ptr = 0
        dir = self.getDir(cnt)
        while(ptr<l):
           res.append(matrix[pos[0]][pos[1]])
           if(ptr==l-1):
                cnt += 1
                dir = self.getDir(cnt)
                ptr = 0
                if(l==w):
                    h -= 1
                    l = h
                else:
                    w -= 1
                    l = w
           else:
                ptr += 1
           pos[0] += dir[0]
           pos[1] += dir[1]
        return res