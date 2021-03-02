package com.mycompany.app;

/**
 * https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/
 * Given a string S of lowercase letters, a duplicate removal consists of choosing two adjacent and equal letters, and removing them.
 * We repeatedly make duplicate removals on S until we no longer can.
 * Return the final string after all such duplicate removals have been made.  It is guaranteed the answer is unique.
 *
 * Example 1:
 * Input: "abbaca"
 * Output: "ca"
 * Explanation:
 * For example, in "abbaca" we could remove "bb" since the letters are adjacent and equal, and this is the only possible move.
 * The result of this move is that the string is "aaca", of which only "aa" is possible, so the final string is "ca".
 *
 * Note:
 * 1 <= S.length <= 20000
 * S consists only of English lowercase letters.
 */
public class RemoveAllAdjacentDuplicatesInString {
    public String removeDuplicates(String S) {
        StringBuilder buf = new StringBuilder();
        boolean found = false;
        int ptr0 = 0;
        int ptr1 = 1;
        while(ptr0<S.length()){
            //注意：1. 每次只处理2个，题目中明确说了（陷阱）2. 注意ptr1不要out of bound.
            if(ptr1<S.length() && S.charAt(ptr0)==S.charAt(ptr1)){
                found = true;
                ptr0 = ptr1+1;
                ptr1 = ptr0+1;
            }
            else{
                buf.append(S.charAt(ptr0));
                ptr0++;
                ptr1++;
            }
        }
        //System.out.println(buf.toString());
        return found ? removeDuplicates(buf.toString()) : S;
    }
}
