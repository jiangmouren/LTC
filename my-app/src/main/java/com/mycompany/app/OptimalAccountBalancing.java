package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/optimal-account-balancing/
 * You are given an array of transactions transactions where transactions[i] = [fromi, toi, amounti]
 * indicates that the person with ID = fromi gave amounti $ to the person with ID = toi.
 * Return the minimum number of transactions required to settle the debt.
 *
 * Example 1:
 * Input: transactions = [[0,1,10],[2,0,5]]
 * Output: 2
 * Explanation:
 * Person #0 gave person #1 $10.
 * Person #2 gave person #0 $5.
 * Two transactions are needed. One way to settle the debt is person #1 pays person #0 and #2 $5 each.
 *
 * Example 2:
 * Input: transactions = [[0,1,10],[1,0,1],[1,2,5],[2,0,5]]
 * Output: 1
 * Explanation:
 * Person #0 gave person #1 $10.
 * Person #1 gave person #0 $1.
 * Person #1 gave person #2 $5.
 * Person #2 gave person #0 $5.
 * Therefore, person #1 only need to give person #0 $4, and all debt is settled.
 *
 * Constraints:
 * 1 <= transactions.length <= 8
 * transactions[i].length == 3
 * 0 <= fromi, toi <= 20
 * fromi != toi
 * 1 <= amounti <= 100
 */

/**
 * 先把net balance算出来，然后对balance!=0的做settle.
 */
public class OptimalAccountBalancing {
    public int minTransfers(int[][] transactions) {
        //get net balance for each account
        Map<Integer, Integer> map = new HashMap<>();
        for(int[] trans : transactions){
            if(!map.containsKey(trans[0])){
                map.put(trans[0], 0);
            }
            if(!map.containsKey(trans[1])){
                map.put(trans[1], 0);
            }
            map.put(trans[0], map.get(trans[0])+(-trans[2]));
            map.put(trans[1], map.get(trans[1])+trans[2]);
        }
        List<Integer> balances = new ArrayList<>();
        balances.addAll(map.values());
        return settle(balances, 0);
    }

    //backtracking to settle, balances.size()>1
    private int settle(List<Integer> balances, int start){
        //一定要先找到非0的start，否则后面for loop里面的if不成立，会在0的位置return Max_Value
        while(start<balances.size() && balances.get(start)==0){
            start++;
        }
        //termination, 注意是在balances.size()-1的位置就要终止，否则在size()-1的位置就会return MAX_VALUE
        if(start>=balances.size()){
            return 0;
        }

        int res = Integer.MAX_VALUE;
        for(int i=start+1; i<balances.size(); i++){
            if(balances.get(start)*balances.get(i)<0){
                balances.set(i, balances.get(i)+balances.get(start));
                res = Math.min(res, settle(balances, start+1)+1);
                balances.set(i, balances.get(i)-balances.get(start));
                //No residual is best option for current start, 没有这个跑leetcode需要100ms，有这个，只需要7ms
                if(balances.get(i)+balances.get(start)==0){
                    break;
                }
            }
        }
        return res;
    }
}
