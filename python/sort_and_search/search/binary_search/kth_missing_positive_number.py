"""
https://leetcode.com/problems/kth-missing-positive-number/
Given an array arr of positive integers sorted in a strictly increasing order, and an integer k.
Find the kth positive integer that is missing from this array.

Example 1:
Input: arr = [2,3,4,7,11], k = 5
Output: 9
Explanation: The missing positive integers are [1,5,6,8,9,10,12,13,...]. The 5th missing positive integer is 9.

Example 2:
Input: arr = [1,2,3,4], k = 2
Output: 6
Explanation: The missing positive integers are [5,6,7,...]. The 2nd missing positive integer is 6.

Constraints:
1 <= arr.length <= 1000
1 <= arr[i] <= 1000
1 <= k <= 1000
arr[i] < arr[j] for 1 <= i < j <= arr.length
"""

"""
要点：
    1. 对于给定的idx，其左侧missing的正数的的个数为：arr[idx] - (idx + 1)
    2. 基于上述关系，就可以进行binary search, 但不同于普通binary search的是要找的数字不在arr当中
    3. This binary search will ended with left = right + 1, left can be len(arr) while right can be -1
    4. By the end, to get the result:
        1. the kth missing is in-between arr[right] and arr[left]:
           arr[right] + (k - (arr[right] - (right + 1))) = k + left
           解释：
           arr[right] - (right+1)： 是arr[right]左侧missing的正数的个数
           k - 左侧missing个数 = k - (arr[right] - (right + 1)): 是还应该往arr[right]右侧找几个数
           result = arr[right] + 还应该往arr[right]右侧找几个数 = arr[right] + (k - (arr[right] - (right + 1)))
                  = arr[right] + k - (arr[right] - (right + 1)) = arr[right] + k - arr[right] + (right + 1)
                  = k + (right + 1) = k +left
        2. the kth missing在区间右侧：同上
        3. 在区间左侧： k，刚好也等于left + k,因为这种情况left==0
"""
from typing import List

class Solution:
    def find_kth_missing_number(self, arr: List[int], k: int) -> int:
        l, r = 0, len(arr)-1
        while l<=r:
            mid = (l+r)//2
            missing_count = arr[mid] - (mid + 1)
            if missing_count>=k:
                r = mid - 1
            else:
                l = mid + 1
        return l + k

if __name__ == "__main__":
    sol = Solution()
    print(sol.find_kth_missing_number([2,3,4,7,11], 5))
    print(sol.find_kth_missing_number([2,3,4,7,11], 1))
    print(sol.find_kth_missing_number([2,3,4,7,11], 10))
    print(sol.find_kth_missing_number([1,2,3,4], 2))