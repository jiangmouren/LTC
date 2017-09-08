package finished.math;

/**
 * Created by jiangmouren on 6/4/17.
 */

/**
 * Question:
 * Write an algorithm to determine if a number is "happy".
 * A happy number is a number defined by the following process:
 * Starting with any positive integer, replace the number by the sum of the squares of its digits,
 * and repeat the process until the number equals 1 (where it will stay),
 * or it loops endlessly in a cycle which does not include 1.
 * Those numbers for which this process ends in 1 are happy numbers.
 * Example: 19 is a happy number
 * 1^2 + 9^2 = 82
 * 8^2 + 2^2 = 68
 * 6^2 + 8^2 = 100
 * 1^2 + 0^2 + 0^2 = 1
 */

/**
 * Analysis:
 * Like many math trick problems, this one you need to go through some examples to find a pattern.
 * Unlike that add digits problem, no magic pattern here.
 * When doing the square sum operations, it's like traversing through a Directed Graph.
 * As long as you are not yet in a loop, there is a chance to find 1.
 * Whenever you found a loop, you stop.
 * In the question, this is also the hint given.
 */

import java.util.*;

public class HappyNumber {
    Set<Integer> set = new HashSet<>();
    public boolean isHappy(int n) {
        //Termination Conditions
        if(n==1) return true;
        if(set.contains(n)) return false;

        //Recursive Conditions
        set.add(n);
        long tmp = 0;
        while(n>0){
            int digit = n%10;
            tmp += Math.pow(digit, 2);
            n = (n-digit)/10;
        }
        return isHappy((int)tmp);
    }
    public boolean isHappyLoop(int n){
        while(n!=1){
            if(set.contains(n)) return false;
            set.add(n);
            long tmp = 0;
            while(n>0){
                int digit = n%10;
                tmp += Math.pow(digit, 2);
                n = (n-digit)/10;
            }
            n=(int)tmp;
        }
        return true;
    }
}
