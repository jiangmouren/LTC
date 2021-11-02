package com.mycompany.app.backtracking;

import java.util.*;

/**
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.
 * A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 *
 * Example 1:
 * Input: digits = "23"
 * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * Example 2:
 * Input: digits = ""
 * Output: []
 *
 * Example 3:
 * Input: digits = "2"
 * Output: ["a","b","c"]
 *
 * Constraints:
 * 0 <= digits.length <= 4
 * digits[i] is a digit in the range ['2', '9'].
 */

public class LetterCombinationsOfAPhoneNumber{
    public List<String> letterCombinations(String digits) {
        if(digits==null || digits.length()==0){
            return new ArrayList<String>();
        }
        //典型的backtracking
        List<String> res = new ArrayList<>();
        StringBuilder buf = new StringBuilder();
        helper(res, buf, digits, 0);
        return res;
    }

    private void helper(List<String> res, StringBuilder buf, String digits, int pos){
        //termination case
        if(pos>=digits.length()){
            res.add(buf.toString());
            return;
        }

        int num = digits.charAt(pos) - '0';
        List<Character> letters = getLetters(num);

        for(char letter : letters){
            buf.append(letter);
            helper(res, buf, digits, pos+1);
            buf.deleteCharAt(buf.length()-1);
        }
    }

    private List<Character> getLetters(int num){
        int first = (num-2)*3;//the offset of the letter represented by the number
        if(num>=8){
            first += 1;
        }

        List<Character> res = new ArrayList<>();
        int cnt = 3;
        if(num==7 || num==9){
            cnt = 4;
        }
        for(int i=0; i<cnt; i++){
            char c = (char)('a' + first+i);
            res.add(c);
        }
        return res;
    }
}