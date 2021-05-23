package com.mycompany.app;

import java.util.*;

/**
 * Question:
 * There are 4 types of products each with different price options:
 * For example:
 * price1 = [2, 3];
 * price2 = [4];
 * price3 = [1, 2];
 * price4 = [2, 3];
 * Say the customer has a total amount of $10 and the customer needs to buy 1 piece of each type of product.
 * Return how many options/ways the customer has.
 */

/**
 * Amazon online screening遇到的：这个题本质上是4SumII的一个变化。除了用两个Map之外，要做一些小的优化，比如BinarySearch, PrefixSum.
 * 时间复杂度：O(n^2*lg(n^2))
 */
public class ShoppingOptions {
    public int numberOfOptions(List<Integer> price1, List<Integer> price2,
                               List<Integer> price3, List<Integer> price4, int dollars){
        Map<Integer, Integer> map1 = new HashMap<>();
        Map<Integer, Integer> map2 = new HashMap<>();
        populateMap(map1, price1, price2);
        populateMap(map2, price3, price4);
        Set<Map.Entry<Integer, Integer>> set1 = map1.entrySet();
        Set<Map.Entry<Integer, Integer>> set2 = map2.entrySet();
        List<Map.Entry<Integer, Integer>> list2 = new ArrayList<>();
        list2.addAll(set2);
        Collections.sort(list2, (a, b)->a.getKey()-b.getKey());
        int[] prefixSum = new int[list2.size()];
        int sum = 0;
        for(int i=0; i<list2.size(); i++){
            sum += list2.get(i).getValue();
            prefixSum[i] = sum;
        }
        int res = 0;
        for(Map.Entry<Integer, Integer> entry : set1){
            int cnt1 = entry.getValue();
            int cnt2 = getCount(dollars-entry.getKey(), list2, prefixSum);
            res += (cnt1*cnt2);
        }
        return res;
    }

    private int getCount(int target, List<Map.Entry<Integer, Integer>> list, int[] prefixSum){
        int idx = binarySearch(target, list, 0, list.size()-1);
        if(idx==-1){
            return 0;
        }
        else{
            return prefixSum[idx];
        }
    }

    private int binarySearch(int target, List<Map.Entry<Integer, Integer>> list, int left, int right){
        //termination
        if(left>right){
            return -1;
        }

        int midIdx = (left+right)/2;
        int midValue = list.get(midIdx).getValue();
        if(midValue==target){
            return midIdx;
        }
        else if(midValue>target){
            return binarySearch(target, list, left, midIdx-1);
        }
        else{
            int rightRes = binarySearch(target, list, midIdx+1, right);
            if(rightRes==-1){
                return midIdx;
            }
            else{
                return rightRes;
            }
        }
    }

    private void populateMap(Map<Integer, Integer> map, List<Integer> prices1, List<Integer> prices2){
        for(int price1 : prices1){
            for(int price2 : prices2){
                int temp = price1 + price2;
                if(map.containsKey(temp)){
                    int cnt = map.get(temp);
                    map.put(temp, ++cnt);
                }
                else{
                    map.put(temp, 1);
                }
            }
        }
    }

}
