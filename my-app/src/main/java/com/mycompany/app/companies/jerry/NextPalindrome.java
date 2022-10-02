package com.mycompany.app.companies.jerry;
import java.util.*;

/**
 * https://www.1point3acres.com/bbs/thread-932171-1-1.html
 * coding只有15分钟到20分钟，题目是给一个int，输出最小的，大于这个数的回文串：比如说：12300 -> 12321。
 * 解答：
 * 基本思路是从中间劈开，然后让右侧去Match左侧，因为改动右侧造成的delta比较小。
 * 如果匹配之后，刚好大于原来的数字，那么什么都不用做。
 * 如果匹配之后，小于后者等于原来的数字，那么把中间的一个(odd case)或者两个(even case)数字往上增加1
 * 那么在进行+1操作的时候还要考虑如果刚好中间一个或者两个数字是9的情况怎么搞？
 * 那么我们就在中间那个，或者两个当中左侧那个9，那一位，给它加一，改往高位进位就进位，调整完了之后，再次用右侧去匹配左侧。
 * 上面再次匹配之后，必然是既满足palindrome也满足更大这两个要求了。
 */
public class NextPalindrome {
    //assume num>=0
    public int nextPalindrome(int num){
        int tempVal = matchRightToLeft(num);
        if(tempVal>num){
            return tempVal;
        }
        String str = String.valueOf(tempVal);
        int mid = (str.length()-1)/2;
        boolean odd = str.length()%2==1;
        if(str.charAt(mid)!='9'){
            tempVal += Math.pow(10, mid-1);
            if(!odd){
                tempVal += Math.pow(10, mid-2);
            }
            return tempVal;
        }
        else{
            tempVal += Math.pow(10, mid-1);
            return matchRightToLeft(tempVal);
        }
    }

    private int matchRightToLeft(int num){
        String str = String.valueOf(num);
        StringBuilder buf = new StringBuilder();
        int mid = (str.length()-1)/2;
        boolean odd = str.length()%2==1;
        buf.append(str.substring(0, mid+1));
        int ptr = buf.length();
        if(odd){
            ptr--;
        }
        while(ptr>=0){
            buf.append(buf.charAt(ptr));
            ptr--;
        }
        String temp = buf.toString();
        int tempVal = Integer.parseInt(temp);
        return tempVal;
    }
}
