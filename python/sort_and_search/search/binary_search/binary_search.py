"""
implement binary search
Given a sorted integer array and a target value, return the index of the target value if exits, otherwise return -1
"""

class Solution:
    def binary_search(self, nums, target):
        left, right = 0, len(nums) - 1
        while left<=right:
            mid = (left + right) // 2
            if nums[mid] == target: 
                return mid
            elif nums[mid] < target:
                left = mid + 1
            else: 
                right = mid - 1
        return -1

if __name__ == "__main__":
    sol = Solution()
    nums = [1,2,3,4,5,6,7,8,9,10]
    target = 1
    print(sol.binary_search(nums, target))
