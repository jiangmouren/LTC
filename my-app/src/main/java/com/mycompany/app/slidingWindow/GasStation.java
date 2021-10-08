package com.mycompany.app.slidingWindow;

/**
 * https://leetcode.com/problems/gas-station/
 * There are n gas stations along a circular route, where the amount of gas at the ith station is gas[i].
 * You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from the ith station to its next (i + 1)th station.
 * You begin the journey with an empty tank at one of the gas stations.
 * Given two integer arrays gas and cost, return the starting gas station's index if you can travel around the circuit
 * once in the clockwise direction, otherwise return -1. If there exists a solution, it is guaranteed to be unique
 *
 * Example 1:
 * Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
 * Output: 3
 * Explanation:
 * Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
 * Travel to station 4. Your tank = 4 - 1 + 5 = 8
 * Travel to station 0. Your tank = 8 - 2 + 1 = 7
 * Travel to station 1. Your tank = 7 - 3 + 2 = 6
 * Travel to station 2. Your tank = 6 - 4 + 3 = 5
 * Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
 * Therefore, return 3 as the starting index.
 *
 * Example 2:
 * Input: gas = [2,3,4], cost = [3,4,3]
 * Output: -1
 * Explanation:
 * You can't start at station 0 or 1, as there is not enough gas to travel to the next station.
 * Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
 * Travel to station 0. Your tank = 4 - 3 + 2 = 3
 * Travel to station 1. Your tank = 3 - 3 + 3 = 3
 * You cannot travel back to station 2, as it requires 4 unit of gas but you only have 3.
 * Therefore, you can't travel around the circuit once no matter where you start.
 *
 * Constraints:
 * gas.length == n
 * cost.length == n
 * 1 <= n <= 104
 * 0 <= gas[i], cost[i] <= 104
 */
public class GasStation{
    /**
     * 确定解存在不需要loop一圈。
     * 只要存在一个ptr0,截止gasLeft终点的所有prefixSum全是非负数，而且totalSum>=0, 解就找到了。
     * 原因如下：
     * 只需要证明对于任意的'i'属于[0, ptr0-1], sum([ptr0, n])+sum([0, i])都是非负数。
     * 首先从ptr0往前看所有的suffixSum都是负值，既suffixSum([i, ptr0))<0，这个的证明后面再说。
     * 假设上述成立，那么sum([ptr0, n])+sum([0, i])是非负数就显而易见了，因为sum([ptr0, n])+sum([0, i])+suffixSum([i, ptr0))=Total>=0
     * 所以前者一定是大于零的。下面证明suffixSum([i, ptr0))<0:
     * 可以有两个思路：
     * 1. 假设：s0......s1......s2......s3......ptr0......n，si都是前面使用过的起始点，所以sum([s(i), s(i+1)])都是负值，
     * 包括sum(s3, ptr0)也是负值。 所以i如果刚好处在s点上，自然suffixSum([i, ptr0))<0.
     * 如果i处于两个s点之间，比如s1和s2之间，只需要证明sum([i, s2])是负值，即可。因为sum([s1, s2])是负值，而sum([s1, i])是非负数，
     * 否则就不会到s2才换起始点了，所以显然sum([i, s2])必然是负值，证毕。
     * 2. 第二种办法就是不参考这些起始点，用反证法。假设存在i使得sum([i, ptr0))非负数，那么必然从i往又找，必然存在一个非负数，假设是j.
     * 把i到j之间的负数全部抛掉。那么因为j并不是最终的满足条件的起始点，那么往右找必然存在一个点k，满足sum([j, k])<0.
     * 那么sum([i,j))<0, sum([j,k])<0，而sum([i, ptr0])>=0，所以sum((k, ptr0])>0.而且上述推导是recursive，一直做下去，
     * 也就说：对于[i, ptr0)区间中的，任意非负数j，都可以在(j, ptr0)的区间内找到一个k，使得sum([j, k])<0，
     * 或者说所有的非负数都可以找到一个以其为起点的prefix，让prefixSum<0，将这些prefix区间扣掉，余下的[i, ptr0)之内全是负数，
     * 那么整个区间的加和就不可能是非负数，所以sum([i, ptr0))不可能是非负数，证毕。
     */
    public int canCompleteCircuitSln2(int[] gas, int[] cost) {
        int n = gas.length;

        int total_tank = 0;
        int curr_tank = 0;
        int starting_station = 0;
        for (int i = 0; i < n; ++i) {
            total_tank += gas[i] - cost[i];
            curr_tank += gas[i] - cost[i];//注意curr_tank也是accumulative的
            // If one couldn't get here,
            if (curr_tank < 0) {
                // Pick up the next station as the starting one.
                starting_station = i + 1;
                // Start with an empty tank.
                curr_tank = 0;
            }
        }
        return total_tank >= 0 ? starting_station : -1;
    }

