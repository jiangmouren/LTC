package Finished;

/**
 * Created by jiangmouren on 6/4/17.
 */

/**
 * Question:
 * Given a roman numeral, convert it to an integer.
 * Input is guaranteed to be within the range from 1 to 3999.
 */

/**
 * Analysis:
 * The biggest problem for this problem is that I don't know how Roman number works.
 * I was trying to use recursive pattern, not a good idea, until I realized it is too complicated.
 * Right thing to do is to sum it up and identify when to subtract, assuming it is in right format.
 * (IV, 4), (IX, 9), (XL, 40), (XC, 90), (CD, 400), (CM, 900)
 *
 * I made another mistake: s="IV", s.charAt(0) should be 'I' not 'V'
 */

public class RomanToInteger {
    public int romanToInt(String s) {
        //special cases
        if(s==null) throw new IllegalArgumentException("wrong inputs");
        if(s.length()==0) return 0;

        //normal cases
        //inputs like: IXL or IXCD are illegal inputs, they both have two ways to interpret.
        //Right format should be LIX or CDIX, basically larger pairs come first.
        int i = 0;
        int result = 0;
        while(i<s.length()){
            if(i==s.length()-1 || !subCase(s, i)){
                result+=getValue(s.charAt(i));
            }
            else{
                result-=getValue(s.charAt(i));
            }
            i++;
        }
        return result;
    }

    private int getValue(char c){
        if(c=='I') return 1;
        else if(c=='V') return 5;
        else if(c=='X') return 10;
        else if(c=='L') return 50;
        else if(c=='C') return 100;
        else if(c=='D') return 500;
        else return 1000;
    }

    //assuming ptr+1<s.length()
    private boolean subCase(String s, int ptr){
        if(s.charAt(ptr)=='I' && s.charAt(ptr+1)=='V' ||
                s.charAt(ptr)=='I' && s.charAt(ptr+1)=='X' ||
                s.charAt(ptr)=='X' && s.charAt(ptr+1)=='L' ||
                s.charAt(ptr)=='X' && s.charAt(ptr+1)=='C' ||
                s.charAt(ptr)=='C' && s.charAt(ptr+1)=='D' ||
                s.charAt(ptr)=='C' && s.charAt(ptr+1)=='M') return true;
        else return false;
    }

}

/**
 * False Analysis:
 * This question is all about flow control and complexity handling.
 * You should first ask how Roman numeral works and figure out the pattern.
 * I:1; X:10; C:100; M:1000
 * V:5; L:50; D:500
 * A basic pattern is like following:
 * (I, 1), (II, 2), (III, 3), (IV, 4), (V, 5), (VI, 6), (VII, 7), (VIII, 8), (IX, 9)
 * Then it recursively changes as I-->X, V-->L, X-->C, etc.
 * No more than 1 combination for 1s;
 * No more than 1 combination for 10s;
 * No more than 1 combination for 100s;
 * No more than 1 combination for 1000s;
 */

/**
 * False Solution:
 public int romanToInt(String s) {
 //special cases
 if(s==null) throw new IllegalArgumentException("wrong inputs");
 if(s.length()==0) return 0;

 //normal cases
 int[] ptr = {0};
 int[] val = {0};
 while(ptr[0]<s.length()){
 getValue(s, ptr, val);
 }
 return val[0];
 }

 private void getValue(String s, int[] ptr, int[] val){
 if(s.charAt(ptr[0])=='I' || s.charAt(ptr[0])=='V'){
 //scale 1: IVX
 helper(s, ptr, val,'I','V','X');
 }
 else if(s.charAt(ptr[0])=='X'){
 //IVX
 if(ptr[0]<s.length()-1 && s.charAt(ptr[0]+1)=='I') helper(s, ptr, val,'I','V','X');
 //XLC
 else helper(s, ptr, val,'X','L','C');
 }
 else if(s.charAt(ptr[0])=='L'){
 //scale 10: XLC
 helper(s, ptr, val,'X','L','C');
 }
 else if(s.charAt(ptr[0])=='C'){
 //XLC
 if(ptr[0]<s.length()-1 && s.charAt(ptr[0]+1)=='X') helper(s, ptr, val,'X','L','C');
 //CDM
 else helper(s, ptr, val,'C','D','M');
 }
 else if(s.charAt(ptr[0])=='D'){
 //scale 100: CDM
 helper(s, ptr, val,'C','D','M');
 }
 else if(s.charAt(ptr[0])=='M'){
 //CDM
 if(ptr[0]<s.length()-1 && s.charAt(ptr[0]+1)=='C') helper(s, ptr, val,'C','D','M');
 //M
 else helper(s, ptr, val,'M','G','G');
 }
 }

 private void helper(String s, int[] ptr, int[] val, char c1, char c2, char c3){
 int tmpVal=0, scale;
 if(c1=='I') scale=1;
 else if(c1=='X') scale=10;
 else if(c1=='C') scale=100;
 else scale=1000;

 //case 1
 if(s.charAt(ptr[0])==c1 &&
 (ptr[0]==s.length()-1 || s.charAt(ptr[0]+1)!=c1 && s.charAt(ptr[0]+1)!=c2)){
 tmpVal = 1;
 }
 //case 2
 if(s.charAt(ptr[0])==c1 &&
 (ptr[0]+1==s.length()-1 && s.charAt(ptr[0]+1)==c1 ||
 s.charAt(ptr[0]+1)==c1 && s.charAt(ptr[0]+2)!=c1)){
 tmpVal = 1;
 }

 }
 */
