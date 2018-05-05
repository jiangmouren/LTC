package com.mycompany.app;
/**
 * Question:
 * Given n, how many structurally unique BST's (binary search trees) that store values 1...n?
 * For example,
 * Given n = 3, there are a total of 5 unique BST's.
 *
 *    1         3     3      2      1
 *     \       /     /      / \      \
 *      3     2     1      1   3      2
 *     /     /       \                 \
 *  2     1         2                 3
 *
 */

/**
 * Analysis:
 * This is a DP problem.
 * My initial sense is to solve it as backtracking problem. Something like whenever find a path then increment count.
 * Soon I find it is very difficult to fit it into the Backtracking pattern. 
 * This one looks very much similar to the parenthesis problem. Both are about descions and both are dealing with trees.
 * But they are fundamentally different.
 *
 * 1. DP model.
 * f(n) = Sum(f(i)f(n-1-i)), i=0-->(n-1)
 * f(0) = 1, f(1) = 1, f(2) = 2;
 * Here f(n) represent the number of Unique BSTs for n consecutive numbers. 
 * You will realize doesn't matter it is from 1-->n or i-->n-1+i, the number of unique BSTs are the same, so we have the above recursion. 
 * To verify that, we have f(3) = f(0)f(2) + f(1)f(1) + f(2)f(0) = 2 + 1 + 2 = 5;
 * The recursion function requires us to use an array, we need to f(0) --> f(n-1) to construct f(n)
 * Solved. 
 *
 * 2. What if we want to list out all possible BSTs?
 * We will need a helper function that returns list of all possible BSTs. 
 * So for selected root, we have a list of left subtrees and a list of right subtrees, we then can then construct the solution list.
 *
 * 3. Why this cannot be fit into a Backtracking problem?
 * To fit into a Backtracking pattern, there are two requirments:
 * 1. the solution can be found in a subproblem.
 * 2. we have a templete to hold a specific solution, like a StringBuilder buffer.
 * In this problem, both are violated. 
 * If we split the original problem into left subproblem ans right subprolem, we have to finish both subproblem to solve the orginal problem.
 * So we cannot find the solution in a specific subprolem. 
 * We do not have BST buffer.
 */
public class UniqueBinarySearchTrees {
    public int uniqueBSTs(int n){
        //edge cases
        if(n==0) return 1;
        else if(n==1) return 1;
        else if(n==2) return 2;

        int[] sln = new int[n+1];
        sln[0] = 1;
        sln[1] = 1;
        sln[2] = 2;
        for(int i=3; i<=n; i++){
            sln[i] = 0;
            for(int j=0; j<i; j++){
                sln[i]+=sln[j]*sln[i-1-j];
            }
        }
        return sln[n];
    }
}
