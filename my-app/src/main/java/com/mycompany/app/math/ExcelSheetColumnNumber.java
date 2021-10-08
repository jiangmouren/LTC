package com.mycompany.app.math;

/**
 * Question: https://leetcode.com/problems/excel-sheet-column-number/
 * Given a string columnTitle that represents the column title as appear in an Excel sheet,
 * return its corresponding column number.
 *
 * For example:
 * A -> 1
 * B -> 2
 * C -> 3
 * ...
 * Z -> 26
 * AA -> 27
 * AB -> 28
 * ...
 *
 * Example 1:
 * Input: columnTitle = "A"
 * Output: 1
 *
 * Example 2:
 * Input: columnTitle = "AB"
 * Output: 28
 *
 * Example 3:
 * Input: columnTitle = "ZY"
 * Output: 701
 *
 * Example 4:
 * Input: columnTitle = "FXSHRXW"
 * Output: 2147483647
 *
 * Constraints:
 * 1 <= columnTitle.length <= 7
 * columnTitle consists only of uppercase English letters.
 * columnTitle is in the range ["A", "FXSHRXW"].
 */


public class ExcelSheetColumnNumber {
    public int titleToNumber(String s) {
        long res = 0;
        for(int i=s.length()-1; i>=0; i--){
            char c = s.charAt(i);
            long value = (c-'A'+1)*(long)Math.pow(26, s.length()-1-i);
            res += value;
        }
        return (int) res;
    }
}
