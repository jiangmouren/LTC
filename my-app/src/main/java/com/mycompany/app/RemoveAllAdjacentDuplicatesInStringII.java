package com.mycompany.app;

/**
 * https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/
 * Given a string s, a k duplicate removal consists of choosing k adjacent and equal letters from s
 * and removing them causing the left and the right side of the deleted substring to concatenate together.
 * We repeatedly make k duplicate removals on s until we no longer can.
 * Return the final string after all such duplicate removals have been made.
 * It is guaranteed that the answer is unique.
 *
 * Example 1:
 * Input: s = "abcd", k = 2
 * Output: "abcd"
 * Explanation: There's nothing to delete.
 *
 * Example 2:
 * Input: s = "deeedbbcccbdaa", k = 3
 * Output: "aa"
 * Explanation:
 * First delete "eee" and "ccc", get "ddbbbdaa"
 * Then delete "bbb", get "dddaa"
 * Finally delete "ddd", get "aa"
 *
 * Example 3:
 * Input: s = "pbbcggttciiippooaais", k = 2
 * Output: "ps"
 *
 * Constraints:
 * 1 <= s.length <= 10^5
 * 2 <= k <= 10^4
 * s only contains lower case English letters.
 */
//RemoveAllAdjacentDuplicatesInString 的简单扩展版
public class RemoveAllAdjacentDuplicatesInStringII {
    public String removeDuplicates(String s, int k) {
        StringBuilder buf = new StringBuilder();
        boolean found = false;
        int ptr0 = 0;
        int ptr1 = 1;
        while(ptr0<s.length()){
            if(ptr1<s.length() && s.charAt(ptr0)==s.charAt(ptr1)){
                while(ptr1<s.length() && ptr1<ptr0+k && s.charAt(ptr0)==s.charAt(ptr1)){
                    ptr1++;
                }
                if(ptr1-ptr0==k){
                    found = true;
                }
                else{
                    buf.append(s.substring(ptr0, ptr1));//注意这里不要忘记
                }
                ptr0 = ptr1;
                ptr1++;
            }
            else{
                buf.append(s.charAt(ptr0));
                ptr0++;
                ptr1++;
            }
        }
        //System.out.println(buf.toString());
        return found ? removeDuplicates(buf.toString(), k) : s;
    }
}
