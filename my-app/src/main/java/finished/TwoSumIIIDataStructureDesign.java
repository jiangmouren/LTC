package finished;

import java.util.*;

/**
 * Question:
 * Design and implement a TwoSum class.
 * It should support the following operations: add and find.
 * add - Add the number to an internal data structure.
 * find - Find if there exists any pair of numbers which sum is equal to the value.
 * For example,
 * add(1); add(3); add(5);
 * find(4) -> true
 * find(7) -> false
 */

/**
 * Use a set, so add will be O(1) and find will be O(n);
 * If use sorted array, both add and find will be O(n).
 */
public class TwoSumIIIDataStructureDesign {
    private Set<Integer> set;
    public TwoSumIIIDataStructureDesign(){
        this.set = new HashSet<>();
    }

    public void add(int val){
        this.set.add(val);
    }

    public boolean find(int x){
        for(int tmp : this.set){
            if(set.contains(x-tmp)) return true;
        }
        return false;
    }
}
