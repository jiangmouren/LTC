package com.mycompany.app.greedy;

/**
 * https://leetcode.com/problems/can-place-flowers/
 * You have a long flowerbed in which some of the plots are planted, and some are not.
 * However, flowers cannot be planted in adjacent plots.
 * Given an integer array flowerbed containing 0's and 1's, where 0 means empty and 1 means not empty, and an integer n,
 * return if n new flowers can be planted in the flowerbed without violating the no-adjacent-flowers rule.
 *
 * Example 1:
 * Input: flowerbed = [1,0,0,0,1], n = 1
 * Output: true
 *
 * Example 2:
 * Input: flowerbed = [1,0,0,0,1], n = 2
 * Output: false
 *
 * Constraints:
 * 1 <= flowerbed.length <= 2 * 104
 * flowerbed[i] is 0 or 1.
 * There are no two adjacent flowers in flowerbed.
 * 0 <= n <= flowerbed.length
 */
public class CanPlaceFlowers {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        //4 -> 2, 3 -> 2, 5 -> 3 --> flowerbed can fit in at most (l+1)/2
        //下面这段early termination加上跟快，不加更简洁
        //int max = (flowerbed.length+1)/2;
        //    if(n>max){
        //    return false;
        //}
        for(int i=0; i<flowerbed.length && n>0; i++){
            if(flowerbed[i]==0 && (i==0 || flowerbed[i-1]==0) && (i==flowerbed.length-1 || flowerbed[i+1]==0)){
                flowerbed[i] = 1;//don't forget to set position i to 1
                n--;
            }
        }
        return n==0;
    }
}
