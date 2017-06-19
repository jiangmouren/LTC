package Finished;
/**
 * Question:
 * Given a non-negative integer represented as a non-empty array of digits, plus one to the integer.
 * You may assume the integer do not contain any leading zero, except the number 0 itself.
 * The digits are stored such that the most significant digit is at the head of the list.
 */

/**
 * There are 2 catches:
 * 1. the order of the digits
 * 2. if a carry in the end is needed, i.e. the length of the result can be n+1.
 */
import java.util.*;

public class PlusOne {
    public int[] plusOne(int[] nums){
        //assume nums[0] is the least significant bit
        List<Integer> list = new LinkedList<>();
        int carry = 1;
        for(int i=0; i<nums.length; i++){
            int tmp = nums[i]+carry;
            if(tmp>9){
                carry = 1;
                list.add(tmp-10);
            }
            else{
                carry = 0;
                list.add(tmp);
            }
        }
        if(carry==1) list.add(1);
        int[] result = new int[list.size()];
        ListIterator<Integer> iterator = list.listIterator();
        while(iterator.hasNext()){
            result[iterator.nextIndex()] = iterator.next();
        }
        return result;
    }
}
