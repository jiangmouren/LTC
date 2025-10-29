"""
https://leetcode.com/problems/maximum-product-of-three-numbers
Given an integer array nums, find three numbers whose product is maximum 
and return the maximum product.

Example 1:

Input: nums = [1,2,3]
Output: 6
Example 2:

Input: nums = [1,2,3,4]
Output: 24
Example 3:

Input: nums = [-1,-2,-3]
Output: -6

Constraints:
3 <= nums.length <= 104
-1000 <= nums[i] <= 1000
"""

"""
Analysis:
1. Brutal force: O(n^3), check each pair
2. You can find the max product is either the product of the largest 3 numbers
or the product of the smallest 2 numbers and the largest number. Following is why.
Case 1: 0, ... num_0, num_1...num_n --> all numbers are positive, so the product
of the largest 3 numbers.
Case 2: num_0, num_1...num_n...0 --> all numbers are negative, so the product
will always be negative, you want to use the largest 3 numbers so to have the
smallest absolute value.
Case 3: num_0, num_1...0...num_n --> the array has both positive and negative. 
In this case, you absolutely want to get a positive product with:
    either all 3 positive numbers: you use the 3 largest number
    or two negative numbers and one positive number: 
        you use the 2 smallest (negative) & the largest (positive)

So you are choosing between:
1. the product of the 3 largest numbers 
2. the product of the 2 smallest numbers and the largest number

So you just need to to run one loop and find the largest 3 and the smallest 2,
then you can calculate the largest product.
"""
import heapq
def max_product_of_three(nums):
    largest_3 = heapq.nlargest(3, nums)
    smallest_2 = heapq.nsmallest(2, nums)
    prod_1 = largest_3[0] * largest_3[1] * largest_3[2]
    prod_2 = largest_3[0] * smallest_2[0] * smallest_2[1]
    return max(prod_1, prod_2)

if __name__ == "__main__":
    nums = [1, 2, 3, 4]
    print(max_product_of_three(nums))
    nums = [-1, -2, -3, 4]
    print(max_product_of_three(nums))
    nums = [-1, -2, -3]
    print(max_product_of_three(nums))

    