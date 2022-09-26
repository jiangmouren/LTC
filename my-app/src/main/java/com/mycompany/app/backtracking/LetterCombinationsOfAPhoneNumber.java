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
        Map<Integer, List<String>> dict = buildDict();
        return getList(digits, 0, dict);
    }

    //类似DP，先解决sub-problem，逐级原问题构造
    private List<String> getList(String digits, int pos, Map<Integer, List<String>> dict){
        List<String> res = new ArrayList<>();
        if(digits.length()==0){
            return res;
        }
        if(pos>=digits.length()){
            res.add("");//这里放个empty string,保证后面loop through partialRes的时候不会直接bypass,否则最后一位的字母就无法拿到了。
            return res;
        }
        List<String> partialRes = getList(digits, pos+1, dict);
        for(String s : dict.get(Character.getNumericValue(digits.charAt(pos)))){
            StringBuilder buf = new StringBuilder();
            buf.append(s);
            for(String sub : partialRes){
                buf.append(sub);
                res.add(buf.toString());
                buf.setLength(1);
            }
        }
        return res;
    }

    private Map<Integer, List<String>> buildDict(){
        Map<Integer, List<String>> dict = new HashMap<>();
        for(int i=2; i<=9; i++){
            List<String> list = new ArrayList<>();
            dict.put(i, list);
        }
        dict.get(2).add("a");
        dict.get(2).add("b");
        dict.get(2).add("c");

        dict.get(3).add("d");
        dict.get(3).add("e");
        dict.get(3).add("f");

        dict.get(4).add("g");
        dict.get(4).add("h");
        dict.get(4).add("i");

        dict.get(5).add("j");
        dict.get(5).add("k");
        dict.get(5).add("l");

        dict.get(6).add("m");
        dict.get(6).add("n");
        dict.get(6).add("o");

        dict.get(7).add("p");
        dict.get(7).add("q");
        dict.get(7).add("r");
        dict.get(7).add("s");

        dict.get(8).add("t");
        dict.get(8).add("u");
        dict.get(8).add("v");

        dict.get(9).add("w");
        dict.get(9).add("x");
        dict.get(9).add("y");
        dict.get(9).add("z");

        return dict;
    }

    //用下面这种backtracking写法的好处是省去了中间的很多partial results，缺点是重复计算
    public List<String> letterCombinationsBackTracking(String digits) {
        if(digits==null || digits.length()==0){
            return new ArrayList<String>();
        }
        //典型的backtracking
        List<String> res = new ArrayList<>();
        StringBuilder buf = new StringBuilder();
        Map<Integer, List<String>> dict = buildDict();
        helper(res, buf, digits, 0, dict);
        return res;
    }

    private void helper(List<String> res, StringBuilder buf, String digits, int pos, Map<Integer, List<String>> dict){
        //termination case
        if(pos>=digits.length()){
            res.add(buf.toString());
            return;
        }

        int num = digits.charAt(pos) - '0';
        List<String> letters = dict.get(num);

        for(String letter : letters){
            buf.append(letter);
            helper(res, buf, digits, pos+1, dict);
            buf.deleteCharAt(buf.length()-1);
        }
    }

}