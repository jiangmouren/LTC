package Finished;
/**
 * Question:
 * Numbers can be regarded as product of its factors. For example,
 *
 * 8 = 2 x 2 x 2;
 *   = 2 x 4.
 * Write a function that takes an integer n and return all possible combinations of its factors.
 *
 * Note:
 * You may assume that n is always positive.
 * Factors should be greater than 1 and less than n.
 * Examples:
 * input: 1
 * output:
 * []
 * input: 37
 * output:
 * []
 * input: 12
 * output:
 * [
 *   [2, 6],
 *   [2, 2, 3],
 *   [3, 4]
 * ]
 * input: 32
 * output:
 * [
 *   [2, 16],
 *   [2, 2, 8],
 *   [2, 2, 2, 4],
 *   [2, 2, 2, 2, 2],
 *   [2, 4, 4],
 *  [4, 8]
 * ]
 */

/**
 * Analysis:
 * This is a more generic kind of Backtracking problem. 
 * In a sense that the point we get a valid path does not overlap where we backtrack. 
 * Another interesting thing is the forwarding rule, that we only move forward in a assending order.
 * In that way, we can avoid duplication.
 */
import java.util.*;

public class FactorCombinations {
    public List<List<Integer>> find(int n){
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> buf = new ArrayList<>();
        helper(n, result, buf);
        return result;
    }

    private void helper(int n, List<List<Integer>> result, List<Integer> buf){
        //backward case
        if(!buf.isEmpty() && n < Math.pow(buf.get(buf.size()-1), 2)){
            buf.add(n);
            addList(result, buf);
            buf.remove(buf.size()-1);
            return;
        }

        //forward case
        int start;
        if(buf.isEmpty()){
            start = 2;
        }
        else{
            buf.add(n);
            addList(result, buf);//get result at none backward places also
            buf.remove(buf.size()-1);
            start = buf.get(buf.size()-1);
        }
        for(int i=start; i<=Math.pow(n, 0.5); i++){
            if(n%i==0){
                buf.add(i);
                helper(n/i, result, buf);
                buf.remove(buf.size()-1);
            }
        }
    }

    private void addList(List<List<Integer>> result, List<Integer> buf){
        List<Integer> tmp = new ArrayList<>();
        tmp.addAll(buf);
        result.add(tmp);
    }

}
