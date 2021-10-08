package com.mycompany.app.dp;
/**
 * Given a non-empty string, encode the string such that its encoded length is the shortest.
 * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times.
 * Note:
 * k will be a positive integer and encoded string will not be empty or have extra space.
 * You may assume that the input string contains only lowercase English letters. The string's length is at most 160.
 * If an encoding process does not make the string shorter, then do not encode it. If there are several solutions,
 * return any of them is fine.
 * Example 1:
 * Input: "aaa"
 * Output: "aaa"
 * Explanation: There is no way to encode it such that it is shorter than the input string, so we do not encode it.
 *
 * Example 2:
 * Input: "aaaaa"
 * Output: "5[a]"
 * Explanation: "5[a]" is shorter than "aaaaa" by 1 character.
 *
 * Example 3:
 * Input: "aaaaaaaaaa"
 * Output: "10[a]"
 * Explanation: "a9[a]" or "9[a]a" are also valid solutions, both of them have the same length = 5, which is the same as "10[a]".
 *
 * Example 4:
 * Input: "aabcaabcd"
 * Output: "2[aabc]d"
 * Explanation: "aabc" occurs twice, so one answer can be "2[aabc]d".
 *
 * Example 5:
 * Input: "abbbabbbcabbbabbbc"
 * Output: "2[2[abbb]c]"
 * Explanation: "abbbabbbc" occurs twice, but "abbbabbbc" can also be encoded to "2[abbb]c", so one answer can be "2[2[abbb]c]".
 */

/**
 * This is one of the hardest DP problem I have ever seen, a good example of finding optimal value and construct the
 * optimal solution at the same time. To memorize the solution along the way, we can use more sophisticated type as DP array.
 * In this specific case, we use string as the DP array type, so we have both the optimal value and the optimal solution
 * in place at the same time.
 *
 * This problem is complicated because the recursion is not very obvious.
 * Basically the original problem itself is not directly recursive, we need to somehow split the original
 * problem into some sub-problems, which are recursive.
 *
 * The following is good example to understand how to decompose the problem:
 * Input: "abbbabbbcabbbabbbc"
 * Output: "2[2[abbb]c]"
 *
 * There are two types of strings:
 * 1. the complete string a multiple of some patterns: like the above input is 2 "abbbabbbc"
 * 2. the string is not a multiple of some pattern: like the above input, the substring "abbbabbbc" is not a multiple
 * of something, it has one more c. In this case, we split it into "abbaabba" + "c", then work on both the prefix and
 * the suffix.
 *
 * Now what are the options when given a string:
 * 1. if it is "complete", we can try to fold it(if it saves), then we have reduced the original problem to the remaining
 * prefix.
 * 2. another option, for any given string, we can try to split and then move onto prefix and suffix.
 * 3. Question: for dp[i][j] say it has multiple valid solutions, like in the case of the Example 3.
 * Both 10[a] and 9[a]a are valid solutions, which one should dp[i][j] take? Does it matter which one and if so should
 * we take all valid solutions?
 * The answer is it doesn't matter, and we can take any valid solution. Obviously it doesn't matter for the sub-problem
 * itself, that is said in the problem. The only question is does it matter for the parent problem? It will only matter,
 * if you are thinking about merging this sub-problem with sibling sub-problems. But for the merging case, it should be
 * covered by a different splitting strategy. So for any given splitting, we will not consider merging result from those
 * sub-problems, we will only do concatenation.
 *
 * Original problem = min{opt(1), opt(2)}
 * dp[i][j] is the best solution for substring input[i:j]
 * dp[i][j] = min{min{cost(k)+dp[i][k], for all valid prefix pattern}, min{dp[i][m] + dp[m+1][j], for all splits}}
 * The topological order is from small sub to large sub:
 *                                                      i right to left, means large to small, down-->up
 *                                                      j left to right, means small to large, left-->right
 *                                                      i and j should be symmetric, so does not matter row or column first.
 * Base cases: when j-i<5, dp[i][j] = input.subString(i, j+1)
 */

class EncodeStringWithShortestLength {
    public String encode(String s) {
        String[][] dp = new String[s.length()][s.length()];
        for(int i=s.length()-1; i>=0; i--) {
            for (int j = i; j < s.length(); j++) {
                String sub = s.substring(i, j + 1);
                //base cases
                if (j - i < 5) {
                    dp[i][j] = sub;
                }
                else{//Put an else for non-base cases, other wise, will have index out of bound issue.
                    //complete pattern case
                    for (int l = i; l <= (j + i) / 2; l++) {
                        //Very important to use the original substring when you are doing pattern matching.
                        //Otherwise, because we are constructing from smaller substring to larger ones,
                        //the optimized smaller substring will not match the original larger substring's pattern.
                        String pattern = s.substring(i, l+1);
                        //the pattern is dp[i][l]
                        if (sub.replaceAll(pattern,"").length() == 0) {
                            //if "complete" && first(dp[i][j]==null) or better solution, take the solution
                            int repeatTimes = sub.length() / pattern.length();
                            String digits = "" + repeatTimes;
                            //But you need to use the optimized substring for the length calculation and solution construction.
                            int newLength = digits.length() + 2 + dp[i][l].length();
                            if (dp[i][j] == null || dp[i][j].length() > newLength) {
                                dp[i][j] = digits + "[" + dp[i][l] + "]";
                            }
                        }
                    }

                    //split cases
                    for (int k = i; k < j; k++) {
                        //if first(dp[i][j]==null) or better solution, take the solution
                        if (dp[i][j] == null || dp[i][j].length() > (dp[i][k].length() + dp[k + 1][j].length())) {
                            dp[i][j] = dp[i][k] + dp[k + 1][j];
                        }
                    }
                }
            }
        }
        return dp[0][s.length()-1];
    }
}
