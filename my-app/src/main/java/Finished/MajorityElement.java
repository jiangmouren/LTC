package Finished;

/**
 * Created by jiangmouren on 6/4/17.
 */

/**
 * Question:
 * Given an array of size n, find the majority element.
 * The majority element is the element that appears more than ⌊ n/2 ⌋ times.
 * You may assume that the array is non-empty and the majority element always exist in the array.
 */

/**
 * Analysis:
 * This is one of its kind.
 * Most intuitive way would be using Map or Sort.
 * This one can by called "Majority Algorithm", "Vote Algorithm" or "Survivor Algorithm".
 */
public class MajorityElement {
    public int majorityElement(int[] nums) {
        int cnt=0, res=0;
        for(int num : nums){
            if(cnt==0)
                res=num;
            else if(num!=res)
                cnt--;
            else
                cnt++;
        }
        return res;
    }
}
