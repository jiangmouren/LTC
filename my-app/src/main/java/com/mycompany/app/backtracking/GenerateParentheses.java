/**
 * Question:
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 * For example, given n = 3, a solution set is:
 * [
 *   "((()))",
 *   "(()())",
 *   "(())()",
 *   "()(())",
 *   "()()()"
 * ]
 *
 */

/**
 * Analysis:
 * "Find a valid decision path" typical "BackTracking" problem.
 */
package com.mycompany.app.backtracking;
import java.util.*;
public class GenerateParentheses{
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        StringBuilder buf = new StringBuilder();
        backtrack(n, 0, 0, buf, res);
        return res;
    }

    private void backtrack(int n, int leftCnt, int rightCnt, StringBuilder buf, List<String> res){
        if(leftCnt==n && rightCnt==n){
            res.add(buf.toString());
            return;
        }

        if(leftCnt<n){
            buf.append('(');
            backtrack(n, leftCnt+1, rightCnt, buf, res);
            buf.deleteCharAt(buf.length()-1);
        }
        if(rightCnt<leftCnt){
            buf.append(')');
            backtrack(n, leftCnt, rightCnt+1, buf, res);
            buf.deleteCharAt(buf.length()-1);
        }
    }

}
