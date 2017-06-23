package Finished;

/**
 * Determine whether an integer is a palindrome. Do this without extra space.
 * A palindromic number or numeral palindrome is a number that remains the same when its digits are reversed.
 * Like 16461, for example, it is "symmetrical".
 */

public class PalindromeNumber{
    public boolean isPalindrome(int n){
        String str = String.valueOf(n);
        int ptr1=0, ptr2=str.length()-1;
        while(ptr1<=ptr2){
            if(str.charAt(ptr1)!=str.charAt(ptr2)) return false;
            ptr1++;
            ptr2--;
        }
        return true;
    }

}
