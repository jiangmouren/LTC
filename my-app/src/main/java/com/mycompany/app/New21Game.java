package com.mycompany.app;

/**
 * https://leetcode.com/problems/new-21-game/
 * Alice plays the following game, loosely based on the card game "21".
 *
 * Alice starts with 0 points, and draws numbers while she has less than K points.
 * During each draw, she gains an integer number of points randomly from the range [1, W], where W is an integer.
 * Each draw is independent and the outcomes have equal probabilities.
 * Alice stops drawing numbers when she gets K or more points.
 * What is the probability that she has N or less points?
 *
 * Example 1:
 * Input: N = 10, K = 1, W = 10
 * Output: 1.00000
 * Explanation:  Alice gets a single card, then stops.
 *
 * Example 2:
 * Input: N = 6, K = 1, W = 10
 * Output: 0.60000
 * Explanation:  Alice gets a single card, then stops.
 * In 6 out of W = 10 possibilities, she is at or below N = 6 points.
 *
 * Example 3:
 * Input: N = 21, K = 17, W = 10
 * Output: 0.73278
 *
 * Note:
 * 0 <= K <= N <= 10000
 * 1 <= W <= 10000
 * Answers will be accepted as correct if they are within 10^-5 of the correct answer.
 * The judging time limit has been reduced for this question.
 */
//DP问题，从初值为0开始，可以达到1，2,3...W的初值，然后到达这些值的概率是1/W，然后往后推，一旦到达[K, N]这个区间就终止，
//其相应的概率算为1，如果到了(N, infi)其概率就算为0.
//只有0~K-1的初值需要去推导，但是推得每个值需要W个前值得和，所以或者每步都算W次，或者保留W个前值的和到一个变量里面
public class New21Game {
    public double new21Game(int N, int K, int W) {
        double[] dp = new double[K+1];
        dp[K] = 1;
        double wSum = 0;
        for(int i=1; i<=W; i++){
            int idx = K-1+i;
            double temp = idx<=N ? 1.0 : 0;
            wSum += temp;
        }

        for(int i=K-1; i>=0; i--){
            dp[i] += wSum/(double)W;
            int lastIdx = i + W;
            if(lastIdx>K && lastIdx<=N){
                wSum -= 1;
            }
            else if(lastIdx<=K){
                wSum -= dp[lastIdx];
            }
            wSum += dp[i];
        }
        return dp[0];
    }

    //下面这种解法，就是对W个前值每次求和，时间上会超出Leetcode的要求
    public double new21GameSln1(int N, int K, int W) {
        double[][] dp = new double[K+1][K+1];
        //set up initial values
        //从后面的额迭代关系看，这个初值不需要，但是有这个初值，可以cover K=0的情况
        dp[K][K] = 1;

        for(int j=K-1; j>=0; j--){
            for(int i=j; i<=K; i++){
                for(int l=1; l<=W && i+l<=N; l++){
                    if(i+l<K){
                        dp[i][j] += dp[i+l][j+1]/(double)W;
                    }
                    else{
                        dp[i][j] += 1.0/(double)W;
                    }
                }
            }
        }
        return dp[0][0];
    }

    //下面这种解法，是我最开始的想法，就是把各种过程中可能出现的状态，展开成一个树状结构
    //比如初值为3，是0->1>2>3，还是0->3作为两种不同的状态分别求解
    //这样建模，让问题变复杂到了，没必要，上面的解法，相当于该把只要初值是3的情况合并起来看
    public double new21GameSln2(int N, int K, int W) {
        double[] dp = new double[K+1];
        dp[K] = 1;
        for(int i=K-1; i>=0; i--){
            for(int j=1; j<=W && i+j<=N; j++){
                if(i+j<K){
                    dp[i] += dp[i+j]/(double)W;
                }
                else{
                    dp[i] += 1.0/(double)W;
                }
            }
        }
        return dp[0];
    }

}
