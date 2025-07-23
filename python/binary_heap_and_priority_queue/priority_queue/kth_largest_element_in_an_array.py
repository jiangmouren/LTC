"""
https://leetcode.com/problems/kth-largest-element-in-an-array/
Find the kth largest element in an unsorted array.
Note that it is the kth largest element in the sorted order, not the kth distinct element.
For example,
Given [3,2,1,5,6,4] and k = 2, return 5.
Note:
You may assume k is always valid, 1 ≤ k ≤ array's length.
"""
"""
Two typical solutions:
1. use a minHeap, looping through the arry and insert each element into the heap.
   If the heap size goes over k, remove the smallest. By the end, the heap will have the k largest elements.
   And the peak will be the kth largest.
   The time complexity is O(nlogk).

2. Use quickSelect. The typical quickSelect is used to find the kth smallest element.
   Here we just need to find the l - k + 1 th smallest element.
   The time complexity is O(n) in average case, and O(n^2) in the worst case.
"""
from typing import List
import heapq

class Solution:
    # solution 1: using a minHeap
    def kth_largest_element_sln1(self, nums: List[int], k: int) -> int:
        min_heap = []
        for num in nums:
            heapq.heappush(min_heap, num)
            if len(min_heap) > k:
                heapq.heappop(min_heap)
        return min_heap[0]

    def _partition(self, nums: List[int], start: int, end: int) -> int:
        ptr1, ptr2 = start+1, end
        while ptr1 <= ptr2:
            while ptr2 > start and nums[ptr2] > nums[start]: 
                ptr2 -= 1
            while ptr1 <= end and nums[ptr1] <= nums[start]:
                ptr1 += 1
            if ptr1<ptr2:
                nums[ptr1], nums[ptr2] = nums[ptr2], nums[ptr1]
        nums[start], nums[ptr2] = nums[ptr2], nums[start]
        return ptr2

    def _quick_select(self, nums: List[int], k: int) -> int:
        l, r = 0, len(nums) - 1 
        # this value will never be returned, we need it to satisfy the return type checker
        res = -1 
        while l<=r:
            pivot = self._partition(nums, l, r)
            if pivot == k - 1:
                res =  nums[pivot]
                break
            if pivot > k - 1:
                r = pivot - 1
            else:
                l = pivot + 1
        return res

    # solution 2: using quickSelect
    def kth_largest_element_sln2(self, nums: List[int], k: int) -> int:
        pos = len(nums) - k + 1
        return self._quick_select(nums, pos)

if __name__ == "__main__":
    sol = Solution()
    print(sol.kth_largest_element_sln1([3,2,1,5,6,4], 3))
    print(sol.kth_largest_element_sln2([3,2,1,5,6,4], 2))