    /**
     * 下面这种是我想到的解法，复杂度与下面的答案是一致的，大题思路也是一致的，但是下面的答案在逻辑上推导又更深入一层，以至于代码更加简化。
     * 很快就可以意识到，需要一个gasLeft array,然后只能从其中是非负数的位置开始。
     * 然后也会意识到这个gasLeft array从选定起始点开始算的prefixSum必须始终是非负数，才能保证行驶到下一站。
     * 然后问题就落在了，如何找这个起始点。从最左边的非负数开始找->发现问题->换下一个非负数重新开始找->
     * 如果找上面这么做，复杂度会是O(n^2)，问题是能不能比这更好？
     * 从我们用两个Pointer搜索的这个pattern来看，有实现sliding window的可能性。
     * 然后尝试分析实现sliding window的可行性。
     * 通常的sliding window的思路是：一前一后两个pointer,然后周期性的后面的pointer可以drop掉一些元素，
     * 然后原来window里余下的元素所构成子问题的解要能够被复用，来处理后续问题，而不需要重新去计算。
     * 但是在这里面，一旦drop掉一个window左端的数，那么新的起点到端点的prefixSum全都得重算。
     * 所以这种情况下我就想，有没有可能这整个window我都可以整个drop掉？答案是可以，原因如下：
     * 对于[ptr0, ptr1](注意这里ptr1指的是第一次出现负值的位置，不是++之后的位置)里面的prefixSum，
     * 在ptr1的位置第一次出现了负值，能够全部drop的条件是：
     * 不存在'i'在(ptr0, ptr1)当中，能够使得[i, ptr1]里面的prefixSum全是非负数。
     * 如果sum([i, ptr1])是非负数，而sum([ptr0, ptr1])是负数，说明sum([ptr0, i-1])是负数！
     * 这与之前说的，在ptr1之前，所有ptr0开始的prefixSum都是非负数矛盾！所以这个'i'不存在。
     * 所以整个window都可以drop. 思路就清楚了，但是上述思路，实现起来，控制条件还是需要一点巧妙设计和构思。
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int ptr0 = 0;
        int ptr1 = 0;
        int res = -1;
        while(ptr0<n){//确保起始点只搜索一遍
            int sum = 0;
            //我这种写法，就是要真正Loop一圈，prefixSum都是非负数，才确定找到答案。
            while(sum>=0 && ptr1<ptr0+n){//允许ptr1超出区间，反而更方便写边界条件
                sum += gas[ptr1%n] - cost[ptr1%n];//使用取模，处理ptr1出界的情况
                ptr1++; //注意当下的ptr1已经取到是sum变负值之后一个位置了
            }
            if(sum<0){
                if(ptr1>=n){//避免ptr0 loop back回已经搜索过的起始点
                    break;
                }
                ptr0 = ptr1;
            }
            else{//仅仅ptr1>=ptr0+n，并不能做下述操作，这里"else"还蕴含了sum>=0
                res = ptr0;
                break;
            }
        }
        return res;
    }

}