package com.mycompany.app;
/**
 * https://leetcode.com/problems/product-of-array-except-self/
 * Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].
 *
 * Example:
 * Input:  [1,2,3,4]
 * Output: [24,12,8,6]
 * Constraint: It's guaranteed that the product of the elements of any prefix or suffix of the array (including the whole array) fits in a 32 bit integer.
 *
 * Note: Please solve it without division and in O(n).
 *
 * Follow up:
 * Could you solve it with constant space complexity? (The output array does not count as extra space for the purpose of space complexity analysis.)
 */

public class ProductOfArrayExceptSelf{
    //这个题目的里面有两个重要提示：1.O(n); 2. Prefix & Suffix
    //要求解的就是每个element左侧全部的乘积，乘上右侧全部的乘积。
    //左侧就是一个prefix问题，右侧就是一个suffix问题
    public int[] productExceptSelf(int[] nums) {
        int[] prefix = new int[nums.length];
        int[] suffix = new int[nums.length];
        int[] result = new int[nums.length];
        int temp = 1;
        for(int i=0; i<nums.length; i++){
            prefix[i] = temp;
            temp *= nums[i];
        }
        temp = 1;
        for(int i=nums.length-1; i>=0; i--){
            suffix[i] = temp;
            temp *= nums[i];
        }
        for(int i=0; i<nums.length; i++){
            result[i] = prefix[i]*suffix[i];
        }
        return result;
    }

    //至于说NO extra space，其实就是要把上面用的3个array合并到一个，
    //reuse prefix array for result，然后suffix的结果就不存在array里面
    //而是选择keep一个running result，即算即用。
    public int[] productExceptSelfSln2(int[] nums){
        int[] buf = new int[nums.length];
        int temp = 1;
        //buf store prefix results
        for(int i=0; i<nums.length; i++){
            buf[i] = temp;
            temp *= nums[i];
        }
        temp = 1;
        for(int i=nums.length-1; i>=0; i--){
            buf[i] *= temp;
            temp *= nums[i];
        }
        return buf;
    }
}
