package Finished;
/**
 * Question:
 * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
 * Find all strobogrammatic numbers that are of length = n.
 * For example,
 * Given n = 2, return ["11","69","88","96"].
 */

import java.util.*;

/**
 * Analysis:
 * Among 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, the candidates are: 0, 1, 6, 8, 9.
 * So it's a backtracking problem.
 * Because it has to be symmetric, so we only need to traverse half of the path.
 * It's all about details:
 * even, odd
 * 6 and 9
 * cannot add 0 for position 0, if n>1
 * can only put 0, 1, 8 in the middle
 */

public class StrobogrammaticNumberII{
    public List<List<Integer>> find(int n){
        if(n<1) return null;

        List<List<Integer>> res = new ArrayList<>();
        List<Integer> buf = new ArrayList<>();
        helper(res, buf, n);
        return res;
    }

    private void helper(List<List<Integer>> res, List<Integer> buf, int n){
        boolean even = (n%2==0);
        //backtracking case
        if(even && buf.size()>=n/2 || !even && buf.size()>=n/2+1){
            List<Integer> tmp = append(buf, even);
            res.add(tmp);
            return;
        }

        //forward case
        if(n==1 || buf.size()>0){
            buf.add(0);
            helper(res, buf, n);
            buf.remove(buf.size()-1);
        }

        buf.add(1);
        helper(res, buf, n);
        buf.remove(buf.size()-1);

        buf.add(8);
        helper(res, buf, n);
        buf.remove(buf.size()-1);

        if(buf.size()!=n/2){
            buf.add(6);
            helper(res, buf, n);
            buf.remove(buf.size()-1);
            buf.add(9);
            helper(res, buf, n);
            buf.remove(buf.size()-1);
        }
    }

    private List<Integer> append(List<Integer> input, boolean even){
        List<Integer> res = new ArrayList<>();
        res.addAll(input);
        int start = even ? input.size()-1 : input.size()-2;
        for(int i=start; i>=0; i--){
            int val = input.get(i);
            if(val==6){
                res.add(9);
            }
            else if(val==9){
                res.add(6);
            }
            else{
                res.add(val);
            }
        }
        return res;
    }

}
