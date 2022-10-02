package com.mycompany.app.companies.jerry;
import java.util.*;
/**
 * https://www.1point3acres.com/bbs/thread-859737-1-1.html
 * 题目是给一串数字（0 - 9）每个数字之间可以加 + - 号或者不加，组成的表达式计算结果等于 给定的目标数，输出所有满足条件的表达式。
 * 例如： [1 2 3 4 5 6 7 8 9]目标 100
 * 可能的组合：
 * 1 + 23 - 4 + 56 + 7 + 8 + 9
 * -1 - 2 + 34 - 5 - 6 + 78 + 9
 * 这是leetcode 282的一个简化版：https://leetcode.com/problems/expression-add-operators/
 * https://zxi.mytechroad.com/blog/searching/leetcode-282-expression-add-operators/
 * 下面写的是原版的答案，包含'+', '-', '*'
 */
public class ExpressionAddOperators {
    public List<String> addOperators(String num, int target) {
        List<String> res = new ArrayList<>();
        StringBuilder buf = new StringBuilder();
        backTracking(res, num, target, 0, buf, 0, 0);
        return res;
    }

    private void backTracking(List<String> res, String num, int target, int pos, StringBuilder buf, long pre, long cur){
        //termination case
        if(pos>=num.length()){
            if(cur==target){
                res.add(buf.toString());
            }
        }

        long temp = 0;
        int ptr = pos;
        int l = buf.length();
        while(ptr<num.length()){
            //leading 0 case
            if(num.charAt(pos)=='0' && ptr>pos){
                break;
            }
            //operand too big case
            temp = 10*temp + (int)(num.charAt(ptr)-'0');
            if(temp>Integer.MAX_VALUE){
                break;
            }
            //first operand case
            if(pos==0){
                buf.append(temp);
                backTracking(res, num, target, ptr+1, buf, temp, temp);
                buf.setLength(l);
            }
            else{//normal case
                buf.append('+');
                buf.append(temp);
                backTracking(res, num, target, ptr+1, buf, temp, cur+temp);
                buf.setLength(l);

                buf.append('-');
                buf.append(temp);
                backTracking(res, num, target, ptr+1, buf, -temp, cur-temp);
                buf.setLength(l);

                buf.append('*');
                buf.append(temp);
                backTracking(res, num, target, ptr+1, buf, pre*temp, cur-pre+pre*temp);
                buf.setLength(l);
            }
            ptr++;
        }
    }
}
