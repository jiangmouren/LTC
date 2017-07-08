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
package Finished.graph.backtracking;
import java.util.*;
public class GenerateParentheses{
    public List<String> generateParentheses(int n){
        if(n<0) throw new IllegalArgumentException("input cannot be negative");

        List<String> result = new ArrayList<>();
        int left = 0, right = 0;
        StringBuilder buf = new StringBuilder();
        helper(result, buf, left, right, n);
        return result;
    }

    private void helper(List<String> result, StringBuilder buf, int left, int right, int n){
        //Termination Case
        if(left>=n && right >=n){
            result.add(buf.toString());
            return;
        }

        //Recursive Cases
        if(left<n){
            buf.append('(');
            helper(result, buf, left+1, right, n);
            buf.deleteCharAt(buf.length()-1);
        }
        if(right<left){
            buf.append(')');
            helper(result, buf, left, right+1, n);
            buf.deleteCharAt(buf.length()-1);
        }
    }

}
