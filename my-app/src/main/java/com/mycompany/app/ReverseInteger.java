package com.mycompany.app;

/**
 * https://leetcode.com/problems/reverse-integer/
 * Given a signed 32-bit integer x, return x with its digits reversed. If reversing x causes the value to go outside the signed 32-bit integer range [-231, 231 - 1], then return 0.
 * Assume the environment does not allow you to store 64-bit integers (signed or unsigned).
 *
 * Example 1:
 * Input: x = 123
 * Output: 321
 *
 * Example 2:
 * Input: x = -123
 * Output: -321
 *
 * Example 3:
 * Input: x = 120
 * Output: 21
 *
 * Example 4:
 * Input: x = 0
 * Output: 0
 *
 * Constraints:
 * -231 <= x <= 231 - 1
 */

public class ReverseInteger{
    public int reverse(int x) {
        String max = Integer.toString(Integer.MAX_VALUE);
        String min = Integer.toString(Integer.MIN_VALUE);
        min = min.substring(1, min.length());
        if(x==0){
            return 0;
        }
        else if(x>0){
            return process(x, max);
        }
        else{
            return process(x, min);
        }
    }

    private int process(int x, String target){
        StringBuilder xBuf = new StringBuilder();
        xBuf.append(Math.abs(x));
        String res = xBuf.reverse().toString();
        if(res.length()<target.length()){
            int val = Integer.parseInt(res);//This parseInt() method will handle leading 0s
            if(x>0){
                return val;
            }
            else{
                return -val;
            }
        }
        else{//for the case when res.length()==target.length()
            int cmp = res.compareTo(target);
            if(cmp>0){
                return 0;
            }
            else{
                int val = Integer.parseInt(res);
                if(x>0){
                    return val;
                }
                else{
                    return -val;
                }
            }
        }
    }
}
