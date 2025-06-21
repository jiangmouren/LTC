package com.mycompany.app.linearStructure;

/**
 * https://leetcode.com/problems/decode-string/
 * Given an encoded string, return its decoded string.
 * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times.
 * Note that k is guaranteed to be a positive integer.
 * You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.
 * Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k.
 * For example, there won't be input like 3a or 2[4].
 *
 * Example 1:
 * Input: s = "3[a]2[bc]"
 * Output: "aaabcbc"
 *
 * Example 2:
 * Input: s = "3[a2[c]]"
 * Output: "accaccacc"
 *
 * Example 3:
 * Input: s = "2[abc]3[cd]ef"
 * Output: "abcabccdcdcdef"
 *
 * Example 4:
 * Input: s = "abc3[cd]xyz"
 * Output: "abccdcdcdxyz"
 *
 * Constraints:
 * 1 <= s.length <= 30
 * s consists of lowercase English letters, digits, and square brackets '[]'.
 * s is guaranteed to be a valid input.
 * All the integers in s are in the range [1, 300].
 */

public class DecodeString{
    //典型的recusive function
    public String decodeString(String s) {
        return getString(s, 0, s.length()-1);
    }

    private String getString(String s, int start, int end){
        StringBuilder builder = new StringBuilder();
        for(int i=start; i<=end; i++){
            if(Character.isDigit(s.charAt(i))){
                int ptr = i+1;
                while(ptr<=end && Character.isDigit(s.charAt(ptr))){
                    ptr++;
                }
                int value = Integer.parseInt(s.substring(i, ptr));//不要忘记数字可能有多位数
                int ptr2 = ptr + 1;
                int cnt = 1;
                while(ptr2<=end){
                    if(s.charAt(ptr2)=='['){
                        cnt++;
                    }
                    if(s.charAt(ptr2)==']'){
                        cnt--;
                        if(cnt==0){
                            break;
                        }
                    }
                    ptr2++;
                }
                //ptr points '[', ptr2 points']'
                String subString = getString(s, ptr+1, ptr2-1);
                for(int j=0; j<value; j++){
                    builder.append(subString);
                }
                i = ptr2;//i will get incremented in outter for loop
            }
            else{
                builder.append(s.charAt(i));
            }
        }
        return builder.toString();
    }
}
