package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/find-the-duplicate-number/
 * Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
 * There is only one repeated number in nums, return this repeated number.
 *
 * Example 1:
 * Input: nums = [1,3,4,2,2]
 * Output: 2
 *
 * Example 2:
 * Input: nums = [3,1,3,4,2]
 * Output: 3
 *
 * Example 3:
 * Input: nums = [1,1]
 * Output: 1
 *
 * Example 4:
 * Input: nums = [1,1,2]
 * Output: 1
 *
 * Constraints:
 * 2 <= n <= 3 * 104
 * nums.length == n + 1
 * 1 <= nums[i] <= n
 * All the integers in nums appear only once except for precisely one integer which appears two or more times.
 *
 * Follow up:
 * How can we prove that at least one duplicate number must exist in nums?
 * Can you solve the problem without modifying the array nums?
 * Can you solve the problem using only constant, O(1) extra space?
 * Can you solve the problem with runtime complexity less than O(n2)?
 */
public class FindTheDuplicateNumber{
    public int findDuplicate(int[] nums) {
        //原本用HashMap O(n)就可以解决，但是因为要O(1)Space，所以这种思路就只能放弃
        //那么要找duplicate另外一种最直接的思路就是sort，而且找duplicate的时候确实O(1)
        Arrays.sort(nums);
        int ptr0 = 0;
        int ptr1 = 1;
        int res = nums[0];
        while(ptr1<nums.length){
            if(nums[ptr0]!=nums[ptr1]){
                ptr0++;
                ptr1++;
            }
            else{
                res = nums[ptr0];
                break;
            }
        }
        return res;
    }

    /**
     * 下面这道题，可以看做是Linked List  Cycle II的一个变种
     * https://leetcode.com/problems/linked-list-cycle-ii/
     * 上述题目最早见到这种解法是在cracking the coding interview讲linkedList得部分
     * 但是这个题目的模式又不是上述LinkedList问题的简单对应，分析见下图：
     * src\main\resources\FindTheDuplicateNumber.jpg
     * 这种解法time complexity是O(n)
     */
    public int findDuplicateFloyd(int[] nums){
        int ptr0 = nums[0];
        int ptr1 = nums[0];
        //这里要用一个do-while，因为开始ptr0==ptr1
        do{
            ptr0 = nums[ptr0];
            ptr1 = nums[nums[ptr1]];
        }while(ptr0!=ptr1);//注意do-while后面要加分号

        ptr1 = nums[0];
        //这里要用一个while-do，因为起始位只就可能是cycle的起始位置
        while(ptr1!=ptr0){
            ptr0 = nums[ptr0];
            ptr1 = nums[ptr1];
        }
        return ptr0;
    }
}