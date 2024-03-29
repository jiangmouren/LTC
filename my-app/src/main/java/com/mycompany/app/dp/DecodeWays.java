package com.mycompany.app.dp;

/**
 * https://leetcode.com/problems/decode-ways/
 * A message containing letters from A-Z can be encoded into numbers using the following mapping:
 *
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 * To decode an encoded message, all the digits must be mapped back into letters using the reverse of the mapping above (there may be multiple ways). For example, "111" can have each of its "1"s be mapped into 'A's to make "AAA", or it could be mapped to "11" and "1" ('K' and 'A' respectively) to make "KA". Note that "06" cannot be mapped into 'F' since "6" is different from "06".
 * Given a non-empty string num containing only digits, return the number of ways to decode it.
 * The answer is guaranteed to fit in a 32-bit integer.
 *
 * Example 1:
 * Input: s = "12"
 * Output: 2
 * Explanation: "12" could be decoded as "AB" (1 2) or "L" (12).
 *
 * Example 2:
 * Input: s = "226"
 * Output: 3
 * Explanation: "226" could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
 *
 * Example 3:
 * Input: s = "0"
 * Output: 0
 * Explanation: There is no character that is mapped to a number starting with 0. The only valid mappings with 0 are 'J' -> "10" and 'T' -> "20".
 * Since there is no character, there are no valid ways to decode this since all digits need to be mapped.
 *
 * Example 4:
 * Input: s = "1"
 * Output: 1
 *
 * Constraints:
 * 1 <= s.length <= 100
 * s contains only digits and may contain leading zero(s).
 */

/**
 * 典型的dp问题，唯一有点让人误会的就是题中最后一句话说“may contain leading zero(s)”.
 * 有 leading zeros 应该算不vallid，但是它描述的好像要允许leading zeros.
 */

public class DecodeWays {
    public int numDecodings(String s) {
        //int test = Integer.parseInt("01");
        //System.out.println(test);
        int[] dp = new int[s.length()];
        dp[s.length()-1] = s.charAt(s.length()-1)=='0' ? 0: 1;

        for(int i=s.length()-2; i>=0; i--){
            if(s.charAt(i)=='0'){
                dp[i] = 0;
            }
            else{
                dp[i] = dp[i+1];
                String temp = s.substring(i, i+2);
                if(Integer.parseInt(temp)<27){
                    dp[i] += (i+2)<s.length() ? dp[i+2] : 1;
                }
            }

        }
        return dp[0];
    }

    //prefix也可以
    public int numDecodingsPrefix(String s) {
        //int test = Integer.parseInt("01");
        //System.out.println(test);
        int[] dp = new int[s.length()];

        dp[0] = s.charAt(0)=='0' ? 0 : 1;
        if(s.length()>1){
            if(s.charAt(1)!='0'){
                dp[1] += dp[0];
            }
            if(s.charAt(0)!='0' && Integer.parseInt(s.substring(0, 2))<27){
                dp[1] += 1;
            }
        }

        for(int i=2; i<s.length(); i++){
            if(s.charAt(i)!='0'){
                dp[i] += dp[i-1];
            }
            if(s.charAt(i-1)!='0' && Integer.parseInt(s.substring(i-1, i+1))<27){
                dp[i] += dp[i-2];
            }
        }
        return dp[s.length()-1];
    }
}
