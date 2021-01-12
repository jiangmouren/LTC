package com.mycompany.app;

/**
 * Given a positive integer N, how many ways can we write it as a sum of consecutive positive integers?
 *
 * Example 1:
 *
 * Input: 5
 * Output: 2
 * Explanation: 5 = 5 = 2 + 3
 * Example 2:
 *
 * Input: 9
 * Output: 3
 * Explanation: 9 = 9 = 4 + 5 = 2 + 3 + 4
 * Example 3:
 *
 * Input: 15
 * Output: 4
 * Explanation: 15 = 15 = 8 + 7 = 4 + 5 + 6 = 1 + 2 + 3 + 4 + 5
 * Note: 1 <= N <= 10 ^ 9.
 */

/**
 * Analysis:
 * 这是一道数学分析的题目。关键是分析清楚里面的数学规律。一旦规律搞清，实现很简单。
 * 基本思路就是对每个给定的N，遍历每一种可能的组合。
 * 这里边有两个问题：
 * 1. 对每个给定的N有哪些组合需要被check?很明显，不需要从1组数到N组数都check，要把最大可能的组数找到。
 * 2. 对于，每种给定的组数k，如何判定N是否可以拆成k个连续数的和？
 *
 * 对于给定的N和试图拆分成的组数k，如果这组连续数存在的话，那么它们在数轴上的位置，是确定的。
 * 确定的锚点，这组数的均值，或者中点，就是: N/k
 * 那么接下来就是，只要在 N/k的左侧和右侧都能找到：
 * k/2个数（当k是偶数），或者 (k-1)/2个数（当K是奇数）
 *
 * 再进一步观察，会发现只要左侧满足，右侧就一定满足，原因如下:
 * 左侧数轴的长度：N/k，右侧数轴的长度(K-1)N/k,所以只要K>=2，右侧数轴长度就大于等于左侧数轴长度。
 * 左侧数轴是(0, N/k)，右侧数轴是(N/k, N]，会发现左侧数轴两个端点都不能取，而右侧数轴的右端点理论上可取，
 * 所以只要左侧能找到足够的点，右侧一定能找到足够的点。
 *
 * 从另外一个角度看，往左找到最左就是1，那么对应额右侧点x要满足：x+1 = 2N/k
 * x = (2N/k)-1
 * 只要K>=2，那么2N/k<=N，所以(2N/K)-1<=N-1
 * 所以右侧一定满足。
 * 看左侧，不管K是奇数还是偶数，N/K都最起码比K/2大0.5
 * 所以：N/K >= K/2 + 1 --> k(k+1)<=2*N
 * 以上是个一元二次方程，可以写出解析解，或者直接用上述不等式控制 K 就可以。
 * Time:O(sqrt(N)), Space: O(1)
 */
public class consecutiveNumbersSum {
    public int consecutiveNumbersSum(int N) {
        //double max = (Math.sqrt(8*(double)N+1.0)-1)/2;//因为N的上限很大，乘以8以后会出现int溢出的情况，所以要cast成double
        int result = 1;
        //for(int i=2; i<max; i++){
        for(int i=2; i*(i+1)<2*N; i++){//或者用二次方程求根公式写出解析解，或者直接用不等式控制i就可以
            if(i%2==0){
                if(N%i==(i/2)){
                    result++;
                }
            }
            else{
                if(N%i==0){
                    result++;
                }
            }
        }
        return result;
    }
}
