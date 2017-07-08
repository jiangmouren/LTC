package Finished.arrayANDstring;

/**
 * Question:
 * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
 * Write a function to determine if a number is strobogrammatic.
 * The number is represented as a string.
 * For example, the numbers "69", "88", and "818" are all strobogrammatic.
 */

/**
 * Analysis:
 * Just write down 0 to 9, you can find the rule:
 * 0, 1, 8 are allowed anywhere, 6 and 9 must appear in pair and symmetrically, other numbers are not allowed.
 * Thought about using map, but as we only care about the symmetry, so two pointers can do.
 */

public class StrobogrammaticNumber {
    public boolean strobogrammaticNumber(String input){
        if(input==null || input.length()==0) throw new IllegalArgumentException("Input cannot be null");
        int ptr1=0, ptr2=input.length()-1;
        while(ptr1<=ptr2){
            if(!allowed(input.charAt(ptr1)) || !allowed(input.charAt(ptr2))) return false;
            else if(input.charAt(ptr1)=='6' && input.charAt(ptr2)=='9' ||
                    input.charAt(ptr1)=='9' && input.charAt(ptr2)=='6'){;}
            else if(input.charAt(ptr1)!=input.charAt(ptr2)) return false;
            ptr1++;
            ptr2--;
        }
        return true;
    }

    private boolean allowed(char input){
        if(input=='0' || input=='1' || input=='8' || input=='6' || input=='9') return true;
        else return false;
    }
}
