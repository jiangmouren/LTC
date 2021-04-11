package com.mycompany.app;

/**
 * https://leetcode.com/problems/integer-to-english-words/
 * Convert a non-negative integer num to its English words representation.
 *
 * Example 1:
 * Input: num = 123
 * Output: "One Hundred Twenty Three"
 *
 * Example 2:
 * Input: num = 12345
 * Output: "Twelve Thousand Three Hundred Forty Five"
 *
 * Example 3:
 * Input: num = 1234567
 * Output: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
 *
 * Example 4:
 * Input: num = 1234567891
 * Output: "One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety One"
 *
 * Constraints:
 * 0 <= num <= 2^31 - 1
 */

/**
 * Analysis:
 * 思路很简单就是按照英文命名规律，三位数一组的处理，主要是实现起来很多细节需要注意。
 * 因为题目里提到最大数字就是int的最大值，2^31 -1 = 2,147,483,647
 * 所以最多也就是有4组数字需要处理。
 * 具体在处理每组的3个数字时又要注意，10-19的特殊，以及0要对应“”.
 * 还要注意控制加空格，选择在每次append之前检查buf里是否有东西。
 */

class IntegerToEnglishWords {
    public String numberToWords(int num) {
        //max int: 2,147,483,647
        //divide into 4 steps:
        //1st step if(num>999,999,999), handle highest 3 digits; 2nd step if(num>999,999), handle next 3 digits;
        //3rd step if(num>999), handle next 3 digits; 4th step handle lowest 3 digits;按照这个顺序处理方便往StringBuilder里填结果
        if(num==0){
            return "Zero";
        }
        StringBuilder buf = new StringBuilder();
        int temp;
        String str;
        if(num>999999999){
            temp = num / 1000000000;
            str = toString(temp);
            if(!str.isEmpty()){
                buf.append(str);
                buf.append(" Billion");
            }
        }
        if(num>999999){
            temp = num / 1000000;//取高位，用除法
            temp = temp % 1000;//去低位，用取模
            str = toString(temp);
            if(!str.isEmpty()){
                addSpace(buf);
                buf.append(str);
                buf.append(" Million");
            }
        }
        if(num>999){
            temp = num / 1000;
            temp = temp % 1000;
            str = toString(temp);
            if(!str.isEmpty()){
                addSpace(buf);
                buf.append(str);
                buf.append(" Thousand");
            }
        }
        temp = num % 1000;
        str = toString(temp);
        if(!str.isEmpty()){
            addSpace(buf);
            buf.append(str);
        }
        return buf.toString();
    }

    //num is a maximum 3 digit int
    //return string representation of num
    private String toString(int num){
        StringBuilder buf = new StringBuilder();
        int temp;

        if(num>99){
            temp = num/100;
            buf.append(getNumString(temp, true));
            buf.append(" Hundred");
        }
        temp = num%100;
        if(temp>9 && temp<20){
            addSpace(buf);
            buf.append(TenToNineteen(temp));
        }
        else if(temp>=20){
            int k = temp/10;
            addSpace(buf);
            buf.append(getNumString(k, false));
            k = temp%10;
            String str = getNumString(k, true);
            if(!str.isEmpty()){
                addSpace(buf);
                buf.append(str);
            }
        }
        else{
            String str = getNumString(temp, true);
            if(!str.isEmpty()){
                addSpace(buf);
                buf.append(str);
            }
        }
        return buf.toString();
    }

    private void addSpace(StringBuilder buf){
        if(buf.length()>0){
            buf.append(" ");
        }
    }

    //taking single digit number and return text representation
    private String getNumString(int num, boolean low){
        String res;
        if(low){
            switch(num){
                case 1: res = "One";
                    break;
                case 2: res = "Two";
                    break;
                case 3: res = "Three";
                    break;
                case 4: res = "Four";
                    break;
                case 5: res = "Five";
                    break;
                case 6: res = "Six";
                    break;
                case 7: res = "Seven";
                    break;
                case 8: res = "Eight";
                    break;
                case 9: res = "Nine";
                    break;
                default: res = "";
                    break;
            }
        }
        else{
            switch(num){
                case 2: res = "Twenty";
                    break;
                case 3: res = "Thirty";
                    break;
                case 4: res = "Forty";
                    break;
                case 5: res = "Fifty";
                    break;
                case 6: res = "Sixty";
                    break;
                case 7: res = "Seventy";
                    break;
                case 8: res = "Eighty";
                    break;
                case 9: res = "Ninety";
                    break;
                default: res = "";
                    break;
            }
        }

        return res;
    }

    private String TenToNineteen(int num){
        String res;
        switch(num){
            case 10: res = "Ten";
                break;
            case 11: res = "Eleven";
                break;
            case 12: res = "Twelve";
                break;
            case 13: res = "Thirteen";
                break;
            case 14: res = "Fourteen";
                break;
            case 15: res = "Fifteen";
                break;
            case 16: res = "Sixteen";
                break;
            case 17: res = "Seventeen";
                break;
            case 18: res = "Eighteen";
                break;
            case 19: res = "Nineteen";
                break;
            default: res = "";
                break;
        }
        return res;
    }
}
