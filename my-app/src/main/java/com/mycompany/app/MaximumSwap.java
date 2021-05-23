package com.mycompany.app;

/**
 * https://leetcode.com/problems/maximum-swap/
 * You are given an integer num. You can swap two digits at most once to get the maximum valued number.
 * Return the maximum valued number you can get.
 *
 * Example 1:
 * Input: num = 2736
 * Output: 7236
 * Explanation: Swap the number 2 and the number 7.
 *
 * Example 2:
 * Input: num = 9973
 * Output: 9973
 * Explanation: No swap.
 *
 * Constraints:
 * 0 <= num <= 10^8
 */

public class MaximumSwap {
    //基本思路就是我需要知道每个位置右侧的最大的数字，以及对应的位置，先用running max生成这个
    //然后从高位往低位，走看右侧有没有比自己大的数字，如果有，就置换。
    public int maximumSwap(int num) {
        StringBuilder buf = new StringBuilder();
        buf.append(num);
        int n = buf.length();
        int[][] maxArr = new int[n][2];
        int temp = 0;
        int ptr = n;
        for(int i=n-1; i>=0; i--){
            maxArr[i][0] = temp;
            maxArr[i][1] = ptr;
            if(buf.charAt(i)-'0'>temp){
                temp = buf.charAt(i) - '0';
                ptr = i;
            }
        }
        for(int i=0; i<n; i++){
            if(buf.charAt(i)-'0'<maxArr[i][0]){
                swap(buf, i, maxArr[i][1]);
                break;
            }
        }

        return Integer.parseInt(buf.toString());
    }

    private void swap(StringBuilder buf, int left, int right){
        char temp = buf.charAt(left);
        buf.setCharAt(left, buf.charAt(right));
        buf.setCharAt(right, temp);
    }
}
