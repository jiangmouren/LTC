package Finished.dp;

/**
 * Question:
 * There is a fence with n posts, each post can be painted with one of the k colors.
 * You have to paint all the posts such that no more than two adjacent fence posts have the same color.
 * Return the total number of ways you can paint the fence.
 * Note:
 * n and k are non-negative integers.
 */

import java.util.*;

/**
 * Analysis:
 * It's symmetrical. From 1 all the way to k, different colors are symmetrical.
 * A(n)=B(n-1)+C(n-1)+A(n-1)-A(n-2), A(n-2) will not be effected by anything on the n-1 side,
 * so there will be a clear cut at A(n-2)
 *
 * More generally:
 * F_k(n) = Sum(n-1) - F_k(n-2)
 * Sum(n-1) = k*F_k(n-1)
 * F_1(1) = 1, F_1(2) = k, Sum(2) = k^2;
 * It's always good to figure out the topological order:
 * We need 3 variables: f(n-2), f(n-1), s(n-1) to get f(n) and s(n), and on and on
 *
 */

public class PaintFence {
    public int paintFence1(int n, int k){
        if(n==1) return k;
        else if(n==2) return k*k;
        int f1=1, f2=k, s2=k*k;
        for(int i=3; i<=n; i++){
            int f3 = s2-f1;
            int s3 = k*f3;
            f1=f2;
            f2=f3;
            s2=s3;
        }
        return s2;
    }

    /**
     * This is some how like a fibonacci sequence, must use memorization when doing recursion.
     *                  f(n)
     *         f(n-1)         f(n-2)
     *     f(n-2)  f(n-3)  f(n-3)  f(n-4)
     * As you can see in the above diagram, f(n-2) and f(n-3) occurs in different branches.
     * @param n
     * @param k
     * @return
     */
    public int paintFence2(int n, int k){
        return k*helper(n, k);
    }
    private int helper(int n, int k){
        //Termination cases
        if(n==1) return 1;
        if(n==2) return k;

        //Recursive cases
        Map<Integer, Integer> map = new HashMap<>();
        int result;
        if(map.containsKey(n)) return map.get(n);
        else{
            result = k*helper(n-1, k) - helper(n-2, k);
            map.put(n, result);
        }
        return result;
    }

}
