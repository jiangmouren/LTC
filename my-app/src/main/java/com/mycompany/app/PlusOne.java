package com.mycompany.app;
import java.util.*;

/**
 * Question: https://leetcode.com/problems/plus-one/
 * Given a non-empty array of decimal digits representing a non-negative integer, increment one to the integer.
 * The digits are stored such that the most significant digit is at the head of the list,
 * and each element in the array contains a single digit.
 * You may assume the integer does not contain any leading zero, except the number 0 itself.
 *
 * Example 1:
 * Input: digits = [1,2,3]
 * Output: [1,2,4]
 * Explanation: The array represents the integer 123.
 *
 * Example 2:
 * Input: digits = [4,3,2,1]
 * Output: [4,3,2,2]
 * Explanation: The array represents the integer 4321.
 *
 * Example 3:
 * Input: digits = [0]
 * Output: [1]
 *
 * Constraints:
 * 1 <= digits.length <= 100
 * 0 <= digits[i] <= 9
 */

public class PlusOne {
    public int[] plusOne(int[] digits) {
        List<Integer> res = new ArrayList<>();
        int c = 1;
        for(int i=digits.length-1; i>=0; i--){
            c += digits[i];
            if(c>9){
                res.add(0);
                c = 1;
            }
            else{
                res.add(c);
                c = 0;
            }
        }
        if(c==1){
            res.add(c);
        }

        int[] result = new int[res.size()];
        for(int i=0; i<res.size(); i++){
            result[i] = res.get(res.size()-1-i);
        }
        return result;
    }
}
