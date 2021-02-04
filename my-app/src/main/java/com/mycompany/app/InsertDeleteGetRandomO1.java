package com.mycompany.app;

/**
 * https://leetcode.com/problems/insert-delete-getrandom-o1/
 * Implement the RandomizedSet class:
 * bool insert(int val) Inserts an item val into the set if not present. Returns true if the item was not present, false otherwise.
 * bool remove(int val) Removes an item val from the set if present. Returns true if the item was present, false otherwise.
 * int getRandom() Returns a random element from the current set of elements (it's guaranteed that at least one element exists when this method is called). Each element must have the same probability of being returned.
 * Follow up: Could you implement the functions of the class with each function works in average O(1) time?
 *
 * Example 1:
 * Input
 * ["RandomizedSet", "insert", "remove", "insert", "getRandom", "remove", "insert", "getRandom"]
 * [[], [1], [2], [2], [], [1], [2], []]
 * Output
 * [null, true, false, true, 2, true, false, 2]
 *
 * Explanation
 * RandomizedSet randomizedSet = new RandomizedSet();
 * randomizedSet.insert(1); // Inserts 1 to the set. Returns true as 1 was inserted successfully.
 * randomizedSet.remove(2); // Returns false as 2 does not exist in the set.
 * randomizedSet.insert(2); // Inserts 2 to the set, returns true. Set now contains [1,2].
 * randomizedSet.getRandom(); // getRandom() should return either 1 or 2 randomly.
 * randomizedSet.remove(1); // Removes 1 from the set, returns true. Set now contains [2].
 * randomizedSet.insert(2); // 2 was already in the set, so return false.
 * randomizedSet.getRandom(); // Since 2 is the only number in the set, getRandom() will always return 2.
 *
 * Constraints:
 * -231 <= val <= 231 - 1
 * At most 105 calls will be made to insert, remove, and getRandom.
 * There will be at least one element in the data structure when getRandom is called.
 */

import java.util.*;

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */

public class InsertDeleteGetRandomO1{
    //肯定能看出来需要hashmap，唯一要绕的弯就是在remove的时候，不能简单用arraylist的的remove，需要做一些置换处理，因为Order不重要，可以这么搞。

    Map<Integer, Integer> indexMap;
    List<Integer> list;
    /** Initialize your data structure here. */
    public void RandomizedSet() {
        this.indexMap = new HashMap<>();
        this.list = new ArrayList<>();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        boolean res = !this.indexMap.containsKey(val);
        if(res){
            this.list.add(val);
            this.indexMap.put(val, this.list.size()-1);
        }
        return res;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        boolean res = this.indexMap.containsKey(val);
        if(res){
            int idx = this.indexMap.get(val);
            int temp = this.list.get(this.list.size()-1);
            this.list.set(idx, temp);
            //换位后，不要忘记更新indexMap中的index
            this.indexMap.put(temp, idx);
            this.list.remove(this.list.size()-1);
            this.indexMap.remove(val);
        }
        return res;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        //Use the formula (int) (Math.random() * (max-min)) + min to generate values with the min value inclusive and the max exclusive
        int min = 0;
        int max = this.list.size();
        int random = (int) (Math.random() * (max-min)) + min;
        return this.list.get(random);
    }
}
