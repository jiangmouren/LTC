package com.mycompany.app.math;

/**
 * Question: https://leetcode.com/problems/excel-sheet-column-title/
 * Given a positive integer, return its corresponding column title as appear in an Excel sheet.
 * For example:
 *
 *     1 -> A
 *     2 -> B
 *     3 -> C
 *     ...
 *     26 -> Z
 *     27 -> AA
 *     28 -> AB
 */

/**
 * Analysis:
 * Typical Radix conversion problem.
 * 主要注意A-Z对应的是1-26，而不是0-25，所以要在loop里面往左shift一下（主要是把末尾往左shift一下）
 */

public class ExcelSheetColumnTitle{
    public String convertToTitle(int n) {
        StringBuilder buf = new StringBuilder();
        while(n>0){
            n -= 1;
            int temp = n%26;
            buf.append((char)(temp+'A'));
            n /= 26;
        }
        return buf.reverse().toString();
    }
}
