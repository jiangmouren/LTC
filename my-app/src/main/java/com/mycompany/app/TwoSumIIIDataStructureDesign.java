package com.mycompany.app;

import java.util.*;

/**
 * Question: https://leetcode.com/problems/two-sum-iii-data-structure-design/
 * Design a data structure that accepts a stream of integers and checks
 * if it has a pair of integers that sum up to a particular value.
 *
 * Implement the TwoSum class:
 * TwoSum() Initializes the TwoSum object, with an empty array initially.
 * void add(int number) Adds number to the data structure.
 * boolean find(int value) Returns true if there exists any pair of numbers whose sum is equal to value,
 * otherwise, it returns false.
 *
 * Example 1:
 * Input
 * ["TwoSum", "add", "add", "add", "find", "find"]
 * [[], [1], [3], [5], [4], [7]]
 * Output
 * [null, null, null, null, true, false]
 * Explanation
 * TwoSum twoSum = new TwoSum();
 * twoSum.add(1);   // [] --> [1]
 * twoSum.add(3);   // [1] --> [1,3]
 * twoSum.add(5);   // [1,3] --> [1,3,5]
 * twoSum.find(4);  // 1 + 3 = 4, return true
 * twoSum.find(7);  // No two integers sum up to 7, return false
 *
 * Constraints:
 * -105 <= number <= 105
 * -231 <= value <= 231 - 1
 * At most 5 * 104 calls will be made to add and find.
 */

public class TwoSumIIIDataStructureDesign {
    //三种思路:
    //一种是每次进来一个数，就跟之前所有的数，计算可能的sum，然后存下所有的可能的sum
    //这样每次add()是O(n),但是每次find()是O(1)

    //另外一种是把所有进来的数，存在一个sorted red-black-tree里面，这样每次add()是O(lgn),每次find()是O(n)
    //但是这里要处理duplicated values,所以不能用java自带的TreeMap,而是得写自己的red-back-tree
    //而且从上面的分析来看，也并没有明显的优势
    //结论还是放弃第二种思路

    //第三种思路就是把所有进来数存在一个hashMap里，key是数本身，value是cnt
    //这样每次add()是O(1),但是每次find()是O(n)，因为需要把hashMap entry过一遍，看对应每个key能不能找到需要的另一半
    //但是对于这第三种解法的好处在于，我的find()平均complexity应该是好于O(n)的，只是worst case是O(n)

    Map<Integer, Integer> map;

    /** Initialize your data structure here. */
    public TwoSumIIIDataStructureDesign() {
        this.map = new HashMap<>();
    }

    /** Add the number to an internal data structure.. */
    public void add(int number) {
        if(this.map.containsKey(number)){
            int cnt = this.map.get(number);
            this.map.put(number, ++cnt);
        }
        else{
            this.map.put(number, 1);
        }
    }

    /** Find if there exists any pair of numbers which sum is equal to the value. */
    public boolean find(int value) {
        Set<Integer> keys = this.map.keySet();
        for(int key : keys){
            int target = value - key;
            if(this.map.containsKey(target)){
                if(target==key){
                    if(map.get(target)>1){
                        return true;
                    }
                }
                else{
                    return true;
                }
            }
        }
        return false;
    }
}
