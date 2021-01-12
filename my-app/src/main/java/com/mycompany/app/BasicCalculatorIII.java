package com.mycompany.app;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * https://leetcode.com/problems/basic-calculator-iii/
 * Implement a basic calculator to evaluate a simple expression string.
 * The expression string contains only non-negative integers, +, -, *, / operators , open ( and closing parentheses ) and empty spaces . The integer division should truncate toward zero.
 * You may assume that the given expression is always valid. All intermediate results will be in the range of [-2147483648, 2147483647].
 * Follow up: Could you solve the problem without using built-in library functions.
 *
 * Example 1:
 * Input: s = "1 + 1"
 * Output: 2
 *
 * Example 2:
 * Input: s = " 6-4 / 2 "
 * Output: 4
 *
 * Example 3:
 * Input: s = "2*(5+5*2)/3+(6/2+8)"
 * Output: 21
 *
 * Example 4:
 * Input: s = "(2+6* 3+5- (3*14/7+2)*5)+3"
 * Output: -12
 *
 * Example 5:
 * Input: s = "0"
 * Output: 0
 *
 * Constraints:
 * 1 <= s <= 104
 * s consists of digits, '+', '-', '*', '/', '(', ')' and ' '.
 * s is a valid expression.
 */
public class BasicCalculatorIII {

    /**
     *我自己想到的解法:
     *从top level根据“+/-”号，把整个expression拆分成一个段段的只有乘除的。
     *各段算出来，然后汇总到一起。在各段当中，如果有括号，就recurse。
     * Time: O(n^2), Space: O(n)
     * 空间复杂度比较容易理解，除了recursion之外还有里面用的positions是O(n)的list
     * 时间复杂度可以考虑如下例子：（（（（...）+1）+1）+1）
     * 处理每一个括号就要O(n),然后每个里面的括号的recursive call又要take O(n):
     * n + n-1 + n-2 + n-3 + ... = O(n^2)
     */
    public int calculate_solution1(String s) {
        return (int)solve(s, 0, s.length()-1);
    }

    private long solve(String s, int start, int end){
        List<Integer> positions = parse(s, start, end);
        int ptr = start;
        int sign = 1;
        long res = 0;
        for(int position : positions){
            //solve and accumulate each segment
            res += sign * solveMulDiv(s, ptr, position-1);
            //update for next segment
            ptr = position + 1;
            sign = s.charAt(position)=='-' ? -1 : 1;
        }
        //handle last segment
        res += sign*solveMulDiv(s, ptr, end);
        return (int)res;

    }

    //return list of top level + and - positions
    private List<Integer> parse(String s, int start, int end){
        List<Integer> positions = new ArrayList<Integer>();
        int leftCnt = 0;
        for(int i=start; i<=end; i++){
            if(s.charAt(i)=='('){
                leftCnt++;
            }
            else if(s.charAt(i)==')'){
                leftCnt--;
            }
            else if(leftCnt==0 && (s.charAt(i)=='+' || s.charAt(i)=='-')){
                positions.add(i);
            }
        }
        return positions;
    }

    //solver for top level mulDiv case
    //这个func一点更要做去括号的动作，否则就会死循环了
    private long solveMulDiv(String s, int start, int end){
        long res = 1l;
        boolean empty = true;
        boolean mul = true;
        for(int i=start; i<=end; i++){
            if(s.charAt(i)==' '){
                continue;
            }
            else if(s.charAt(i)=='('){
                empty = false;
                int leftCnt = 1;
                int k = i+1;
                while(k<=end){
                    if(s.charAt(k)=='('){
                        leftCnt++;
                    }
                    else if(s.charAt(k)==')'){
                        leftCnt--;
                        if(leftCnt==0){
                            break;
                        }
                    }
                    k++;
                }
                res = mul ? res*solve(s, i+1, k-1) : res/solve(s, i+1, k-1);
                i = k;//because i will increment in for loop, so need to back one step here
            }
            else if(Character.isDigit(s.charAt(i))){
                empty = false;
                int temp = s.charAt(i)-'0';
                while(i+1<=end && Character.isDigit(s.charAt(i+1))){
                    temp = temp*10 + s.charAt(++i)-'0';
                }
                res = mul ? res*temp : res/temp;
            }
            else if(s.charAt(i)=='*'){
                mul = true;
            }
            else{
                mul = false;
            }
        }
        return empty ? 0 : res;
    }

