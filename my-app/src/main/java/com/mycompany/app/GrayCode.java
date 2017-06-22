package com.mycompany.app;

/**
 * Question:
 * The gray code is a binary numeral system where two successive values differ in only one bit.
 * Given a non-negative integer n representing the total number of bits in the code, print the sequence of gray code.
 * A gray code sequence must begin with 0.
 * For example, given n = 2, return [0,1,3,2]. Its gray code sequence is:
 * 00 - 0
 * 01 - 1
 * 11 - 3
 * 10 - 2
 * Note:
 * For a given n, a gray code sequence is not uniquely defined.
 * For example, [0,2,3,1] is also a valid gray code sequence according to the above definition.
 * For now, the judge is able to judge based on one instance of gray code sequence. Sorry about that.
 */

/**
 * Analysis:
 * 1. Gray Code property.
 * Try some example and you will find the pattern:
 * n=1: 0-->1-->0
 *
 *        -->01
 * n=2: 00     -->11-->00
 *        -->10
 * For n=3 case, refer to the following link:
 * https://github.com/jiangmouren/Resources/blob/master/GrayCode.jpg
 * The pattern you will find is basically the code will always move one step away as you flip one bit at a time.
 * It will start with all 0s, next level with single 1, then double 1s, then 3 1s, then loop back.
 * All the edges are bidirectional. Will have many loops in this graph.
 *
 * 2. Bit manipulation implementation.
 * The way to deal with it not a graph approach, it is a complex directed graph with many loops.
 * Instead we use bit manipulation and set 1 bit at a time.
 */

public class GrayCode {
    public int[] grayCode(int n){
        if(n<1) return new int[0];
        int[] result = new int[n+1];

        int tmp = 0, msk = 1;
        result[0] = tmp;
        for(int i=1; i<=n; i++){
            tmp = tmp | msk;//msk starts from bit 0, so we mask before shift
            msk = msk << 1;
            result[i] = tmp;//msk result starts from position 1
        }
        return result;
    }
}
