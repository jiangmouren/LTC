package Finished;

/**
 * Created by jiangmouren on 6/4/17.
 */

/**
 * Question:
 * Related to question Excel Sheet Column Title
 * Given a column title as appear in an Excel sheet, return its corresponding column number.
 * For example:
 *     A -> 1
 *     B -> 2
 *     C -> 3
 *     ...
 *     Z -> 26
 *     AA -> 27
 *     AB -> 28
 */

/**
 * Analysis:
 * This is a Radix conversion problem. Convert a Radix 20 number to Radix 10 number.
 * And the Radix 26 number is right skewed by 1, since it starts from 1.
 */

public class ExcelSheetColumnNumber {
    public int titleToNumber(String s) {
        if(s==null) throw new IllegalArgumentException("input cannot be null");
        if(s.length()==0) throw new IllegalArgumentException("input cannot be empty");
        int result = 0;
        //Attention: it is easy to make stupid mistake here: S.charAt(0) is MSB
        for(int i=s.length()-1; i>=0; i--){
            result += ((s.charAt(i)-'A' + 1) * Math.pow(26, s.length()-1-i));
        }
        return result;
    }
}
