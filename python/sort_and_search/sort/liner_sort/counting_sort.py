"""
Implement CountingSort Algorithm.
Sorting an array of integers, with the range of values between 0 and max_val.
"""

class Solution:
    def counting_sort(self, arr, max_val):
        # Need to initialize the buf to the same length first, otherwise later when assigning values will get index out of range error
        buf = [0] * len(arr)
        count = [0] * (max_val + 1)
        for val in arr:
            count[val] += 1
        # count[i] is the number of elements in arr that equals to i
        for i in range(1, len(count)):
            count[i] = count[i-1] + count[i]
        # count[i] is the number of elements in arr that is less than or equal to i
        # Note: it's important to loop from the end of arr to the beginning, otherwise the sort will be unstable
        # "Stable" means that the order of equal elements is preserved.
        for num in reversed(arr):
            buf[count[num] - 1] = num
            count[num] -= 1
        return buf

if __name__ == "__main__":
    sol = Solution()
    arr = [0, 2, 8, 3, 2, 1, 10]
    sorted_arr = sol.counting_sort(arr, 10)
    print(sorted_arr)
