package finished.array_and_string;

/**
 * Created by jiangmouren on 6/4/17.
 */

/**
 * Question:
 * Given two strings s and t, write a function to determine if t is an anagram of s.
 * For example,
 * s = "anagram", t = "nagaram", return true.
 * s = "rat", t = "car", return false.
 * Note:
 * You may assume the string contains only lowercase alphabets.
 * Follow up:
 * What if the inputs contain unicode characters? How would you adapt your solution to such case?
 */

import java.util.*;

/**
 * Analysis:
 * Need to check if two strings have the same set of characters.
 * Naive way would be use two hashmap to count characters. Then count if each key has same value.
 * But actually we don't need the actual number, we just need both to be the same.
 * Better way would be use 1 HashMap, put everything from one string into it.
 * Then take everything from the other string from the map, if any time, char not found, return false.
 * If by the end, map not empty, return false, otherwise return true.
 * Can use a int[] to emulate hashmap in this case.
 *
 * This question can also be asked in an other way like:
 * Given two strings, write a method to decide if one is a permutation of the other.
 * This one and the other "PalindromePermutation" problem, are essentially the same problem.
 * Attention: Do not try to do permutation as soon as you saw the word "permutation"
 * Actually, you will never want to do permutation unless asked to, because it will take factorial time,
 * which is even worse than exponential time. 15! is more than 1.3 Trillion!!!
 *
 * Another way, if trying to avoid the extra storage, can sort both strings and compare side by side.
 * But even quick sort will take extra space, because of stack though.
 */
public class ValidAnagram {
    public boolean isAnagram(String s, String t) {
        if(s==null || t==null) return false;
        int ls = s.length(), lt = t.length();
        if(ls!=lt) return false;
        //ls==0? Covered
        Map<Character, Integer> map = new HashMap<>();
        for(int i=0; i<ls; i++){
            if(map.containsKey(s.charAt(i))){
                int val = map.get(s.charAt(i)) + 1;
                map.put(s.charAt(i), val);
            }
            else{
                map.put(s.charAt(i), 1);
            }
        }

        for(int i=0; i<ls; i++){
            if(!map.containsKey(t.charAt(i))) return false;
            else{
                int tmp = map.get(t.charAt(i));
                if(--tmp==0) map.remove(t.charAt(i));
                else map.put(t.charAt(i), tmp);
            }
        }

        if(map.isEmpty()) return true;
        else return false;
    }
}
