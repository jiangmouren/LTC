"""
https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
Given an array of integers nums sorted in ascending order, find the starting and ending position of a given target value.
If target is not found in the array, return [-1, -1].
Follow up: Could you write an algorithm with O(log n) runtime complexity?

Example 1:
Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]

Example 2:
Input: nums = [5,7,7,8,8,10], target = 6
Output: [-1,-1]

Example 3:
Input: nums = [], target = 0
Output: [-1,-1]

Constraints:
0 <= nums.length <= 10^5
-10^9 <= nums[i] <= 10^9
nums is a non-decreasing array.
-10^9 <= target <= 10^9
"""
#典型的BinarySearch找最左，或者最右的问题
from typing import List
class Solution:
    def _search(self, nums, target, start, end, find_left):
        l, r = start, end
        pos = -1
        while l<=r:
            mid = (l+r)//2
            if nums[mid]<target:
                l = mid + 1
            elif nums[mid]>target:
                r = mid - 1
            else:
                pos = mid 
                if find_left:
                    r = mid - 1
                else:
                    l = mid + 1
        return pos

    def search_range(self, nums: List[int], target: int) -> List[int]:
        left = self._search(nums, target, 0, len(nums)-1, True)
        right = self._search(nums, target, 0, len(nums)-1, False)
        return [left, right]

if __name__ == "__main__":
    sol = Solution()
    print(sol.search_range([5,7,7,8,8,10], 8))
    print(sol.search_range([5,7,7,8,8,10], 6))
    print(sol.search_range([5,7,7,8,8,10], 4))
    print(sol.search_range([5,7,7,8,8,10], 14))
    print(sol.search_range([], 0))

