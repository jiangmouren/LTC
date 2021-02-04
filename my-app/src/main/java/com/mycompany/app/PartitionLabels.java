package com.mycompany.app;
/**
 * https://leetcode.com/problems/partition-labels/
 * A string S of lowercase English letters is given. We want to partition this string into as many parts as possible so that each letter appears in at most one part, and return a list of integers representing the size of these parts.
 *
 * Example 1:
 * Input: S = "ababcbacadefegdehijhklij"
 * Output: [9,7,8]
 * Explanation:
 * The partition is "ababcbaca", "defegde", "hijhklij".
 * This is a partition so that each letter appears in at most one part.
 * A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
 *
 * Note:
 * S will have length in range [1, 500].
 * S will consist of lowercase English letters ('a' to 'z') only.
 */

import java.util.*;

public class PartitionLabels {
    public List<Integer> partitionLabels(String S) {
        //这个可以转化成merge interval的问题
        int[][] positions = new int[26][2];
        for(int i=0; i<26; i++){
            positions[i][0] = -1;
            positions[i][1] = -1;
        }
        for(int i=0; i<S.length(); i++){
            char c = S.charAt(i);
            int idx = c - 'a';
            if(positions[idx][0]==-1){
                positions[idx][0] = i;
                positions[idx][1] = i;
            }
            else{
                positions[idx][1] = i;
            }
        }
        Arrays.sort(positions, (a,b)->a[0]-b[0]);
        //start merging intervals
        List<Integer> res = new ArrayList<>();
        int ptr0 = 0;
        int ptr1 = 1;
        while(ptr1<26){
            if(positions[ptr0][0]==-1){
                ptr0 = ptr1;
                ptr1++;
                continue;
            }
            else{
                if(positions[ptr1][0]<=positions[ptr0][1]){
                    if(positions[ptr1][1]>positions[ptr0][1]){
                        positions[ptr0][1] = positions[ptr1][1];
                    }
                }
                else{
                    int val = positions[ptr0][1] - positions[ptr0][0] + 1;
                    res.add(val);
                    ptr0 = ptr1;
                }
                ptr1++;
            }
        }
        //take the last val
        int val = positions[ptr0][1] - positions[ptr0][0] + 1;
        res.add(val);
        return res;
    }
}
