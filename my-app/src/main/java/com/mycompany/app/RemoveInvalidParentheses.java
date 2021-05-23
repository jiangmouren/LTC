package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/remove-invalid-parentheses/
 * Given a string s that contains parentheses and letters,
 * remove the minimum number of invalid parentheses to make the input string valid.
 * Return all the possible results. You may return the answer in any order.
 *
 * Example 1:
 * Input: s = "()())()"
 * Output: ["(())()","()()()"]
 *
 * Example 2:
 * Input: s = "(a)())()"
 * Output: ["(a())()","(a)()()"]
 *
 * Example 3:
 * Input: s = ")("
 * Output: [""]
 *
 * Constraints:
 * 1 <= s.length <= 25
 * s consists of lowercase English letters and parentheses '(' and ')'.
 * There will be at most 20 parentheses in s.
 */

public class RemoveInvalidParentheses{
    public List<String> removeInvalidParentheses(String s){
        List<String> res = new ArrayList<>();
        remove(s, res, 0, 0, new char[]{'(', ')'});
        return res;
    }

    private void remove(String s, List<String> res, int start, int lastRm, char[] par){
        int cnt = 0;
        for(int i=start; i<s.length(); i++){
            if(s.charAt(i)==par[0]){
                cnt++;
            }
            else if(s.charAt(i)==par[1]){
                cnt--;
            }
            //Do nothing for alphabetic, check cnt after updated.
            if(cnt>=0){
                continue;
            }
            //traverse all possible removals if cnt<0
            //这里从lastRm开始，因为之前那个位置移除掉之后，原Index对应的就是下一个char了
            for(int j=lastRm; j<=i; j++){
                if(s.charAt(j)==par[1] && (j==lastRm || s.charAt(j-1)!=par[1])){
                    //同理，这里recurse的时候用i，而非i+1，因为移除一个之后，i就指向新的char了
                    remove(s.substring(0, j) + s.substring(j+1, s.length()), res, i, j, par);
                }
            }
            //这个return有点让人困惑，如果有remove，traverse上面所有的情况后，就要结束，
            //后面的代码是给不需要remove的情况写的termination logic
            return;
        }

        String reversed = new StringBuilder(s).reverse().toString();
        if(par[0]=='('){//finished left to right
            remove(reversed, res, 0, 0, new char[]{')', '('});
        }
        else{//finished right to left
            res.add(reversed);
        }
    }
}