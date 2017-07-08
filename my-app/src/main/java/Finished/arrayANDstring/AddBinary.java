package Finished.arrayANDstring;
/**
 * Question:
 * Given two binary strings, return their sum (also a binary string).
 * For example,
 * a = "11"
 * b = "1"
 * Return "100".
 */

/**
 * Analysis:
 * Just need to loop through and make sure:
 * 1. Termination cases(2 inputs have different lengths, but even if one/two terminates may still have carry)
 * 2. Carry bits
 * 3. Make sure the first digit is at right most which is the last char in a string
 * 4. Need to reverse the StringBuilder result because of 3
 */

public class AddBinary {
    public String sum(String s1, String s2){
        //edge cases
        if(s1==null || s2==null)
            throw new IllegalArgumentException("input cannot be null");
        int l1 = s1.length();
        int l2 = s2.length();
        if(l1==0) return s2;
        if(l2==0) return s1;

        StringBuilder result = new StringBuilder();
        char carry = '0';
        //passing value across loop no need for pointer unlike the case in recursion.
        //Just need to put a variable outside the loop.

        while(l1>=1 || l2>=1){
            //if list out all combinations, 8 cases, not necessary.
            //Can just loop through s1, s2, Carry to count 1s.
            //count must be loop local so count will be cleaned after each loop.
            int count = 0;
            if(l1>=1 && s1.charAt(l1-1)=='1') count++;
            if(l2>=1 && s2.charAt(l2-1)=='1') count++;
            if(carry=='1') count++;

            if(count==0){
                result.append('0');
                carry = '0';
            }
            else if(count==1){
                result.append('1');
                carry = '0';
            }
            else if(count==2){
                result.append('0');
                carry = '1';
            }
            else{
                result.append('1');
                carry = '1';
            }
            l1--;
            l2--;
        }

        if(carry=='1') result.append('1');
        return result.reverse().toString();
    }

}
