package Finished;
/**
 * Question:
 * You are playing the following Flip Game with your friend:
 * Given a string that contains only these two characters:
 * + and -, you and your friend take turns to flip two consecutive "++" into "--".
 * The game ends when a person can no longer make a move and therefore the other person will be the winner.
 * Write a function to determine if the starting player can guarantee a win.
 * For example, given s = "++++", return true.
 * The starting player can guarantee a win by flipping the middle "++" to become "+--+".
 * Follow up:
 * Derive your algorithm's runtime complexity.
 */

/**
 * Analysis:
 * This is still "BackTracking", but it is just a more complicated version.
 * The recursion happens every 2 levels instead of every level.
 * Two barriers:
 * 1. Figuring out the logic of "guarantee a win", during which you should find it recurse every 2 levels instead of 1.
 * 2. How to implement the logic to represent:
 *     a. at least one in the first level; b. all of them in the second level; c. two level recursion.
 *
 */
import java.util.*;

public class FlipGameII{
    public boolean guaranteeWin(String str){
        if(str ==null || str.length()<2) throw new IllegalArgumentException("Illegal Argument");

        //Termination Case Will not be at level 1, but at level 2
        List<String> list1 = getNext(str);//list1 means results after player1
        // for level1, requirement is "find one", so possibly return true inside and should only return false in the end.
        for(String str1 : list1){
            //for level2, requirement is "all of them", so only return true after loop everything
            // and cannot return false here because of level1 requirement.
            List<String> list2 = getNext(str1);//the termination case is when list2 is empty, just return true, covered.
            boolean flag = true;
            for(String str2 : list2){
                flag = flag && guaranteeWin(str2);
            }
            if(flag) return true;
        }
        return false;
    }

    private List<String> getNext(String str){
        List<String> result = new ArrayList<>();
        StringBuilder buf = new StringBuilder(str);
        int ptr1 = 0;
        while(ptr1<buf.length()-1){
            if(buf.charAt(ptr1)=='+' && buf.charAt(ptr1+1)=='+'){
                buf.setCharAt(ptr1, '-');
                buf.setCharAt(ptr1+1, '-');
                result.add(buf.toString());
                buf.setCharAt(ptr1, '+');
                buf.setCharAt(ptr1+1, '+');
            }
            ptr1++;
        }
        return result;
    }
}
