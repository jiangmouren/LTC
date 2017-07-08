package Finished.arrayANDstring;

/**
 * Created by jiangmouren on 6/1/17.
 */


/**
 * Question:
 * Given a string, determine if a permutation of the string could form a palindrome.
 *
 * For example, "code" -> False, "aab" -> True, "carerac" -> True.
 */

/**
 * Analysis:
 * Start with examples and try to summarize the pattern:
 * For any string to be a permutation palindrome(PP), it has to satisfy the following terms:
 * A string S can be represented as C(1)C(2)...C(i)...C(n), where i represents any alphabet, and C stands for "count"
 * So in the above example, S is constructed from C(1) of 1, C(i) of i, ...etc.
 * In order for S to be PP: among all C(i), there could be maximum 1 odd number and not all C(i) are 0.
 *
 * So now we have the basic solution:
 * Use a hashmap to list out all the distinct alphabets and there counts. Then we can check the counts.
 *
 * Problem with the above approach: No matter what type we use to store the counts, there will be a chance of overflow.
 * But if we come back to the basics, do we really need the counts, why?
 * Actually we do not need the actual counts, all we need is whether it is even or odd for a count, so we can use booleans.
 *
 * Also because we know we need no more than 26 buckets for this hashmap, we can just use a Boolean Array.
 */

public class PalindromePermutation {
    public boolean canPermutePalindrome(String s){
        //edge cases
        if(s==null) return false;
        int l = s.length();
        if(l==0) return false;
        if(l==1) return true;

        //all value in boolean array will be default to "False"
        //assume all inputs are alphabetical, and all letters are in lower case
        //In leetcode, the above assumption is not true. Need to fix this.
        boolean[] map = new boolean[26];
        for(int i=0; i<l; i++){
            int key = s.charAt(i) - 'a';
            map[key]=!map[key];
        }

        int count = 0;
        for(boolean v : map){
            if(v==true) count++;
        }

        if(count<2) return true;
        else return false;
    }
}
