package finished.array_and_string;

/**
 * Created by jiangmouren on 6/4/17.
 */

/**
 * Question:
 * Given an array of integers that is already sorted in ascending order,
 * find two numbers such that they add up to a specific target number.
 * The function twoSum should return indices of the two numbers such that they add up to the target,
 * where index1 must be less than index2.
 * Please note that your returned answers (both index1 and index2) are not zero-based.
 * You may assume that each input would have exactly one solution and you may not use the same element twice.
 * Input: numbers={2, 7, 11, 15}, target=9
 * Output: index1=1, index2=2
 */

/**
 * Analysis:
 * The key points:
 * 1. because it is sorted, you can to something like a binary search.
 * 2. once you moved away from a point, you can prove you will never needed it.
 */

public class TwoSumInputArraySorted {
    public int[] twoSum(int[] numbers, int target) {
        int i=0, j=numbers.length-1;
        int[] result = new int[2];
        while(i<j){
            if(numbers[i]+numbers[j]==target){
                result[0] = i+1;
                result[1] = j+1;
                return result;
            }
            if(numbers[i]+numbers[j]<target) i++;
            if(numbers[i]+numbers[j]>target) j--;
        }
        //if returned from here, no result.
        return result;
    }

}