    /**
     *
     * Solution 2
     * 下面这种写法其实源自于下面这个Link:
     * https://leetcode.com/problems/basic-calculator-iii/discuss/113592/Development-of-a-generic-solution-for-the-series-of-the-calculator-problems
     * 思路上，跟我的上面的解法完全一致，也是逻辑上从top level根据“+/-”号，把整个expression拆分成一个段段的只有乘除的。
     * 各段算出来，然后汇总到一起。在各段当中，如果有括号，就recurse。
     * 只不过下面这个Implementation要比我上面的优雅的多！！！
     */
    public int calculate_solution2(String s) {
        long l1 = 0; //partial result for + & -
        int o1 = 1; // 1: +; -1: -
        long l2 = 1; //partial result for * & /
        int o2 = 1; // 1: *; -1: /

        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(c==' '){
                continue;
            }
            else if(Character.isDigit(c)){
                int temp = c - '0';
                while(i+1<s.length() && Character.isDigit(s.charAt(i+1))){
                    temp = temp*10 + s.charAt(++i)-'0';
                }
                l2 = (o2==1) ? l2*temp : l2/temp;
            }
            else if(c=='('){
                int leftCnt = 1;
                int k = i+1;
                while(k<s.length()){
                    if(s.charAt(k)=='('){
                        leftCnt++;
                    }
                    else if(s.charAt(k)==')'){
                        leftCnt--;
                    }
                    if(leftCnt==0){
                        break;
                    }
                    k++;
                }
                long temp = calculate_solution2(s.substring(i+1, k));
                l2 = (o2==1) ? l2*temp: l2/temp;
                i = k; //i will increment in the for loop, no need to manually increment
            }
            else if(c=='*'){
                o2 = 1;
            }
            else if(c=='/'){
                o2 = -1;
            }
            else if(c=='+'){
                l1 += o1*l2;
                o1 = 1;
                //reset segment
                l2 = 1;
                o2 = 1;
            }
            else{
                l1 += o1*l2;
                o1 = -1;
                //reset segment
                o2 = 1;
                l2 = 1;
            }
        }
        //handle the last segment
        return (int)(l1+o1*l2);
    }

    //Solution2 的Iterative版本
    public int calculate_iterative(String s) {
        int l1 = 0, o1 = 1;
        int l2 = 1, o2 = 1;

        Deque<Integer> stack = new ArrayDeque<>(); // stack to simulate recursion
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                int num = c - '0';

                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    num = num * 10 + (s.charAt(++i) - '0');
                }

                l2 = (o2 == 1 ? l2 * num : l2 / num);

            } else if (c == '(') {
                // First preserve current calculation status
                stack.offerFirst(l1); stack.offerFirst(o1);
                stack.offerFirst(l2); stack.offerFirst(o2);

                // Then reset it for next calculation
                l1 = 0; o1 = 1;
                l2 = 1; o2 = 1;

            } else if (c == ')') {
                // First preserve the result of current calculation
                int num = l1 + o1 * l2;

                // Then restore previous calculation status
                o2 = stack.poll(); l2 = stack.poll();
                o1 = stack.poll(); l1 = stack.poll();

                // Previous calculation status is now in effect
                l2 = (o2 == 1 ? l2 * num : l2 / num);

            } else if (c == '*' || c == '/') {
                o2 = (c == '*' ? 1 : -1);

            } else if (c == '+' || c == '-') {
                l1 = l1 + o1 * l2;
                o1 = (c == '+' ? 1 : -1);

                l2 = 1; o2 = 1;
            }
        }

        return (l1 + o1 * l2);
    }
}
