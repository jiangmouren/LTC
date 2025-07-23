"""
Implement RadixSort
"""

class Solution:
    def radix_sort(self, arr):
        d = self._get_digits_count(arr)
        for i in range(d):
            arr = self._counting_sort(arr, i)
        return arr
    
    def _get_digits_count(self, arr):
        max_val = max(arr)
        return len(str(max_val))
    
    def _get_digit(self,num, pos):
        return (num // (10 ** pos)) % 10
    
    def _counting_sort(self, arr, pos):
        count = [0] * 10
        buf = [0] * len(arr)
        for num in arr:
            digit = self._get_digit(num, pos)
            count[digit] += 1
        for i in range(1, len(count)):
            count[i] += count[i-1]
        for num in reversed(arr):
            digit = self._get_digit(num, pos)
            buf[count[digit] - 1] = num
            count[digit] -= 1
        return buf

if __name__ == "__main__":
    sol = Solution()
    arr = [170, 45, 75, 90, 802, 24, 2, 66]
    sorted_arr = sol.radix_sort(arr)
    print(sorted_arr)