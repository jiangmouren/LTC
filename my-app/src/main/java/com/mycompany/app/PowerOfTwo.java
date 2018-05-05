package com.mycompany.app;

/**
 * Created by jiangmouren on 6/4/17.
 */

/**
 * Question:
 * Given an integer, write a function to determine if it is a power of two.
 */

/**
 * Analysis:
 * No way to do it in O(1), can do O(lgn)
 */
public class PowerOfTwo {
    public boolean isPowerOfTwo(int n) {
        //Termination conditions
        if(n==1) return true;
        if(n%2!=0) return false;

        //Recursive case
        return isPowerOfTwo(n/2);
    }


    public boolean isPowerOfTwoLoop(int n) {
        while(n>=1){
            if(n==1) return true;
            if(n%2!=0) return false;
            n=n/2;
        }
        return false;
    }
}
