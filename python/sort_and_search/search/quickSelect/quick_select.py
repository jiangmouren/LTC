"""
Implement QuickSelect Algorithm.
Given an array, find the k-th smallest element in the array.
Time complexity: O(n)
Analysis: my-app\src\main\resources\QuickSortQuickSelectComplexity.PNG
"""

class Solution:
    def quick_select(self, arr, k):
        if k < 1 or k > len(arr):
            return None
        left, right = 0, len(arr)-1
        while left<=right:
            pivot = self._partition(arr, left, right)
            if pivot == k-1:
                return arr[pivot]
            if pivot>k-1:
                right = pivot-1
            else:
                left = pivot+1

    def _partition(self, arr, start, end):
        ptr1, ptr2 = start+1, end
        while ptr1 <= ptr2:
            while ptr2 > start and arr[ptr2] > arr[start]:
                ptr2 -= 1
            while ptr1 <=end and arr[ptr1] <= arr[start]:
                ptr1 += 1
            if ptr1 < ptr2:
                arr[ptr1], arr[ptr2] = arr[ptr2], arr[ptr1]
        arr[start], arr[ptr2] = arr[ptr2], arr[start]
        return ptr2

if __name__ == "__main__":
    sol = Solution()
    arr = [3, 2, 1, 5, 6, 4]
    k = 7
    print(sol.quick_select(arr, k))