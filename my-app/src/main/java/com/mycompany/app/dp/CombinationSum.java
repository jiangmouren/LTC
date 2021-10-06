package com.mycompany.app.dp;
import java.util.*;

public class CombinationSum {
    /**
     * 基于coin change 2演变而来的写法
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        //这个写法有点Hack，通常不能使用“Array of List”("List of Array" is Ok)，因为不能有array of generics
        //然后他这里在instantiate array的时候使用了不带generics的ArrayList，实际生成的是一个array Of null
        //然后再在后面替换每一个null reference为一个concrete arrayList,最后的结果就是还是得到了array of generics
        //就是拆成两步，绕过了compiler的排查，不要这么写！！！
        //List<List<Integer>>[] dp = new ArrayList[target + 1];
        List<List<List<Integer>>> dp = new ArrayList<>();
        for (int i = 0; i <= target; i++){
            dp.add(new ArrayList<>());
        }
        dp.get(0).add(new ArrayList<>());

        for (int candidate: candidates) {
            for (int j = candidate; j <= target; j++) {
                //dp[i-coin]表达了使用目前所有涵盖的coin所能构成i-coin的组合个数
                //dp[i]在加上dp[i-coin]之前，表达在不使用当前所讨论coin的前提下，所能构成i的组合数
                //然后在加上至少包含一个当前所讨论coin的组合数：所以"i-coin"表达reserve一个当前coin
                //然后dp[i-coin]则涵盖使用和不使用当前coin所能构成i-coin的所有情况
                for (List<Integer> comb: dp.get(j-candidate)) {
                    List<Integer> newComb = new ArrayList(comb);
                    newComb.add(candidate);
                    dp.get(j).add(newComb);
                }
            }
        }
        return dp.get(target);
    }

    /**
     * 基于coin change演变而来的写法, 需要用到set de-dupe
     */
    public List<List<Integer>> combinationSumSln2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Map<Integer, List<List<Integer>>> map = new HashMap<>();
        dp(candidates, target, res, map);
        for(Map.Entry<Integer,List<List<Integer>>> entry : map.entrySet()){
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
        return res;
    }

    private boolean dp(int[] candidates, int target, List<List<Integer>> res, Map<Integer, List<List<Integer>>> map){
        //termination
        if(map.containsKey(target)){
            res = map.get(target);
            return true;
        }
        if(target==0){
            //需要return list of empty list,后面构造super-problem的解的时候，这个subproblem不能return empty list
            res.add(new ArrayList<Integer>());
            map.put(target, res);
            return true;
        }
        if(target<0){
            return false;
        }

        Set<List<Integer>> set = new HashSet<>();
        for(int candidate: candidates){
            List<List<Integer>> subList = new ArrayList<>();
            if(dp(candidates, target-candidate, subList, map)){
                for(List<Integer> sub : subList){
                    List<Integer> subNew = new ArrayList<>();
                    subNew.addAll(sub);
                    subNew.add(candidate);
                    Collections.sort(subNew);
                    set.add(subNew);
                }
            }
        }
        if(!set.isEmpty()){
            res.addAll(set);
            map.put(target, res);
            return true;
        }
        else{
            return false;
        }
    }
}
