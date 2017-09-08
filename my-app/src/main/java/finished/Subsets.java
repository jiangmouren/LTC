package finished;
/**
 * Question:
 * Given a set of distinct integers, nums, return all possible subsets.
 * Note: The solution set must not contain duplicate subsets.
 * For example,
 * If nums = [1,2,3], a solution is:
 *
 * [
 *   [3],
 *   [1],
 *   [2],
 *   [1,2,3],
 *   [1,3],
 *   [2,3],
 *   [1,2],
 *   []
 * ]
 */

/**
 * Analysis:
 * It is trivial to see the empty set and those single elements sets.
 * The only trap is for none single subsets, how to avoid duplication?
 * The answer is always look forward no backward, say 2nd element should be on the right of 1st element,
 * and 3rd on the right of the 2nd. That's also how you do it manually.
 * The above are the permutation rules.
 */
import java.util.*;
public class Subsets{
    public List<List<Integer>> subsets(int[] input){
        if(input==null) throw new IllegalArgumentException("Input cannot be null");

        int length = input.length;
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> buf = new ArrayList<>();
        for(int i=0; i<=length; i++){
            getSubset(input, result, buf, 0, i);
        }
        return result;
    }

    private void getSubset(int[] input, List<List<Integer>> result, List<Integer> buf, int pos, int size){
        //edge case
        if(size==0){
            List<Integer> tmp = new ArrayList<>();
            tmp.addAll(buf);
            result.add(tmp);
            return;
        }

        //normal cases
        //Need to make sure we leave enough room for next level
        for(int i=pos; i<=input.length-size; i++){
            buf.add(input[i]);
            getSubset(input, result, buf, i+1, size-1);
            buf.remove(buf.size()-1);
        }
    }

}
