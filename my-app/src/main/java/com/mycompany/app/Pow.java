package com.mycompany.app;

import java.util.*;

/**
 * Implement pow(x, n), which calculates x raised to the power n (i.e., xn).
 *
 * Example 1:
 * Input: x = 2.00000, n = 10
 * Output: 1024.00000
 *
 * Example 2:
 * Input: x = 2.10000, n = 3
 * Output: 9.26100
 *
 * Example 3:
 * Input: x = 2.00000, n = -2
 * Output: 0.25000
 * Explanation: 2-2 = 1/22 = 1/4 = 0.25
 *
 * Constraints:
 * -100.0 < x < 100.0
 * -231 <= n <= 231-1
 * -104 <= xn <= 104
 */

public class Pow {
    public double myPow(double x, int n) {
        //最简单的想法就是把n个x依次相乘
        //优化一下的思路，就是x^n = x^(n/2) * x^(n/2)
        //用这种方式每次把运算量减半，用lg(n)就可以把x^n算出来了
        //对于n%2==0, n->n/2+n/2; 对于n%2==1， n->n/2+(n+1)/2
        //然后用个HashMap记录下已经算过的值，毕竟4=2+2， 3=2+1,这里面2就是重叠的
        Map<Integer, Double> map = new HashMap<>();
        map.put(1, x);
        map.put(0, 1.0);
        map.put(-1, 1.0/x);
        return pow(x, n, map);
    }

    private double pow(double x, long n, Map<Integer, Double> map){
        if(map.containsKey((int)n)){
            return map.get((int)n);
        }

        double res = 0.0;
        if(n%2==0){
            res =  pow(x, n/2, map)*pow(x, n/2, map);
        }
        else if(n>0){
            res = pow(x, n/2, map)*pow(x, (n+1)/2, map);//因为n的区间的问题，这里加一就有可能溢出，所以n要用long
        }
        else{
            res = pow(x, n/2, map)*pow(x, (n-1)/2, map);//这里也有溢出的可能
        }
        map.put((int)n, res);
        return res;
    }
}
