"""
https://leetcode.com/problems/k-closest-points-to-origin/
We have a list of points on the plane.  Find the K closest points to the origin (0, 0).
(Here, the distance between two points on a plane is the Euclidean distance.)
You may return the answer in any order.  The answer is guaranteed to be unique (except for the order that it is in.)

Example 1:
Input: points = [[1,3],[-2,2]], K = 1
Output: [[-2,2]]
Explanation:
The distance between (1, 3) and the origin is sqrt(10).
The distance between (-2, 2) and the origin is sqrt(8).
Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
We only want the closest K = 1 points from the origin, so the answer is just [[-2,2]].

Example 2:
Input: points = [[3,3],[5,-1],[-2,4]], K = 2
Output: [[3,3],[-2,4]]
(The answer [[-2,4],[3,3]] would also be accepted.)


Note:
1 <= K <= points.length <= 10000
-10000 < points[i][0] < 10000
-10000 < points[i][1] < 10000
"""

"""
Either use a minHeap or quickSelect can solve this problem.
Will just do a minHeap here.
"""
from typing import List
import heapq

class Solution:
    def _get_distance_square(self, point: List[int]) -> int:
        return point[0]**2 + point[1]**2

    def k_closest_points(self, points: List[List[int]], k: int) -> List[List[int]]:
        min_heap = []
        for point in points:
            heapq.heappush(min_heap, (self._get_distance_square(point), point))
            if len(min_heap) > k:
                heapq.heappop(min_heap)
        return [point for _, point in min_heap]

if __name__ == "__main__":
    sol = Solution()
    print(sol.k_closest_points([[1,3],[-2,2], [2,3]], 2))