package com.mycompany.app.logic;

import java.util.*;

/**
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this:
 * (you may want to display this pattern in a fixed font for better legibility)
 *
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * And then read line by line: "PAHNAPLSIIGYIR"
 * Write the code that will take a string and make this conversion given a number of rows:
 * string convert(string s, int numRows);
 *
 * Example 1:
 * Input: s = "PAYPALISHIRING", numRows = 3
 * Output: "PAHNAPLSIIGYIR"
 *
 * Example 2:
 * Input: s = "PAYPALISHIRING", numRows = 4
 * Output: "PINALSIGYAHRPI"
 * Explanation:
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 *
 * Example 3:
 * Input: s = "A", numRows = 1
 * Output: "A"
 *
 * Constraints:
 * 1 <= s.length <= 1000
 * s consists of English letters (lower-case and upper-case), ',' and '.'.
 * 1 <= numRows <= 1000
 */

/**
 * 首先这个题目的描述需要理解一下：要求吧字母按照给定的行数，进行Z字型排列。
 * 解决上的思路也很简单，因为实际发生的是：先正序每行填一个，在倒序每行填一个，只需要行用一个buf，把放进去的char存住，
 * 最后再把逐行合并在一起就可以了。
 * 在控制“正序”/“倒序”的时候使用了一个sign变量，但是sign变换符号的逻辑，只在最少有两行的情况下成立，
 * 所以要特殊处理rowNums==1的情况。
 */
public class ZigZagConversion {
    public String convert(String s, int numRows) {
        //"You cannot create arrays of parameterized types"!!!
        //List<String>[] = new ArrayList<>[numRows];
        List<List<Character>> list = new ArrayList<>();
        for(int i=0; i<numRows; i++){
            list.add(new ArrayList<>());
        }
        int sign = 1;
        int ptr = 0;
        int idx = 0;
        while(ptr<s.length()){
            list.get(idx).add(s.charAt(ptr));
            if(numRows>1){
                idx += sign;
                if(idx==numRows-1 || idx==0){
                    sign = -sign;
                }
            }
            ptr++;
        }
        StringBuilder buf = new StringBuilder();
        for(List<Character> row : list){
            for(char c : row){
                buf.append(c);
            }
        }
        return buf.toString();
    }
}
