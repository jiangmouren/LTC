package com.mycompany.app;

import java.util.*;

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
        Stack<Integer> stack = new Stack<>();
        for(int i=0; i<s.length(); i++){
            if(buf.length()==0 || buf.charAt(buf.length()-1)!=s.charAt(i)){
                buf.append(s.charAt(i));
                stack.push(1);
            }
            else if(stack.peek()<k-1){
                buf.append(s.charAt(i));
                int cnt = stack.pop();
                stack.push(cnt+1);
            }
            else{
                buf.setLength(buf.length()-k+1);
                stack.pop();
            }
        }
        return buf.toString();
    }

    //下面这个是用Stack解决的办法复杂度是O(nk),依然不能pass leetcode的test，会有timeLimitExceed错误
    //主要问题就是这个check function给每一步都增加了O(k)的一个系数，用一哥额外的stack记录cnt可以不用每次都数一次
    public String removeDuplicatesStack(String s, int k) {
        StringBuilder buf = new StringBuilder();
        for(int i=0; i<s.length(); i++){
            if(buf.length()<k-1 || !check(buf, s.charAt(i), k)){
                buf.append(s.charAt(i));
            }
            else{
                buf.setLength(buf.length()-k+1);
            }
        }
        return buf.toString();
    }

    private boolean check(StringBuilder buf, char c, int k){
        for(int i=buf.length()-1; i>buf.length()-k; i--){
            if(c!=buf.charAt(i)){
                return false;
            }
        }
        return true;
    }

    //下面这种用recursion的解法的复杂度是O((n^2)/k)，曾经可以，现在已经不能pass leetcode的test了，会timeLimitExceeded
    public String removeDuplicatesRecursion(String s, int k) {
        StringBuilder buf = new StringBuilder();
        boolean found = false;
        int ptr0 = 0;
        int ptr1 = 0;
        while(ptr0<s.length()){
            while(ptr1<s.length() && ptr1-ptr0<k && s.charAt(ptr0)==s.charAt(ptr1)){
                ptr1++;
            }
            if(ptr1-ptr0<k){
                buf.append(s.substring(ptr0, ptr1));
            }
            else{
                found = true;
            }
            ptr0 = ptr1;
        }
        //没有改动，说明不会有变化，所以就可以停止了
        return found ? removeDuplicates(buf.toString(), k) : s;
    }
}
