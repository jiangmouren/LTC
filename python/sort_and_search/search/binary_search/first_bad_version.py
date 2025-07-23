"""
https://leetcode.com/problems/first-bad-version/
You are a product manager and currently leading a team to develop a new product.
Unfortunately, the latest version of your product fails the quality check.
Since each version is developed based on the previous version,
all the versions after a bad version are also bad.

Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one,
which causes all the following ones to be bad.
You are given an API bool isBadVersion(version) which returns whether version is bad.
Implement a function to find the first bad version. You should minimize the number of calls to the API.

Example 1:
Input: n = 5, bad = 4
Output: 4
Explanation:
call isBadVersion(3) -> false
call isBadVersion(5) -> true
call isBadVersion(4) -> true
Then 4 is the first bad version.

Example 2:
Input: n = 1, bad = 1
Output: 1

Constraints:
1 <= bad <= n <= 231 - 1
"""

#考察的就是binary sarch的最终收敛状态：l = r + 1，并且l刚好指向first bad version
class Solution:
    def first_bad_version(self, n: int) -> int:
        l, r = 1, n
        while l<=r:
            mid = (l+r)//2
            if isBadVersion(mid):
                r = mid - 1
            else:
                l = mid + 1
        return l

BAD_VERSION = 4
def isBadVersion(version):
    return version >= BAD_VERSION

if __name__ == "__main__":
    sol = Solution()
    print(sol.first_bad_version(5))